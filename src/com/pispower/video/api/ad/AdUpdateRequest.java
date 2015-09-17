/*
 * File Name  : AdUpdateRequest.java
 * Authors    : lunjz*
 * Stage      : Implementation
 * Created    : Aug 12, 2015 6:24:04 PM
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
public class AdUpdateRequest
{
	private Integer id;
	private String name;
	
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
}
