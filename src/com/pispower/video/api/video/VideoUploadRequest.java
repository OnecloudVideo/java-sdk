/*
 * File Name  : VideoUploadBean.java
 * Authors    : jinjin.wang*
 * Stage      : Implementation
 * Created    : May 28, 2015 10:49:50 AM
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

/**
 * This class is used for
 * 
 */
public class VideoUploadRequest
{
	private Integer catalogId;
	
	private String name;

	private String description;

	private File file;
	

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
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @return the description
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description)
	{
		this.description = description;
	}

}
