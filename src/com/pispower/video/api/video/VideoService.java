/*
 * File Name  : VideoOperation.java
 * Authors    : jinjin.wang*
 * Stage      : Implementation
 * Created    : May 27, 2015 2:29:25 PM
 * Copyright  : Copyright © 2009 OneCloud Co., Ltd.  All rights reserved.
 *
 * This software is the confidential and proprietary information of 
 * OneCloud Co., Ltd. ("Confidential Information").  You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with OneCloud.
 */

package com.pispower.video.api.video;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import onecloud.web.support.QueryString;

import com.pispower.video.api.internal.*;

/**
 * This class is used for
 * 
 */
public class VideoService
{
	private final Client client;

	public VideoService(String accessKey, String accessSecret)
	{
		client = new Client(accessKey, accessSecret);
	}

	/**
	 * 上传视频
	 * 
	 * @param bean
	 * @return Video 包含id,name,size,type,status,duration,catalogId,catalogName,
	 *         VideoEmbedCode列表。
	 * @throws PispowerAPIException
	 * @throws IOException
	 * @throws ClientException
	 */
	public Video upload(VideoUploadRequest request)
		throws PispowerAPIException, ClientException
	{
		final QueryString queryString = new QueryString();
		queryString.addParameter("name", request.getName());
		if (null != request.getDescription() && !"".equals(request.getDescription()))
		{
			queryString.addParameter("description", request.getDescription());
		}
		if (null != request.getCatalogId())
		{
			queryString.addParameter("catalogId", String.valueOf(request.getCatalogId()));
		}
		final JSONObject jsonObject = client.post("/video/upload.api", queryString, request.getFile());

		if (jsonObject.getString("statusCode").equals("0"))
		{
			Video video = new Video();
			video.setId(jsonObject.getInt("videoId"));
			video.setName(jsonObject.getString("name"));
			video.setSize(jsonObject.getLong("size"));
			video.setType(jsonObject.getString("type"));
			video.setDuration(jsonObject.getLong("duration"));
			video.setStatus(jsonObject.getString("status"));
			JSONArray embedCodes = (JSONArray) jsonObject.get("embedCodes");
			List<VideoEmbedCode> embedCodeList = new LinkedList<>();

			for (Object o : embedCodes)
			{
				JSONObject jo = (JSONObject) o;
				VideoEmbedCode embedCode = (VideoEmbedCode) JSONObject.toBean(jo, VideoEmbedCode.class);
				embedCodeList.add(embedCode);
			}
			video.setEmbedCodes(embedCodeList);
			video.setCatalogId(jsonObject.getInt("catalogId"));
			video.setCatalogName(jsonObject.getString("catalogName"));
			return video;
		}
		else
		{
			throw new PispowerAPIException(jsonObject.getInt("statusCode"), jsonObject.getString("message"));
		}
	}

	/**
	 * 根据id查询视频
	 * 
	 * @param id Video的id
	 * @return Video
	 *         包含name,size,type,status,duration,description,catalogId,catalogName
	 *         ,VideoEmbedCode列表。
	 * @throws PispowerAPIException
	 * @throws IOException
	 */
	public Video get(Integer id)
		throws PispowerAPIException, ClientException
	{
		final QueryString qs = new QueryString();
		qs.addParameter("videoId", id.toString());

		final JSONObject jsonObject = client.get("/video/get.api", qs);

		if (jsonObject.getString("statusCode").equals("0"))
		{
			Video video = new Video();
			video.setName(jsonObject.getString("name"));
			video.setSize(jsonObject.getLong("size"));
			video.setType(jsonObject.getString("type"));
			video.setStatus(jsonObject.getString("status"));
			video.setDuration(jsonObject.getLong("duration"));
			video.setCatalogId(jsonObject.getInt("catalogId"));
			video.setCatalogName(jsonObject.getString("catalogName"));

			JSONArray embedCodes = (JSONArray) jsonObject.get("embedCodes");
			List<VideoEmbedCode> embedCodeList = new LinkedList<>();
			for (Object o : embedCodes)
			{
				JSONObject jo = (JSONObject) o;
				VideoEmbedCode embedCode = (VideoEmbedCode) JSONObject.toBean(jo, VideoEmbedCode.class);
				embedCodeList.add(embedCode);
			}
			video.setEmbedCodes(embedCodeList);
			return video;
		}
		else
		{
			throw new PispowerAPIException(jsonObject.getInt("statusCode"), jsonObject.getString("message"));
		}
	}

	/**
	 * 列出视频
	 * 
	 * @param request
	 * @return 
	 *         Video列表，每个Video对象包含name,size,type,status,duration,catalogId,catalogName
	 *         。
	 * @throws PispowerAPIException
	 * @throws IOException
	 */
	public List<Video> list(VideoListRequest request)
		throws PispowerAPIException, ClientException
	{
		final QueryString qs = new QueryString();
		if (null != request.getCatalogNameLike() && !"".equals(request.getCatalogNameLike()))
		{
			qs.addParameter("catalogNameLike", request.getCatalogNameLike());
		}
		if (null != request.getNameLike() && !"".equals(request.getNameLike()))
		{
			qs.addParameter("nameLike", request.getNameLike());
		}
		if (null != request.getCatalogId())
		{
			qs.addParameter("catalogId", String.valueOf(request.getCatalogId()));
		}

		if (null != request.getPage())
		{
			qs.addParameter("page", String.valueOf(request.getPage()));
		}

		if (null != request.getMaxResult())
		{
			qs.addParameter("maxResult", String.valueOf(String.valueOf(request.getMaxResult())));
		}

		final JSONObject jsonObject = client.get("/video/list.api", qs);

		if (jsonObject.getString("statusCode").equals("0"))
		{
			JSONArray videos = (JSONArray) jsonObject.get("videos");
			List<Video> videoList = new LinkedList<>();

			for (Object object : videos)
			{
				JSONObject jo = (JSONObject) object;
				Video video = (Video) JSONObject.toBean(jo, Video.class);

				videoList.add(video);
			}
			return videoList;
		}
		else
		{
			throw new PispowerAPIException(jsonObject.getInt("statusCode"), jsonObject.getString("message"));
		}
	}

	/**
	 * 更新视频的名字或描述信息
	 * 
	 * @param request 包含Video的名称，描述信息
	 * @throws PispowerAPIException
	 * @throws IOException
	 */
	public void update(VideoUpdateRequest request)
		throws PispowerAPIException, ClientException
	{
		final QueryString qs = new QueryString();
		qs.addParameter("videoId", request.getVideoId().toString());
		qs.addParameter("name", request.getName());

		if (null != request.getDescription() && !"".equals(request.getDescription()))
		{
			qs.addParameter("description", request.getDescription());
		}
		final JSONObject jsonObject = client.post("/video/update.api", qs);

		if (!jsonObject.getString("statusCode").equals("0"))
		{
			throw new PispowerAPIException(jsonObject.getInt("statusCode"), jsonObject.getString("message"));
		}
	}

	/**
	 * 删除视频
	 * 
	 * @param id Video的唯一标识
	 * @throws PispowerAPIException
	 * @throws IOException
	 */
	public void delete(Integer id)
		throws PispowerAPIException, ClientException
	{
		final QueryString qs = new QueryString();
		qs.addParameter("videoId", id.toString());

		final JSONObject jsonObject = client.post("/video/delete.api", qs);

		if (!jsonObject.getString("statusCode").equals("0"))
		{
			throw new PispowerAPIException(jsonObject.getInt("statusCode"), jsonObject.getString("message"));
		}
	}
}
