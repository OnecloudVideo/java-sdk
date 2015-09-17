/*
 * File Name  : CatalogOperation.java
 * Authors    : jinjin.wang*
 * Stage      : Implementation
 * Created    : May 27, 2015 2:29:01 PM
 * Copyright  : Copyright © 2009 OneCloud Co., Ltd.  All rights reserved.
 *
 * This software is the confidential and proprietary information of 
 * OneCloud Co., Ltd. ("Confidential Information").  You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with OneCloud.
 */

package com.pispower.video.api.catalog;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import onecloud.web.support.QueryString;

import com.pispower.video.api.internal.*;

/**
 * This class is used for
 * 
 */
public class CatalogService
{
	private final Client client;

	public CatalogService(String accessKey, String accessSecret)
	{
		client = new Client(accessKey, accessSecret);
	}

	/**
	 * 根据id查询Catalog
	 * 
	 * @param id Catalog的唯一标识
	 * @return Catalog 包含name,videoNumber字段, 但id字段的值为空
	 * @throws PispowerAPIException
	 * @throws IOException
	 */
	public Catalog get(Integer id)
		throws PispowerAPIException, ClientException
	{
		final QueryString queryString = new QueryString();
		queryString.addParameter("catalogId", id.toString());

		final JSONObject jsonObject = client.get("/catalog/get.api", queryString);

		if (jsonObject.getString("statusCode").equals("0"))
		{
			Catalog catalog = (Catalog) JSONObject.toBean(jsonObject, Catalog.class);
			return catalog;
		}
		else
		{
			throw new PispowerAPIException(jsonObject.getInt("statusCode"), jsonObject.getString("message"));
		}
	}

	/**
	 * 列出Catalog
	 * 
	 * @param request
	 * @return Catalog列表，包含id,name字段,但videoNumber字段的值为空
	 * @throws PispowerAPIException
	 * @throws IOException
	 */
	public List<Catalog> list(CatalogListRequest request)
		throws PispowerAPIException, ClientException
	{
		final QueryString queryString = new QueryString();

		if (null != request.getNameLike() && !"".equals(request.getNameLike()))
		{
			queryString.addParameter("nameLike", request.getNameLike());
		}

		if (null != request.getPage())
		{
			queryString.addParameter("page", request.getPage().toString());
		}
		if (null != request.getMaxResult())
		{
			queryString.addParameter("maxResult", request.getMaxResult().toString());
		}

		final JSONObject jsonObject = client.get("/catalog/list.api", queryString);

		if (jsonObject.getString("statusCode").equals("0"))
		{

			List<Catalog> catalogList = new LinkedList<>();
			JSONArray catalogs = (JSONArray) jsonObject.get("catalogs");

			for (Object object : catalogs)
			{
				JSONObject jo = (JSONObject) object;

				Catalog catalog = (Catalog) JSONObject.toBean(jo, Catalog.class);
				catalogList.add(catalog);
			}
			return catalogList;
		}
		else
		{
			throw new PispowerAPIException(jsonObject.getInt("statusCode"), jsonObject.getString("message"));
		}
	}

	/**
	 * 增加一个Catalog
	 * 
	 * @param name Catalog的名称
	 * @return Catalog 包含id,name字段，但videoNumber字段的值为空
	 * @throws PispowerAPIException
	 * @throws IOException
	 */
	public Catalog add(String name)
		throws PispowerAPIException, ClientException
	{
		final QueryString queryString = new QueryString();
		queryString.addParameter("name", name);

		final JSONObject jsonObject = client.post("/catalog/create.api", queryString);

		if (jsonObject.getString("statusCode").equals("0"))
		{
			Catalog catalog = (Catalog) JSONObject.toBean(jsonObject, Catalog.class);
			return catalog;
		}
		else
		{
			throw new PispowerAPIException(jsonObject.getInt("statusCode"), jsonObject.getString("message"));
		}
	}

	/**
	 * 删除一个Catalog
	 * 
	 * @param id Catalog的唯一标识
	 * @throws PispowerAPIException
	 * @throws IOException
	 */
	public void delete(Integer id)
		throws PispowerAPIException, ClientException
	{
		final QueryString queryString = new QueryString();
		queryString.addParameter("catalogId", id.toString());

		final JSONObject jsonObject = client.post("/catalog/delete.api", queryString);

		if (!jsonObject.getString("statusCode").equals("0"))
		{
			throw new PispowerAPIException(jsonObject.getInt("statusCode"), jsonObject.getString("message"));
		}
	}
}
