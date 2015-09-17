/*
 * File Name  : VideoInfoBean.java
 * Authors    : jinjin.wang*
 * Stage      : Implementation
 * Created    : May 22, 2015 12:45:58 PM
 * Copyright  : Copyright © 2009 OneCloud Co., Ltd.  All rights reserved.
 *
 * This software is the confidential and proprietary information of 
 * OneCloud Co., Ltd. ("Confidential Information").  You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with OneCloud.
 */

package com.pispower.video.api.video;

import java.util.List;

/**
 * This class is used for
 * 
 */
public class Video
{
	private Integer id;
	
	private String name;

	private long size;
	
	private long duration;

	private String status;

	private String type;
	
	private String description;
	
	private Integer catalogId;
	
	private String catalogName;
	
	private List<VideoEmbedCode> embedCodes;
	
	/**
	 * @return the duration
	 */
	public long getDuration()
	{
		return duration;
	}

	/**
	 * @param duration the duration to set
	 */
	public void setDuration(long duration)
	{
		this.duration = duration;
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

	/**
	 * @return the id
	 */
	public Integer getId()
	{
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id)
	{
		this.id = id;
	}

	/**
	 * @return the embedCodes
	 */
	public List<VideoEmbedCode> getEmbedCodes()
	{
		return embedCodes;
	}

	/**
	 * @param embedCodes the embedCodes to set
	 */
	public void setEmbedCodes(List<VideoEmbedCode> embedCodes)
	{
		this.embedCodes = embedCodes;
	}

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
	 * @return the catalogName
	 */
	public String getCatalogName()
	{
		return catalogName;
	}

	/**
	 * @param catalogName the catalogName to set
	 */
	public void setCatalogName(String catalogName)
	{
		this.catalogName = catalogName;
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
	 * @return the size
	 */
	public long getSize()
	{
		return size;
	}

	/**
	 * @param size the size to set
	 */
	public void setSize(long size)
	{
		this.size = size;
	}

	/**
	 * @return the status
	 */
	public String getStatus()
	{
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status)
	{
		this.status = status;
	}

	/**
	 * @return the type
	 */
	public String getType()
	{
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type)
	{
		this.type = type;
	}

}
