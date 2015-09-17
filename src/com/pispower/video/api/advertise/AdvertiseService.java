/*
 * File Name  : AdvertiseService.java
 * Authors    : lunjz*
 * Stage      : Implementation
 * Created    : Aug 12, 2015 7:13:46 PM
 * Copyright  : Copyright © 2009 OneCloud Co., Ltd.  All rights reserved.
 *
 * This software is the confidential and proprietary information of 
 * OneCloud Co., Ltd. ("Confidential Information").  You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with OneCloud.
 */

package com.pispower.video.api.advertise;

import java.text.*;
import java.util.*;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import onecloud.web.support.QueryString;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.pispower.video.api.internal.*;


/**
 * This class is used for
 *
 */
public class AdvertiseService
{
	private final Logger logger = Logger.getLogger(getClass());
	
	private Client client;
	
	public AdvertiseService(String accessKey, String accessSecret)
	{
		client = new Client(accessKey, accessSecret);
	}

	public Advertise add(AdvertiseAddRequest request) throws ClientException, PispowerAPIException
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		QueryString queryString = new QueryString();
		queryString.addParameter("name", request.getName());
		queryString.addParameter("adId", request.getAdId().toString());
		queryString.addParameter("position", request.getPosition());
		queryString.addParameter("onlineDate", dateFormat.format(request.getOnlineDate()));
		queryString.addParameter("offlineDate", dateFormat.format(request.getOfflineDate()));
		if (StringUtils.isNotBlank(request.getLink()))
		{
			queryString.addParameter("link", request.getLink());
		}
		queryString.addParameter("catalogIds", listToString(request.getCatalogIds()));
		
		JSONObject json = client.post("/advertising/add.api", queryString);
		if (json.getInt("statusCode") == 0)
		{
			return jsonToAdvertise(json);
		}
		else
		{
			logger.error("add advertise fail, json:" + json);
			throw new PispowerAPIException(json.getInt("statusCode"), json.getString("message"));
		}
	}
	
	public boolean update(AdvertiseUpdateRequest request) throws ClientException, PispowerAPIException
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		QueryString queryString = new QueryString();
		queryString.addParameter("id", request.getId().toString());
		if (StringUtils.isNotBlank(request.getName()))
		{
			queryString.addParameter("name", request.getName());
		}
		if (request.getAdId() != null)
		{
			queryString.addParameter("adId", request.getAdId().toString());
		}
		if (StringUtils.isNotBlank(request.getPosition()))
		{
			queryString.addParameter("position", request.getPosition());
		}
		if (request.getOnlineDate() != null)
		{
			queryString.addParameter("onlineDate", dateFormat.format(request.getOnlineDate()));
		}
		if (request.getOfflineDate() != null)
		{
			queryString.addParameter("offlineDate", dateFormat.format(request.getOfflineDate()));
		}
		if (StringUtils.isNotBlank(request.getLink()))
		{
			queryString.addParameter("link", request.getLink());
		}
		if (request.getCatalogIds() != null)
		{
			queryString.addParameter("catalogIds", listToString(request.getCatalogIds()));
		}
		
		JSONObject json = client.post("/advertising/update.api", queryString);
		if (json.getInt("statusCode") == 0)
		{
			return true;
		}
		else
		{
			logger.error("update advertise fail, json:" + json);
			throw new PispowerAPIException(json.getInt("statusCode"), json.getString("message"));
		}
	}
	
	public Advertise get(Integer id) throws ClientException, PispowerAPIException
	{
		QueryString queryString = new QueryString();
		queryString.addParameter("id", id.toString());
		JSONObject json = client.get("/advertising/get.api", queryString);
		if (json.getInt("statusCode") == 0)
		{
			return jsonToAdvertise(json);
		}
		else
		{
			logger.error("get advertise fail, json:" + json);
			throw new PispowerAPIException(json.getInt("statusCode"), json.getString("message"));
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Advertise> list(AdvertiseListRequest request) throws ClientException, PispowerAPIException
	{
		QueryString queryString = new QueryString();
		if (StringUtils.isNotBlank(request.getNameLike()))
		{					
			queryString.addParameter("nameLike", request.getNameLike());
		}
		if (StringUtils.isNotBlank(request.getStatus()))
		{
			queryString.addParameter("status", request.getStatus());
		}
		if (request.getPage() != null)
		{
			queryString.addParameter("page", String.valueOf(request.getPage()));
		}
		if (request.getMaxResult() != null)
		{
			queryString.addParameter("maxResult", String.valueOf(request.getMaxResult()));
		}
		JSONObject json = client.get("/advertising/list.api", queryString);
		if (json.getInt("statusCode") == 0)
		{
			List<Advertise> advertises = new ArrayList<>();
			JSONArray array = json.getJSONArray("advertiseList");
			for (Iterator<JSONObject> iterator = array.iterator(); iterator.hasNext();)
			{
				advertises.add(jsonToAdvertise(iterator.next()));
			}
			return advertises;
		}
		else
		{
			logger.error("list advertise fail, json:" + json);
			throw new PispowerAPIException(json.getInt("statusCode"), json.getString("message"));
		}
	}
	
	public boolean setOffline(Integer id) throws ClientException, PispowerAPIException
	{
		QueryString queryString = new QueryString();
		queryString.addParameter("id", id.toString());
		JSONObject json = client.get("/advertising/offline.api", queryString);
		if (json.getInt("statusCode") == 0)
		{
			return true;
		}
		else
		{
			logger.error("set offline advertise fail, json:" + json);
			throw new PispowerAPIException(json.getInt("statusCode"), json.getString("message"));
		}
	}
	
	public boolean delete(Integer id) throws ClientException, PispowerAPIException
	{
		QueryString queryString = new QueryString();
		queryString.addParameter("id", id.toString());
		JSONObject json = client.get("/advertising/delete.api", queryString);
		if (json.getInt("statusCode") == 0)
		{
			return true;
		}
		else
		{
			logger.error("delete advertise fail, json:" + json);
			throw new PispowerAPIException(json.getInt("statusCode"), json.getString("message"));
		}
	}
	
	@SuppressWarnings("unchecked")
	private Advertise jsonToAdvertise(JSONObject json)
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Advertise advertise = new Advertise();
		advertise.setId(json.getInt("id"));
		advertise.setAdId(json.getInt("adId"));
		advertise.setName(json.getString("name"));
		advertise.setPosition(json.getString("position"));
		advertise.setStatus(json.getString("status"));
		try
		{
			advertise.setOnlineDate(dateFormat.parse(json.getString("onlineDate")));
			advertise.setOfflineDate(dateFormat.parse(json.getString("offlineDate")));
			advertise.setCreateTime(dateFormat.parse(json.getString("createTime")));
		}
		catch (ParseException e)
		{
			logger.error("parse date fail", e);
		}
		
		advertise.setLink(json.getString("link"));
		List<Integer> catalogIds = new ArrayList<>();
		JSONArray array = json.getJSONArray("catalogIds");
		for (Iterator<Integer> iterator = array.iterator(); iterator.hasNext();)
		{
			catalogIds.add(iterator.next());
		}
		advertise.setCatalogIds(catalogIds);
		return advertise;
	}
	
	private <T> String listToString(List<T> list)
	{
		String value = "";
		Iterator<T> iterator = list.iterator();
		if (!iterator.hasNext())
		{
			return value;
		}
		
		for (;;)
		{
			value += iterator.next().toString();
			if (!iterator.hasNext())
			{
				return value;
			}
			value += ',';
		}
	}
}
