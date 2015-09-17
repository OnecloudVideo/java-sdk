/*
 * File Name  : FilePart.java
 * Authors    : jinjin.wang*
 * Stage      : Implementation
 * Created    : Jun 15, 2015 12:16:36 PM
 * Copyright  : Copyright © 2009 OneCloud Co., Ltd.  All rights reserved.
 *
 * This software is the confidential and proprietary information of 
 * OneCloud Co., Ltd. ("Confidential Information").  You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with OneCloud.
 */

package com.pispower.video.api.video.multipartUpload;

import java.io.File;

/**
 * This class is used for
 *
 */
public class FilePart
{
	private Integer partNum;
	
	private String partKey;
	
	private String partMD5;
	
	private File partFile;	
	
	public FilePart()
	{
		
	}
	
	public FilePart(Integer partNum, String partKey)
	{
		this.partNum = partNum;
		this.partKey = partKey;
	}
	
	/**
	 * @return the partFile
	 */
	public File getPartFile()
	{
		return partFile;
	}

	/**
	 * @param partFile the partFile to set
	 */
	public void setPartFile(File partFile)
	{
		this.partFile = partFile;
	}

	/**
	 * @return the partNum
	 */
	public Integer getPartNum()
	{
		return partNum;
	}

	/**
	 * @param partNum the partNum to set
	 */
	public void setPartNum(Integer partNum)
	{
		this.partNum = partNum;
	}

	/**
	 * @return the partKey
	 */
	public String getPartKey()
	{
		return partKey;
	}

	/**
	 * @param partKey the partKey to set
	 */
	public void setPartKey(String partKey)
	{
		this.partKey = partKey;
	}

	/**
	 * @return the partMD5
	 */
	public String getPartMD5()
	{
		return partMD5;
	}

	/**
	 * @param partMD5 the partMD5 to set
	 */
	public void setPartMD5(String partMD5)
	{
		this.partMD5 = partMD5;
	}

}
