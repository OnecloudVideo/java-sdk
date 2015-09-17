/*
 * File Name  : CatalogInfoBean.java
 * Authors    : jinjin.wang*
 * Stage      : Implementation
 * Created    : May 22, 2015 5:54:15 PM
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
public class Catalog
{
	private Integer id;

	private String name;

	private Integer videoNumber;

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
	 * @return the videoNumber
	 */
	public Integer getVideoNumber()
	{
		return videoNumber;
	}

	/**
	 * @param videoNumber the videoNumber to set
	 */
	public void setVideoNumber(Integer videoNumber)
	{
		this.videoNumber = videoNumber;
	}

}
