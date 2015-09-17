/*
 * File Name  : PartsInfo.java
 * Authors    : jinjin.wang*
 * Stage      : Implementation
 * Created    : Jun 9, 2015 5:47:15 PM
 * Copyright  : Copyright © 2009 OneCloud Co., Ltd.  All rights reserved.
 *
 * This software is the confidential and proprietary information of 
 * OneCloud Co., Ltd. ("Confidential Information").  You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with OneCloud.
 */

package com.pispower.video.api.video.multipartUpload;

import java.util.List;

/**
 * This class is used for
 *
 */
public class MultipartFileParts
{
	private List<FilePart> unfinishedParts;
	
	private List<FilePart> finishedParts;
	
	private List<FilePart> errorParts;

	/**
	 * @return the unfinishedParts
	 */
	public List<FilePart> getUnfinishedParts()
	{
		return unfinishedParts;
	}

	/**
	 * @param unfinishedParts the unfinishedParts to set
	 */
	public void setUnfinishedParts(List<FilePart> unfinishedParts)
	{
		this.unfinishedParts = unfinishedParts;
	}

	/**
	 * @return the finishedParts
	 */
	public List<FilePart> getFinishedParts()
	{
		return finishedParts;
	}

	/**
	 * @param finishedParts the finishedParts to set
	 */
	public void setFinishedParts(List<FilePart> finishedParts)
	{
		this.finishedParts = finishedParts;
	}

	/**
	 * @return the errorParts
	 */
	public List<FilePart> getErrorParts()
	{
		return errorParts;
	}

	/**
	 * @param errorParts the errorParts to set
	 */
	public void setErrorParts(List<FilePart> errorParts)
	{
		this.errorParts = errorParts;
	}
	
}
