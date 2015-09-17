/*
 * File Name  : CatalogListRequest.java
 * Authors    : jinjin.wang*
 * Stage      : Implementation
 * Created    : Sep 15, 2015 3:09:01 PM
 * Copyright  : Copyright © 2009 OneCloud Co., Ltd.  All rights reserved.
 *
 * This software is the confidential and proprietary information of 
 * OneCloud Co., Ltd. ("Confidential Information").  You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with OneCloud.
 */

package com.pispower.video.api.catalog;

/**
 * This class is used for
 *
 */
public class CatalogListRequest
{
	String nameLike;
	
	Integer page;
	
	Integer maxResult;

	/**
	 * @return the nameLike
	 */
	public String getNameLike()
	{
		return nameLike;
	}

	/**
	 * @param nameLike the nameLike to set
	 */
	public void setNameLike(String nameLike)
	{
		this.nameLike = nameLike;
	}

	/**
	 * @return the page
	 */
	public Integer getPage()
	{
		return page;
	}

	/**
	 * @param page the page to set
	 */
	public void setPage(Integer page)
	{
		this.page = page;
	}

	/**
	 * @return the maxResult
	 */
	public Integer getMaxResult()
	{
		return maxResult;
	}

	/**
	 * @param maxResult the maxResult to set
	 */
	public void setMaxResult(Integer maxResult)
	{
		this.maxResult = maxResult;
	}

}
