/*
 * File Name  : VideoUpdateBean.java
 * Authors    : jinjin.wang*
 * Stage      : Implementation
 * Created    : May 27, 2015 7:31:34 PM
 * Copyright  : Copyright © 2009 OneCloud Co., Ltd.  All rights reserved.
 *
 * This software is the confidential and proprietary information of 
 * OneCloud Co., Ltd. ("Confidential Information").  You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with OneCloud.
 */

package com.pispower.video.api.video;

/**
 * This class is used for
 * 
 */
public class VideoUpdateRequest
{
	private Integer videoId;

	private String name;

	private String description;

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

	/**
	 * @return the videoId
	 */
	public Integer getVideoId()
	{
		return videoId;
	}

	/**
	 * @param videoId the videoId to set
	 */
	public void setVideoId(Integer videoId)
	{
		this.videoId = videoId;
	}

}
