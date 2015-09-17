/*
 * File Name  : MultipartUploadDemo.java
 * Authors    : Keijack*
 * Stage      : Implementation
 * Created    : Jul 7, 2014 10:51:08 AM
 * Copyright  : Copyright © 2009 OneCloud Co., Ltd.  All rights reserved.
 *
 * This software is the confidential and proprietary information of 
 * OneCloud Co., Ltd. ("Confidential Information").  You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with OneCloud.
 */

package com.pispower.video.api.video.multipartUpload;

import java.io.File;

import com.pispower.video.api.video.Video;
import com.pispower.video.api.video.VideoEmbedCode;

/**
 * This class is used for
 * 
 */
public class MultipartUploadDemo
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

	private static final int PART_FILE_SIZE = 4 * 1024 * 1024; // 4M

	// 请输入真实的视频文件路径
	private final File file = new File("");

	public static void main(final String[] args)
	{
		final MultipartUploadDemo multipartUploadDemo = new MultipartUploadDemo();
		try
		{
			multipartUploadDemo.upload();
		}
		catch (final Exception e)
		{
			e.printStackTrace();
		}
	}

	public void upload()
		throws Exception
	{
		MultipartUploadService multipartService = new MultipartUploadService(accessKey, accessSecret);

		FileUploadRequest request = new FileUploadRequest();
		request.setFile(file);
		request.setPartSize(PART_FILE_SIZE);

		Video video = multipartService.upload(request);
		System.out.println("multipart upload...");
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
	}
	
}
