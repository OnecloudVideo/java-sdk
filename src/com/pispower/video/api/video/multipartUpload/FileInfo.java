/*
 * File Name  : FileMultipartUpload.java
 * Authors    : jinjin.wang*
 * Stage      : Implementation
 * Created    : Jun 3, 2015 12:20:08 PM
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
public class FileInfo
{
	private String uploadId;

	private String fileName;
	
	private String fileMD5;
	
	/**
	 * @return the uploadId
	 */
	public String getUploadId()
	{
		return uploadId;
	}

	/**
	 * @param uploadId the uploadId to set
	 */
	public void setUploadId(String uploadId)
	{
		this.uploadId = uploadId;
	}

	/**
	 * @return the fileName
	 */
	public String getFileName()
	{
		return fileName;
	}

	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName)
	{
		this.fileName = fileName;
	}

	/**
	 * @return the fileMD5
	 */
	public String getFileMD5()
	{
		return fileMD5;
	}

	/**
	 * @param fileMD5 the fileMD5 to set
	 */
	public void setFileMD5(String fileMD5)
	{
		this.fileMD5 = fileMD5;
	}

}
