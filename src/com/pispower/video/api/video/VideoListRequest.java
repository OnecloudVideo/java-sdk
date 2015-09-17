/*
 * File Name  : VideoListRequest.java
 * Authors    : jinjin.wang*
 * Stage      : Implementation
 * Created    : Jun 18, 2015 4:01:26 PM
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
public class VideoListRequest
{
	private String nameLike;
	
	private String catalogNameLike;
	
	private Integer catalogId;
	
	private Integer page;
	
	private Integer maxResult;

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
	 * @return the catalogNameLike
	 */
	public String getCatalogNameLike()
	{
		return catalogNameLike;
	}

	/**
	 * @param catalogNameLike the catalogNameLike to set
	 */
	public void setCatalogNameLike(String catalogNameLike)
	{
		this.catalogNameLike = catalogNameLike;
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
