/*
 * File Name  : PispowerException.java
 * Authors    : jinjin.wang*
 * Stage      : Implementation
 * Created    : May 28, 2015 11:39:07 AM
 * Copyright  : Copyright © 2009 OneCloud Co., Ltd.  All rights reserved.
 *
 * This software is the confidential and proprietary information of 
 * OneCloud Co., Ltd. ("Confidential Information").  You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with OneCloud.
 */

package com.pispower.video.api.internal;

/**
 * This class is used for
 * 
 */
public class PispowerAPIException extends Exception
{
	private static final long serialVersionUID = 5680454760828338398L;
	
	private final Integer code;
		
	public PispowerAPIException(Integer code, String message)
	{
		super(message);
		this.code = code;
	}

	public PispowerAPIException(String message)
	{
		super(message);
		this.code = null;
	}

	/**
	 * @return the code
	 */
	public Integer getCode()
	{
		return code;
	}

}
