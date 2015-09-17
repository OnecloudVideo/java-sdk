/*
 * File Name  : AdvertiseListRequest.java
 * Authors    : lunjz*
 * Stage      : Implementation
 * Created    : Aug 13, 2015 11:39:26 AM
 * Copyright  : Copyright © 2009 OneCloud Co., Ltd.  All rights reserved.
 *
 * This software is the confidential and proprietary information of 
 * OneCloud Co., Ltd. ("Confidential Information").  You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with OneCloud.
 */

package com.pispower.video.api.advertise;

/**
 * This class is used for
 *
 */
public class AdvertiseListRequest
{
	private String nameLike;
	private String status;
	private Integer page = 1;
	private Integer maxResult = 100;
	
	public String getNameLike()
	{
		return nameLike;
	}
	public void setNameLike(String nameLike)
	{
		this.nameLike = nameLike;
	}
	public String getStatus()
	{
		return status;
	}
	public void setStatus(String status)
	{
		this.status = status;
	}
	public Integer getPage()
	{
		return page;
	}
	public void setPage(Integer page)
	{
		this.page = page;
	}
	public Integer getMaxResult()
	{
		return maxResult;
	}
	public void setMaxResult(Integer maxResult)
	{
		this.maxResult = maxResult;
	}

}
