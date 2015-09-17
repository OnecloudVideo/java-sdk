/*
 * File Name  : AdService.java
 * Authors    : lunjz*
 * Stage      : Implementation
 * Created    : Aug 11, 2015 2:29:12 PM
 * Copyright  : Copyright © 2009 OneCloud Co., Ltd.  All rights reserved.
 *
 * This software is the confidential and proprietary information of 
 * OneCloud Co., Ltd. ("Confidential Information").  You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with OneCloud.
 */

package com.pispower.video.api.ad;

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
public class AdService
{
	private final Logger logger = Logger.getLogger(getClass());
	
	private Client client;
	
	public AdService(String accessKey, String accessSecret)
	{
		client = new Client(accessKey, accessSecret);
	}
	
	public Ad upload(AdUploadRequest request) throws ClientException, PispowerAPIException
	{
		QueryString queryString = new QueryString();
		JSONObject json = client.post("/ad/upload.api", queryString, request.getFile());
		if (json.getInt("statusCode") == 0)
		{
			return jsonToAd(json);
		}
		else
		{
			logger.error("upload ad fail, json:" + json);
			throw new PispowerAPIException(json.getInt("statusCode"), json.getString("message"));
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Ad> list(AdListRequest request) throws ClientException, PispowerAPIException
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
		
		JSONObject json = client.get("/ad/list.api", queryString);
		if (json.getInt("statusCode") == 0)
		{
			JSONArray array = json.getJSONArray("adList");
			List<Ad> ads = new ArrayList<>();
			for (Iterator<JSONObject> iterator = array.iterator(); iterator.hasNext();)
			{
				ads.add(jsonToAd(iterator.next()));
			}
			return ads;
		}
		else
		{
			logger.error("list ad fail, json:" + json);
			throw new PispowerAPIException(json.getInt("statusCode"), json.getString("message"));
		}
	}
	
	public Ad get(Integer id) throws ClientException, PispowerAPIException
	{
		QueryString queryString = new QueryString();
		queryString.addParameter("id", id.toString());
		JSONObject json = client.get("/ad/get.api", queryString);
		if (json.getInt("statusCode") == 0)
		{
			return jsonToAd(json);
		}
		else
		{
			logger.error("get ad fail, json:" + json);
			throw new PispowerAPIException(json.getInt("statusCode"), json.getString("message"));
		}
	}
	
	public boolean update(AdUpdateRequest request) throws ClientException, PispowerAPIException
	{
		QueryString queryString = new QueryString();
		queryString.addParameter("id", request.getId().toString());
		queryString.addParameter("name", request.getName());
		JSONObject json = client.post("/ad/update.api", queryString);
		if (json.getInt("statusCode") == 0)
		{
			return true;
		}
		else
		{
			logger.error("update Ad fail, json:" + json);
			throw new PispowerAPIException(json.getInt("statusCode"), json.getString("message"));
		}
	}
	
	public boolean delete(Integer id) throws ClientException, PispowerAPIException
	{
		QueryString queryString = new QueryString();
		queryString.addParameter("id", id.toString());
		JSONObject json = client.post("/ad/delete.api", queryString);
		if (json.getInt("statusCode") == 0)
		{
			return true;
		}
		else
		{
			logger.error("delete Ad fail, json:" + json);
			throw new PispowerAPIException(json.getInt("statusCode"), json.getString("message"));
		}
	}
	
	private Ad jsonToAd(JSONObject json)
	{
		Ad ad = new Ad();
		ad.setId(json.getInt("id"));
		ad.setName(json.getString("name"));
		ad.setStatus(json.getString("status"));
		ad.setDuration(json.getInt("duration"));
		ad.setSize(json.getLong("size"));
		ad.setCode(json.getString("code"));
		return ad;
	}
	
}
