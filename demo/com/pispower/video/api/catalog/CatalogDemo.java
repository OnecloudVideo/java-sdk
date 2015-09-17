/*
 * File Name  : Catalog.java
 * Authors    : Keijack*
 * Stage      : Implementation
 * Created    : Jul 1, 2014 4:14:23 PM
 * Copyright  : Copyright © 2009 OneCloud Co., Ltd.  All rights reserved.
 *
 * This software is the confidential and proprietary information of 
 * OneCloud Co., Ltd. ("Confidential Information").  You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with OneCloud.
 */

package com.pispower.video.api.catalog;

import java.util.List;

import com.pispower.video.api.internal.ClientException;
import com.pispower.video.api.internal.PispowerAPIException;

/**
 * This class is used for
 * 
 */
public class CatalogDemo
{
	/**
	 * 视频平台提供的 accessKey，该字段会通过公网传输。
	 * 
	 * 请从开发者支持/Restful API页面获取
	 */
	final String accessKey = "";

	/**
	 * 视频平台提供的 accessSecret，该字段用来生成数字签名，
	 * 请注意保密，并且不要通过公网传输。
	 * 
	 * 请从开发者支持/Restful API页面获取
	 */
	final String accessSecret = "";

	public static void main(String[] args)
	{
		CatalogDemo catalogDemo = new CatalogDemo();

		String catalogName = "test-catalog";

		catalogDemo.testAdd(catalogName);

		String nameLike = "";

		catalogDemo.testQuery(nameLike);

		Integer catalogId = 30;

		catalogDemo.testGet(catalogId);

		catalogDemo.testDelete(catalogId);
	}

	public void testQuery(String nameLike)
	{
		CatalogService catalogService = new CatalogService(accessKey, accessSecret);
		CatalogListRequest request = new CatalogListRequest();
		try
		{
			List<Catalog> catalogList = catalogService.list(request);
			System.out.println("query catalog...");
			for (Catalog catalog : catalogList)
			{			
				System.out.println("id:" + catalog.getId() + " name:" + catalog.getName());
			}
		}
		catch (PispowerAPIException | ClientException e)
		{
			e.printStackTrace();
		}
	}

	public void testGet(Integer id)
	{
		CatalogService catalogService = new CatalogService(accessKey, accessSecret);
		 
		try
		{
			Catalog catalog = catalogService.get(id);
			System.out.println("get catalog...");
			System.out.println("name:" + catalog.getName() + " videoNumber:" + catalog.getVideoNumber());
		}
		catch (PispowerAPIException | ClientException e)
		{
			e.printStackTrace();
		}
	}

	public void testAdd(String name)
	{
		CatalogService catalogService = new CatalogService(accessKey, accessSecret);
		
		try
		{
			Catalog catalog = catalogService.add(name);
			System.out.println("add catalog...");
			System.out.println("id:" + catalog.getId() + " name:" + catalog.getName());
		}
		catch (PispowerAPIException e)
		{
			e.printStackTrace();
		}
		catch (ClientException e)
		{
			e.printStackTrace();
		}

	}

	public void testDelete(Integer id)
	{
		CatalogService catalogService = new CatalogService(accessKey, accessSecret);
		try
		{
			catalogService.delete(id);
			System.out.println("delete catalog success");
		}
		catch (PispowerAPIException e)
		{
			e.printStackTrace();
		}
		catch (ClientException e)
		{
			e.printStackTrace();
		}
	}
}
