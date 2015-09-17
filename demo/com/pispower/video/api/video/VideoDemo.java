/*
 * File Name  : VideoTest.java
 * Authors    : Keijack*
 * Stage      : Implementation
 * Created    : Jul 2, 2014 4:28:37 PM
 * Copyright  : Copyright © 2009 OneCloud Co., Ltd.  All rights reserved.
 *
 * This software is the confidential and proprietary information of 
 * OneCloud Co., Ltd. ("Confidential Information").  You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with OneCloud.
 */

package com.pispower.video.api.video;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.pispower.video.api.internal.ClientException;
import com.pispower.video.api.internal.PispowerAPIException;

/**
 * This class is used for
 * 
 */
public class VideoDemo
{
	/**
	 * 视频平台提供的 accessKey，该字段会通过公网传输。
	 * 
	 * 请从开发者支持/Restful API页面获取
	 */
	final String accessKey = "";

	/**
	 * 视频平台提供的 accessSecret，该字段用来生成数字签名，
	 *  请注意保密， 并且不要通过公网传输。
	 * 
	 * 请从开发者支持/Restful API页面获取
	 */
	final String accessSecret = "";

	public static void main(String[] args)
		throws IOException
	{
		VideoDemo demo = new VideoDemo();

		demo.testListVideo();

		Integer videoId = demo.testUpload();

		demo.testGetVideo(videoId);

		demo.testUpdate(videoId);

		demo.testDelete(videoId);
	}

	public int testUpload()
	{
		String name = "测试视频.avi";
		String description = "测试描述";
		// 请输入视频全路径
		final File file = new File("");

		VideoUploadRequest videoUploadRequest = new VideoUploadRequest();
		videoUploadRequest.setName(name);
		videoUploadRequest.setDescription(description);
		videoUploadRequest.setFile(file);
		
		VideoService videoService = new VideoService(accessKey, accessSecret);

		try
		{
			Video video = videoService.upload(videoUploadRequest);
			System.out.println("upload video...");
			System.out.println("id:" + video.getId() + " name:" + video.getName() + " status:" + video.getStatus()
					+ " size:" + video.getSize() + " type:" + video.getType() + " duration:" + video.getDuration()
					+ " catalogId:" + video.getCatalogId() + " catalogName:" + video.getCatalogName());

			for (VideoEmbedCode embedCode : video.getEmbedCodes())
			{
				System.out.println("autoAdaptionCode:" + embedCode.getAutoAdaptionCode() + " Clarity:"
						+ embedCode.getClarity() + " FlashCode:" + embedCode.getFlashCode() + " Html5Code:"
						+ embedCode.getHtml5Code() + " Resolution:" + embedCode.getResolution() + " filePath:"
						+ embedCode.getFilePath() + " portableCode:" + embedCode.getPortableCode());
			}
			return video.getId();
		}
		catch (PispowerAPIException | ClientException e)
		{
			e.printStackTrace();
		}
		return -1;
	}

	public void testListVideo()
	{		
		VideoListRequest videoListRequest = new VideoListRequest();
		
		VideoService videoService = new VideoService(accessKey, accessSecret);
		try
		{
			List<Video>  videoList = videoService.list(videoListRequest);
			System.out.println("list...");
			for (Video video : videoList)
			{
				System.out.println(" catalogId:" + video.getCatalogId() + " catalogName:" + video.getCatalogName()
						+ " name:" + video.getName() + " id:" + video.getId() + " status:" +   video.getStatus() +
						" duration:" + video.getDuration() + " size" + video.getSize());
			}
		}
		catch (PispowerAPIException | ClientException e)
		{
			e.printStackTrace();
		}
	}

	public void testGetVideo(Integer videoId)
	{
		VideoService videoService = new VideoService(accessKey, accessSecret);
		
		try
		{
			Video video = videoService.get(videoId);
			System.out.println("get video...");
			System.out.println("name:" + video.getName() + " status:" + video.getStatus() + " size:" 
			+ video.getSize() + " type:" + video.getType() + " duration:" + video.getDuration() 
			+ " description:" + video.getDescription() + " catalogId:" + video.getCatalogId() + 
			" catalogName:" + video.getCatalogName());

			for (VideoEmbedCode embedCode : video.getEmbedCodes())
			{
				System.out.println("autoAdaptionCode:" + embedCode.getAutoAdaptionCode() + " Clarity:"
						+ embedCode.getClarity() + " FlashCode:" + embedCode.getFlashCode() + " Html5Code:"
						+ embedCode.getHtml5Code() + " Resolution:" + embedCode.getResolution() + " filePath:" 
						+ embedCode.getFilePath()  + " portableCode:" + embedCode.getPortableCode());
			}
		}
		catch (PispowerAPIException | ClientException e)
		{
			e.printStackTrace();
		}
	}

	public void testDelete(Integer videoId)
	{
		VideoService videoService = new VideoService(accessKey, accessSecret);
		try
		{
			videoService.delete(videoId);
			System.out.println("delete video success");
		}
		catch (PispowerAPIException | ClientException e)
		{
			e.printStackTrace();
		}
	}

	public void testUpdate(Integer videoId)
	{
		String name = "testName";
		String description = "testDescription";

		VideoUpdateRequest videoUpdateRequest = new VideoUpdateRequest();
		videoUpdateRequest.setVideoId(videoId);
		videoUpdateRequest.setName(name);
		videoUpdateRequest.setDescription(description);

		VideoService videoService = new VideoService(accessKey, accessSecret);
		try
		{
			videoService.update(videoUpdateRequest);
			System.out.println("update video success");
		}
		catch (PispowerAPIException | ClientException e)
		{
			e.printStackTrace();
		}
	}
}
