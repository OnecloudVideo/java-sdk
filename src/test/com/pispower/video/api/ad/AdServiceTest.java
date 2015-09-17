/*
 * File Name  : AdServiceTest.java
 * Authors    : lunjz*
 * Stage      : Implementation
 * Created    : Aug 12, 2015 5:54:38 PM
 * Copyright  : Copyright © 2009 OneCloud Co., Ltd.  All rights reserved.
 *
 * This software is the confidential and proprietary information of 
 * OneCloud Co., Ltd. ("Confidential Information").  You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with OneCloud.
 */

package com.pispower.video.api.ad;

import static org.junit.Assert.*;

import java.io.File;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.pispower.video.api.internal.ClientException;
import com.pispower.video.api.internal.PispowerAPIException;

/**
 * This class is used for
 *
 */
public class AdServiceTest
{
	private AdService adService;
	
	@Before
	public void before()
	{
		String key = "";
		String secret = "";
		adService = new AdService(key, secret);
	}
	
	@Test
	public void testUpload()
	{
		String path = getClass().getResource("").getPath();
		File file = new File(path + "video.flv");
		AdUploadRequest request = new AdUploadRequest();
		request.setFile(file);
		try
		{
			Ad ad = adService.upload(request);
			assertNotNull(ad);
			assertEquals("video.flv", ad.getName());
		}
		catch (ClientException | PispowerAPIException e)
		{
			fail();
		}
	}

	@Test
	public void testList()
	{
		try
		{
			AdListRequest request = new AdListRequest();
			request.setStatus("AUDIT_SUCCESS");
			List<Ad> list = adService.list(request);
			assertTrue(!list.isEmpty());
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
			String path = getClass().getResource("").getPath();
			File file = new File(path + "video.flv");
			AdUploadRequest uploadReq = new AdUploadRequest();
			uploadReq.setFile(file);
			Ad uploadAd = adService.upload(uploadReq);
			
			Ad ad = adService.get(uploadAd.getId());
			assertEquals(uploadAd.getName(), ad.getName());
		}
		catch (ClientException | PispowerAPIException e)
		{
			fail();
		}
	}
	
	@Test
	public void testUpdate()
	{
		AdUpdateRequest request = new AdUpdateRequest();
		request.setId(59);
		request.setName("abcd");
		try
		{
			assertTrue(adService.update(request));
			Ad ad = adService.get(59);
			assertEquals("abcd.flv", ad.getName());
		}
		catch (ClientException | PispowerAPIException e)
		{
			fail();
		}
	}
	
	@Test
	public void testDelete()
	{
		try
		{
			adService.delete(58);
			Ad ad = adService.get(58);
			assertEquals(null, ad);
		}
		catch (ClientException | PispowerAPIException e)
		{
			fail();
		}
	}
}
