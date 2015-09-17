/*
 * File Name  : FileInfoListRequest.java
 * Authors    : jinjin.wang*
 * Stage      : Implementation
 * Created    : Jun 25, 2015 5:32:11 PM
 * Copyright  : Copyright © 2009 OneCloud Co., Ltd.  All rights reserved.
 *
 * This software is the confidential and proprietary information of 
 * OneCloud Co., Ltd. ("Confidential Information").  You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with OneCloud.
 */

package com.pispower.video.api.video.multipartUpload;

/**
 * This class is used for
 *
 */
public class ServerFileListRequest
{
	private String fileNameLike;
	
	private String fileMD5Equal;

	/**
	 * @return the fileNameLike
	 */
	public String getFileNameLike()
	{
		return fileNameLike;
	}

	/**
	 * @param fileNameLike the fileNameLike to set
	 */
	public void setFileNameLike(String fileNameLike)
	{
		this.fileNameLike = fileNameLike;
	}

	/**
	 * @return the fileMD5Equal
	 */
	public String getFileMD5Equal()
	{
		return fileMD5Equal;
	}

	/**
	 * @param fileMD5Equal the fileMD5Equal to set
	 */
	public void setFileMD5Equal(String fileMD5Equal)
	{
		this.fileMD5Equal = fileMD5Equal;
	}
	
	
}
