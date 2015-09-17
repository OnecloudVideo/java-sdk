/*
 * File Name  : AdvertiseAddRequest.java
 * Authors    : lunjz*
 * Stage      : Implementation
 * Created    : Aug 12, 2015 7:15:05 PM
 * Copyright  : Copyright © 2009 OneCloud Co., Ltd.  All rights reserved.
 *
 * This software is the confidential and proprietary information of 
 * OneCloud Co., Ltd. ("Confidential Information").  You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with OneCloud.
 */

package com.pispower.video.api.advertise;

import java.util.Date;
import java.util.List;

/**
 * This class is used for
 *
 */
public class AdvertiseAddRequest
{
	private String name;
	private Integer adId;
	private String position;
	private Date onlineDate;
	private Date offlineDate;
	private String link;
	private List<Integer> catalogIds;
	
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public Integer getAdId()
	{
		return adId;
	}
	public void setAdId(Integer adId)
	{
		this.adId = adId;
	}
	public String getPosition()
	{
		return position;
	}
	public void setPosition(String position)
	{
		this.position = position;
	}
	public Date getOnlineDate()
	{
		return onlineDate;
	}
	public void setOnlineDate(Date onlineDate)
	{
		this.onlineDate = onlineDate;
	}
	public Date getOfflineDate()
	{
		return offlineDate;
	}
	public void setOfflineDate(Date offlineDate)
	{
		this.offlineDate = offlineDate;
	}
	public String getLink()
	{
		return link;
	}
	public void setLink(String link)
	{
		this.link = link;
	}
	public List<Integer> getCatalogIds()
	{
		return catalogIds;
	}
	public void setCatalogIds(List<Integer> catalogIds)
	{
		this.catalogIds = catalogIds;
	}
	
}
