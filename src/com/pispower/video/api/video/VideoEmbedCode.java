/*
 * File Name  : VideoEmedCode.java
 * Authors    : jinjin.wang*
 * Stage      : Implementation
 * Created    : May 29, 2015 10:42:49 AM
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
public class VideoEmbedCode
{
	private String resolution;

	private String autoAdaptionCode;

	private String flashCode;

	private String html5Code;
	
	private String portableCode;

	private String clarity;
	
	private String filePath;
	

	/**
	 * @return the portableCode
	 */
	public String getPortableCode()
	{
		return portableCode;
	}

	/**
	 * @param portableCode the portableCode to set
	 */
	public void setPortableCode(String portableCode)
	{
		this.portableCode = portableCode;
	}

	/**
	 * @return the filePath
	 */
	public String getFilePath()
	{
		return filePath;
	}

	/**
	 * @param filePath the filePath to set
	 */
	public void setFilePath(String filePath)
	{
		this.filePath = filePath;
	}

	/**
	 * @return the clarity
	 */
	public String getClarity()
	{
		return clarity;
	}

	/**
	 * @param clarity the clarity to set
	 */
	public void setClarity(String clarity)
	{
		this.clarity = clarity;
	}

	/**
	 * @return the resolution
	 */
	public String getResolution()
	{
		return resolution;
	}

	/**
	 * @param resolution the resolution to set
	 */
	public void setResolution(String resolution)
	{
		this.resolution = resolution;
	}

	/**
	 * @return the autoAdaptionCode
	 */
	public String getAutoAdaptionCode()
	{
		return autoAdaptionCode;
	}

	/**
	 * @param autoAdaptionCode the autoAdaptionCode to set
	 */
	public void setAutoAdaptionCode(String autoAdaptionCode)
	{
		this.autoAdaptionCode = autoAdaptionCode;
	}

	/**
	 * @return the flashCode
	 */
	public String getFlashCode()
	{
		return flashCode;
	}

	/**
	 * @param flashCode the flashCode to set
	 */
	public void setFlashCode(String flashCode)
	{
		this.flashCode = flashCode;
	}

	/**
	 * @return the html5Code
	 */
	public String getHtml5Code()
	{
		return html5Code;
	}

	/**
	 * @param html5Code the html5Code to set
	 */
	public void setHtml5Code(String html5Code)
	{
		this.html5Code = html5Code;
	}

}
