/*
 * File Name  : AdvertiseServiceTest.java
 * Authors    : lunjz*
 * Stage      : Implementation
 * Created    : Aug 13, 2015 8:54:24 AM
 * Copyright  : Copyright © 2009 OneCloud Co., Ltd.  All rights reserved.
 *
 * This software is the confidential and proprietary information of 
 * OneCloud Co., Ltd. ("Confidential Information").  You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with OneCloud.
 */

package com.pispower.video.api.advertise;

import static org.junit.Assert.*;

import java.util.*;

import org.apache.commons.lang.time.DateUtils;
import org.junit.Before;
import org.junit.Test;

import com.pispower.video.api.internal.ClientException;
import com.pispower.video.api.internal.PispowerAPIException;

/**
 * This class is used for
 *
 */
public class AdvertiseServiceTest
{
	private AdvertiseService advertiseService;

	@Before
	public void before()
	{
		String key = "";
		String secret = "";
		advertiseService = new AdvertiseService(key, secret);
	}
	
	@Test
	public void testAdd()
	{
		AdvertiseAddRequest request = new AdvertiseAddRequest();
		request.setAdId(59);
		request.setName("testAdd");
		request.setPosition("HEAD");
		Date now = new Date();
		request.setOnlineDate(DateUtils.addDays(now, 1));
		request.setOfflineDate(DateUtils.addDays(now, 2));
		List<Integer> catalogIds = new ArrayList<>();
		
		request.setCatalogIds(catalogIds);
		try
		{
			Advertise advertise = advertiseService.add(request);
			assertNotNull(advertise);
		}
		catch (ClientException | PispowerAPIException e)
		{
			fail();
		}
	}
	
	@Test
	public void testGet()
	{
		try
		{
			Advertise advertise = advertiseService.get(42);
			assertEquals("wisdom", advertise.getName());
			assertEquals(59, advertise.getAdId().intValue());
			assertEquals(56, advertise.getCatalogIds().get(0).intValue());
			assertEquals("HEAD", advertise.getPosition());
			assertEquals("APPLIED", advertise.getStatus());
		}
		catch (ClientException | PispowerAPIException e)
		{
			fail();
		}
	}
	
	@Test
	public void testList()
	{
		AdvertiseListRequest request = new AdvertiseListRequest();
		try
		{
			List<Advertise> list = advertiseService.list(request);
			assertEquals(1, list.size());
		}
		catch (ClientException | PispowerAPIException e)
		{
			fail();
		}
	}
	
}
