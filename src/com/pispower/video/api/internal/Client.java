/*
 * File Name  : Client.java
 * Authors    : Keijack*
 * Stage      : Implementation
 * Created    : Jul 1, 2014 3:32:34 PM
 * Copyright  : Copyright © 2009 OneCloud Co., Ltd.  All rights reserved.
 *
 * This software is the confidential and proprietary information of 
 * OneCloud Co., Ltd. ("Confidential Information").  You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with OneCloud.
 */

package com.pispower.video.api.internal;

import java.io.*;
import java.net.URLEncoder;
import java.util.Map;

import net.sf.json.JSONObject;
import onecloud.common.MD5;
import onecloud.web.support.QueryString;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.*;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 * 
 * 一个与 Vocchio OVP 视频平台连接的客户端。
 * 
 */
public class Client
{
	/**
	 * 视频平台的地址。
	 */
	private static final String API_HOST = "https://video.pispower.com";

	/**
	 * 视频平台提供的 accessKey，该字段会通过公网传输。
	 * 
	 * 请从ovp 开发者支持/Restful API页面获取
	 */
	private String accessKey;

	/**
	 * 视频平台提供的 accessSecret，该字段用来生成数字签名，注意保密，
	 * 并且不要通过公网传输。
	 * 
	 * 请从ovp 开发者支持/Restful API页面获取
	 */
	private String accessSecret;

	public Client()
	{

	}

	public Client(String accessKey, String accessSecret)
	{
		this.accessKey = accessKey;
		this.accessSecret = accessSecret;
	}

	/**
	 * 
	 * 视频平台中要求为 GET 的接口使用的方法。
	 * 
	 * @param apiContext
	 * @param queryString
	 * @return
	 * @throws ClientException 
	 * @throws IOException
	 */
	public JSONObject get(final String apiContext, final QueryString queryString) throws ClientException
	{
		try
		{
			return excute(RequestBuilder.get(), apiContext, queryString);
		}
		catch (IOException e)
		{
			throw new ClientException(e);
		}
	}

	/**
	 * 
	 * 视频平台要求为 POST 的接口使用的方法。
	 * 
	 * @param apiContext
	 * @param queryString
	 * @return
	 * @throws ClientException 
	 * @throws IOException
	 */
	public JSONObject post(final String apiContext, final QueryString queryString) throws ClientException
	{
		try
		{
			return excute(RequestBuilder.post(), apiContext, queryString);
		}
		catch (IOException e)
		{
			throw new ClientException(e);
		}
	}

	/**
	 * 
	 * 提交一个方法，并且解析视频平台传回的 JSON 到 JSONObject。
	 * 
	 * @param requestBuilder
	 * @param apiContext
	 * @param queryString
	 * @return
	 * @throws IOException
	 */
	private JSONObject excute(final RequestBuilder requestBuilder, final String apiContext, 
			final QueryString queryString)
		throws IOException
	{
		// 增加每个连接都必须传递的约定参数，包括 accessKey、time、sign
		final QueryString aQueryString = addAditionParameters(queryString);

		for (final Map.Entry<String, String[]> entry : aQueryString.getParameterMap().entrySet())
		{
			final String key = entry.getKey();
			for (final String val : entry.getValue())
			{
				// 按照平台规定，所有的参数必须进行一次 UTF8 转码。
				requestBuilder.addParameter(key, encodeToUTF8(val));
			}
		}

		final CloseableHttpClient httpClient = HttpClients.createDefault();
		try
		{
			final String context = this.formatContext(apiContext);
			final HttpUriRequest req = requestBuilder.setUri(API_HOST + context).build();

			final CloseableHttpResponse res = httpClient.execute(req);
			return getJSONFromResponse(res);
		}
		finally
		{
			httpClient.close();
		}
	}

	private String formatContext(final String apiContext)
	{
		if (apiContext.startsWith("/"))
		{
			return apiContext;
		}
		else
		{
			return "/" + apiContext;
		}
	}

	/**
	 * 
	 * 将从视频平台返回的数据转换为JSON对象。
	 * 
	 * @param res
	 * @return
	 * @throws IOException
	 */
	private JSONObject getJSONFromResponse(final CloseableHttpResponse res)
		throws IOException
	{
		if (res.getStatusLine().getStatusCode() != 200)
		{
			throw new IOException("error: code is " + res.getStatusLine().getStatusCode());
		}

		return JSONObject.fromObject(EntityUtils.toString(res.getEntity(), "utf-8"));
	}

	/**
	 * 
	 * 增加平台规定的必传参数：accessKey、time、sign
	 * 
	 * @param queryString
	 * @return
	 */
	private QueryString addAditionParameters(final QueryString queryString)
	{
		final QueryString aQueryString = queryString.clone();
		aQueryString.addParameter("accessKey", accessKey);
		aQueryString.addParameter("time", String.valueOf(System.currentTimeMillis()));

		final String sign = getSign(aQueryString);
		aQueryString.addParameter("sign", sign);
		System.out.println(aQueryString);
		return aQueryString;
	}

	/**
	 * 计算数字签名。
	 * 
	 * @param queryString
	 * @return
	 */
	private String getSign(final QueryString queryString)
	{
		String signOri = this.accessSecret;
		for (final Map.Entry<String, String[]> entry : queryString.getSortedParameterMap().entrySet())
		{
			final String key = entry.getKey();
			for (final String val : entry.getValue())
			{
				signOri += key + val;
			}
		}
		signOri += accessSecret;
		return MD5.getMd5String(signOri);

	}

	/**
	 * 对字符串进行 UTF8 转码
	 * 
	 * @param val
	 * @return
	 */
	private String encodeToUTF8(final String val)
	{
		try
		{
			return URLEncoder.encode(val, "utf-8");
		}
		catch (final UnsupportedEncodingException e)
		{
			e.printStackTrace();
			return val;
		}
	}

	/**
	 * 
	 * 上传文件到视频平台
	 * 
	 * @param apiContext
	 * @param queryString
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public JSONObject post(final String apiContext,
			final QueryString queryString, final File file)
		throws ClientException
	{
		// 文件参数
		final MultipartEntityBuilder builder = MultipartEntityBuilder.create().addBinaryBody("uploadFile", file);

		// 增加每个连接都必须传递的约定参数，包括 accessKey、time、sign
		final QueryString aQueryString = addAditionParameters(queryString);
		for (final Map.Entry<String, String[]> entry : aQueryString.getParameterMap().entrySet())
		{
			final String key = entry.getKey();
			for (final String val : entry.getValue())
			{
				// 按照平台规定，所有的参数必须进行一次 UTF8 转码。
				builder.addTextBody(key, encodeToUTF8(val));
			}
		}

		final String context = this.formatContext(apiContext);
		final HttpPost post = new HttpPost(API_HOST + context);

		final HttpEntity entity = builder.build();
		post.setEntity(entity);

		try
		{
			final CloseableHttpClient httpClient = HttpClients.createDefault();
			try
			{
				final CloseableHttpResponse res = httpClient.execute(post);
				return getJSONFromResponse(res);
			}
			finally
			{
				httpClient.close();
			}
		}
		catch (IOException e)
		{
			throw new ClientException(e);
		}
	}
}
