/*
 * File Name  : Ad.java
 * Authors    : lunjz*
 * Stage      : Implementation
 * Created    : Aug 11, 2015 2:34:09 PM
 * Copyright  : Copyright © 2009 OneCloud Co., Ltd.  All rights reserved.
 *
 * This software is the confidential and proprietary information of 
 * OneCloud Co., Ltd. ("Confidential Information").  You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with OneCloud.
 */

package com.pispower.video.api.ad;

/**
 * This class is used for
 *
 */
public class Ad
{
	private Integer id;
	private String name;
	private String status;
	private Integer duration;
	private Long size;
	private String code;
	
	public Integer getId()
	{
		return id;
	}
	public void setId(Integer id)
	{
		this.id = id;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public String getStatus()
	{
		return status;
	}
	public void setStatus(String status)
	{
		this.status = status;
	}
	public Integer getDuration()
	{
		return duration;
	}
	public void setDuration(Integer duration)
	{
		this.duration = duration;
	}
	public Long getSize()
	{
		return size;
	}
	public void setSize(Long size)
	{
		this.size = size;
	}
	public String getCode()
	{
		return code;
	}
	public void setCode(String code)
	{
		this.code = code;
	}

}
