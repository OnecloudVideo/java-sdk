/*
 * File Name  : ClientException.java
 * Authors    : lunjz*
 * Stage      : Implementation
 * Created    : Aug 11, 2015 3:44:25 PM
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
public class ClientException extends Exception
{

	private static final long serialVersionUID = 5861212644586047353L;
	
	public ClientException(Exception e)
	{
		super(e);
	}
	
	public ClientException(String msg)
	{
		super(msg);
	}

}
