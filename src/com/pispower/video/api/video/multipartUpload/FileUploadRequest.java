/*
 * File Name  : FileMultipartUploadRequest.java
 * Authors    : jinjin.wang*
 * Stage      : Implementation
 * Created    : Jun 3, 2015 11:20:51 AM
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

/**
 * This class is used for
 *
 */
public class FileUploadRequest
{
	private Integer catalogId;
	
	private File file;	
	
	private int partSize;
	
	
	/**
	 * @return the catalogId
	 */
	public Integer getCatalogId()
	{
		return catalogId;
	}

	/**
	 * @param catalogId the catalogId to set
	 */
	public void setCatalogId(Integer catalogId)
	{
		this.catalogId = catalogId;
	}

	/**
	 * @return the file
	 */
	public File getFile()
	{
		return file;
	}

	/**
	 * @param file the file to set
	 */
	public void setFile(File file)
	{
		this.file = file;
	}
	/**
	 * @return the partSize
	 */
	public int getPartSize()
	{
		return partSize;
	}

	/**
	 * @param partSize the partSize to set
	 */
	public void setPartSize(int partSize)
	{
		this.partSize = partSize;
	}	
}
