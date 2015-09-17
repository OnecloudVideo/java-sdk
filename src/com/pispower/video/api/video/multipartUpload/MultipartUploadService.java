/*
 * File Name  : MultipartUploadService.java
 * Authors    : jinjin.wang*
 * Stage      : Implementation
 * Created    : Jun 3, 2015 11:20:27 AM
 * Copyright  : Copyright © 2009 OneCloud Co., Ltd.  All rights reserved.
 *
 * This software is the confidential and proprietary information of 
 * OneCloud Co., Ltd. ("Confidential Information").  You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with OneCloud.
 */

package com.pispower.video.api.video.multipartUpload;

import java.io.*;
import java.util.*;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import onecloud.common.MD5;
import onecloud.web.support.QueryString;

import com.pispower.video.api.internal.*;
import com.pispower.video.api.video.Video;
import com.pispower.video.api.video.VideoEmbedCode;

/**
 * This class is used for
 * 
 */
public class MultipartUploadService
{
	private static final int BUFFER_SIZE = 4 * 1024 * 1024; // 4M

	private final Client client;

	public MultipartUploadService(String accessKey, String accessSecret)
	{
		client = new Client(accessKey, accessSecret);
	}

	/**
	 * 断点续传,查询已经上传的分片，与待上传文件分片比较MD5， 记录未上传的分片，
	 * 上传未上传过的分片，最后合并分片。
	 * 
	 * @param request 包含待上传文件，文件分片大小
	 * @return Video 包含id,name,size,type,status,duration,catalogId,catalogName,
	 *         VideoEmbedCode列表 。
	 * @throws Exception
	 */
	public Video upload(FileUploadRequest request)
		throws Exception
	{
		File file = request.getFile();
		int partFileSize = request.getPartSize();

		// 对文件分片
		File[] files = splitFile(file, partFileSize);

		// 计算各个分片MD5(包含partNum,MD5)
		List<FilePart> localParts = getLocalParts(files);

		// 计算文件MD5
		String fileMD5 = MD5.getFileMD5String(file);

		ServerFileListRequest listRequest = new ServerFileListRequest();
		listRequest.setFileMD5Equal(fileMD5);
		listRequest.setFileNameLike(file.getName());

		// 服务器端已经初始过的文件
		FileInfo serverFileInfo = getServerFileInfo(listRequest);
		// 获得服务器上已经上传的分片
		List<FilePart> serverParts = getServerPartInfos(serverFileInfo);

		// 区分上传完成的分片，未上传的分片，错误的分片
		MultipartFileParts parts = getMultipartFileParts(serverParts, localParts);

		// 删除服务器上错误的分片
		deleteParts(parts.getErrorParts());

		// 已经上传完成的分片记录到一个列表中备用
		List<FilePart> finishedParts = parts.getFinishedParts();

		// 上传未完成的分片
		String uploadId = getUploadId(file.getName(), fileMD5, serverFileInfo);
		List<FilePart> fileParts = uploadParts(uploadId, parts.getUnfinishedParts());

		// 将新上传的数据和已经在服务器的数据进行合并。
		finishedParts.addAll(fileParts);

		String catalogId = "";
		if (null != request.getCatalogId())
		{
			catalogId = request.getCatalogId().toString();
		}
		Video video = completeUpload(uploadId, finishedParts, catalogId);

		// 清除临时文件
		deleteFileParts(files[0].getParentFile());

		return video;
	}

	/**
	 * 中止一个上传进程，调用该接口成功之后，该进程所有上传的块将会从服务器删除
	 * 
	 * @param uploadId 当前上传事件的唯一标识
	 * @return
	 * @throws IOException
	 * @throws PispowerAPIException
	 */
	public String abortUpload(final String uploadId)
		throws PispowerAPIException, ClientException
	{
		final QueryString queryString = new QueryString();
		queryString.addParameter("uploadId", uploadId);

		final JSONObject json = client.post("/video/multipartUpload/abort.api", queryString);

		if (json.getString("statusCode").equals("0"))
		{
			return json.getString("fileName");
		}
		else
		{
			throw new PispowerAPIException(json.getInt("statusCode"), json.getString("message"));
		}
	}

	/**
	 * 文件已被初始化过，返回已有的uploadId,文件未被初始化过，则初始化文件，得到uploadId
	 * 
	 * @param fileName 文件的名字
	 * @param fileMD5 文件的MD5
	 * @param serverFileInfo 服务器上的文件信息
	 * @return
	 * @throws PispowerAPIException
	 * @throws IOException
	 */
	private String getUploadId(String fileName, String fileMD5, FileInfo serverFileInfo)
		throws PispowerAPIException, ClientException
	{
		String uploadId;
		if (serverFileInfo == null)
		{
			uploadId = this.initMultipartUpload(fileName, fileMD5);
		}
		else
		{
			uploadId = serverFileInfo.getUploadId();
		}
		return uploadId;
	}

	/**
	 * 获取服务器上的分片信息列表
	 * 
	 * @param serverFileInfo 服务器上的分片文件信息
	 * @return
	 * @throws PispowerAPIException
	 * @throws IOException
	 */
	private List<FilePart> getServerPartInfos(FileInfo serverFileInfo)
		throws PispowerAPIException, ClientException
	{
		List<FilePart> serverParts;
		if (serverFileInfo == null)
		{
			serverParts = Collections.emptyList();
		}
		else
		{
			serverParts = getParts(serverFileInfo.getUploadId());
		}
		return serverParts;
	}

	/**
	 * 获取服务器上的文件信息
	 * 
	 * @param fileMD5 文件的MD5
	 * @return
	 * @throws PispowerAPIException
	 * @throws IOException
	 */
	private FileInfo getServerFileInfo(ServerFileListRequest request)
		throws PispowerAPIException, ClientException
	{
		List<FileInfo> serverFileList = this.list(request);
		if (serverFileList.size() == 0)
		{
			return null;
		}
		else if (serverFileList.size() == 1)
		{
			return serverFileList.get(0);
		}
		else
		{
			throw new PispowerAPIException("size error: There are " + serverFileList.size() + " in server.");
		}
	}

	/**
	 * 计算本地文件分片的MD5
	 * 
	 * @param files 分片文件
	 * @return
	 * @throws IOException
	 */
	private List<FilePart> getLocalParts(File[] files)
		throws IOException
	{
		List<FilePart> localFileParts = new LinkedList<>();
		for (int i = 0; i < files.length; i++)
		{
			FilePart filePart = new FilePart();
			File file = files[i];
			String filePartMD5 = MD5.getFileMD5String(file);
			// 文件名为 video.[partNum].part
			String[] name = file.getName().split("\\.");

			filePart.setPartMD5(filePartMD5);
			filePart.setPartNum(Integer.parseInt(name[1]));
			filePart.setPartFile(file);
			localFileParts.add(filePart);
		}
		return localFileParts;
	}

	/**
	 * 将本服务器上分片MD5与地文件分片比较，若服务器上的一个分片在本地分片中，
	 * 则加入finishedParts中，并从localParts中对应删除，否则加入errorParts中
	 * 
	 * @param serverParts 服务器上文件分片列表(主要包含partNum,partKey,partMD5)
	 * @param localParts 本地待上传文件的分片列表（主要包含partNum,partMD5，partFile)
	 * @return
	 */
	private MultipartFileParts getMultipartFileParts(List<FilePart> serverParts, List<FilePart> localParts)
	{
		MultipartFileParts parts = new MultipartFileParts();

		List<FilePart> finishedParts = new LinkedList<>();
		List<FilePart> errorParts = new LinkedList<>();

		for (FilePart serverPart : serverParts)
		{
			int index = inLocalPatrs(serverPart, localParts);
			if (index == -1)
			{
				errorParts.add(serverPart);
			}
			else
			{
				finishedParts.add(serverPart);
				localParts.remove(index);
			}
		}
		parts.setErrorParts(errorParts);
		parts.setFinishedParts(finishedParts);
		parts.setUnfinishedParts(localParts);
		return parts;
	}

	/**
	 * 服务器上的某一个分片是否包含在待上传的本地分片列表中
	 * 
	 * @param serverPart 服务器上的某一个分片
	 * @param localParts 本地待上传的分片列表
	 * @return
	 */
	private int inLocalPatrs(FilePart serverPart, List<FilePart> localParts)
	{
		for (FilePart localPart : localParts)
		{
			if (localPart.getPartNum().compareTo(serverPart.getPartNum()) == 0
					&& localPart.getPartMD5().equals(serverPart.getPartMD5()))
			{
				return localParts.indexOf(localPart);
			}
		}
		return -1;
	}

	/**
	 * 列出服务器上已初始化过的文件信息
	 * 
	 * @param request
	 * @return list (包含uploadId,fileName,fileMD5)
	 * @throws PispowerAPIException
	 * @throws IOException
	 */
	public List<FileInfo> list(ServerFileListRequest request)
		throws PispowerAPIException, ClientException
	{
		final QueryString queryString = new QueryString();
		if (null != request.getFileMD5Equal() && !"".equals(request.getFileMD5Equal()))
		{
			queryString.addParameter("fileMD5Equal", request.getFileMD5Equal());
		}
		if (null != request.getFileNameLike() && !"".equals(request.getFileNameLike()))
		{
			queryString.addParameter("fileNameLike", request.getFileNameLike());
		}
		final JSONObject json = client.get("/video/multipartUpload/list.api", queryString);

		List<FileInfo> list = new LinkedList<>();
		if (json.getString("statusCode").equals("0"))
		{
			JSONArray multipartUploads = (JSONArray) json.get("multipartUploads");

			for (Object o : multipartUploads)
			{
				JSONObject jo = (JSONObject) o;
				FileInfo file = (FileInfo) JSONObject.toBean(jo, FileInfo.class);
				list.add(file);
			}
			return list;
		}
		else
		{
			throw new PispowerAPIException(json.getInt("statusCode"), json.getString("message"));
		}
	}

	/**
	 * 查询服务器上已有的分片
	 * 
	 * @param uploadId 当前上传事件的唯一标识
	 * @return
	 * @throws IOException
	 * @throws Exception
	 */
	public List<FilePart> getParts(String uploadId)
		throws PispowerAPIException, ClientException
	{
		final QueryString queryString = new QueryString();
		queryString.addParameter("uploadId", uploadId);

		final JSONObject json = client.get("/video/multipartUpload/getParts.api", queryString);

		List<FilePart> fileParts = new LinkedList<>();
		if (json.getString("statusCode").equals("0"))
		{
			JSONArray uploadParts = (JSONArray) json.get("uploadedParts");
			for (Object o : uploadParts)
			{
				FilePart part = new FilePart();
				JSONObject jo = (JSONObject) o;
				part.setPartKey(jo.getString("partKey"));
				part.setPartMD5(jo.getString("partMD5"));
				part.setPartNum(jo.getInt("partNumber"));
				fileParts.add(part);
			}
			return fileParts;
		}
		else
		{
			throw new PispowerAPIException(json.getInt("statusCode"), json.getString("message"));
		}
	}

	/**
	 * 初始化待上传文件
	 * 
	 * @param file 文件的名字
	 * @return uploadId 当前上传事件的唯一标识
	 * @throws PispowerAPIException
	 * @throws IOException
	 */
	private String initMultipartUpload(String fileName, String fileMD5)
		throws PispowerAPIException, ClientException
	{
		final QueryString queryString = new QueryString();
		queryString.addParameter("fileName", fileName);
		queryString.addParameter("fileMD5", fileMD5);

		final JSONObject json = client.post("/video/multipartUpload/init.api", queryString);

		if (json.getString("statusCode").equals("0"))
		{
			return json.getString("uploadId");
		}
		else
		{
			throw new PispowerAPIException(json.getInt("statusCode"), json.getString("message"));
		}
	}

	/**
	 * 上传分片
	 * 
	 * @param uploadId 当前上传事件的唯一标识
	 * @param unFinishedParts 未上传的分片列表（主要包含partNum,partMD5，partFile）
	 * @return
	 * @throws Exception
	 */
	private List<FilePart> uploadParts(final String uploadId, List<FilePart> unFinishedParts)
		throws Exception
	{
		final List<FilePart> finishedParts = new LinkedList<>();

		// 对没有上传成功的分块进行上传
		for (FilePart filePart : unFinishedParts)
		{
			Integer partNum = filePart.getPartNum();
			FilePart part = uploadPart(uploadId, partNum, filePart.getPartFile());
			String partMD5 = part.getPartMD5();
			String partKey = part.getPartKey();

			// 返回的分块MD5是否与本地对应的相等
			if (filePart.getPartMD5().equals(partMD5))
			{
				// 上传成功一片，将该片加入finishedMap
				finishedParts.add(new FilePart(partNum, partKey));
			}
			else
			{
				throw new PispowerAPIException("partMD5 error");
			}
		}
		return finishedParts;
	}

	/**
	 * 上传一个分片
	 * 
	 * @param uploadId 当前上传事件的唯一标识
	 * @param partNum 分片的num编号
	 * @param part 分片文件
	 * @return
	 * @throws PispowerAPIException
	 * @throws IOException
	 */
	private FilePart uploadPart(final String uploadId, final Integer partNum, final File part)
		throws PispowerAPIException, ClientException
	{
		final QueryString queryString = new QueryString();
		queryString.addParameter("uploadId", uploadId);
		queryString.addParameter("partNumber", partNum.toString());

		final JSONObject json = client.post("/video/multipartUpload/uploadPart.api", queryString, part);

		if (json.getString("statusCode").equals("0"))
		{
			FilePart filePart = new FilePart();
			String partMD5 = json.getString("partMD5");
			String partKey = json.getString("partKey");
			filePart.setPartKey(partKey);
			filePart.setPartMD5(partMD5);
			return filePart;
		}
		else
		{
			throw new PispowerAPIException(json.getInt("statusCode"), json.getString("message"));
		}
	}

	/**
	 * 合并分片
	 * 
	 * @param uploadId 当前上传事件的唯一标识
	 * @param parts 上传完成的分片列表（主要包含partNum,partKey的值）
	 * @param catalogId 上传到该catalogId对应的Catalog中
	 * @return video对象
	 *         包含id,name,size,type,status,catalogId,catalogName,List<VideoEmbedCode
	 *         >。
	 * @throws PispowerAPIException
	 * @throws IOException
	 */
	private Video completeUpload(final String uploadId, final List<FilePart> parts, String catalogId)
		throws PispowerAPIException, ClientException
	{
		final QueryString queryString = new QueryString();
		queryString.addParameter("uploadId", uploadId);
		if (null != catalogId && !"".equals(catalogId))
		{
			queryString.addParameter("catalogId", catalogId);
		}
		for (FilePart part : parts)
		{
			final Integer key = part.getPartNum();
			final String partKey = part.getPartKey();
			queryString.addParameter("part" + key, partKey);
		}

		final JSONObject json = client.post("/video/multipartUpload/complete.api", queryString);

		if (json.getString("statusCode").equals("0"))
		{
			Video video = new Video();
			video.setId(json.getInt("videoId"));
			video.setName(json.getString("name"));
			video.setSize(json.getLong("size"));
			video.setType(json.getString("type"));
			video.setDuration(json.getLong("duration"));
			video.setStatus(json.getString("status"));
			JSONArray embedCodes = (JSONArray) json.get("embedCodes");
			List<VideoEmbedCode> embedCodeList = new LinkedList<>();

			for (Object o : embedCodes)
			{
				JSONObject jo = (JSONObject) o;
				VideoEmbedCode embedCode = (VideoEmbedCode) JSONObject.toBean(jo, VideoEmbedCode.class);
				embedCodeList.add(embedCode);
			}
			video.setEmbedCodes(embedCodeList);
			video.setCatalogId(json.getInt("catalogId"));
			video.setCatalogName(json.getString("catalogName"));
			return video;
		}
		else
		{
			throw new PispowerAPIException(json.getInt("statusCode"), json.getString("message"));
		}

	}

	/**
	 * 删除服务器上错误的分片
	 * 
	 * @param errorParts 错误的分片列表
	 * @throws IOException
	 * @throws PispowerAPIException
	 */
	private void deleteParts(List<FilePart> errorParts)
		throws ClientException, PispowerAPIException
	{
		if (errorParts.isEmpty())
		{
			return;
		}

		final QueryString queryString = new QueryString();
		for (FilePart part : errorParts)
		{
			queryString.addParameter("partKeys", part.getPartKey());
		}

		final JSONObject json = client.post("/video/multipartUpload/deleteParts.api", queryString);

		if (!json.getString("statusCode").equals("0"))
		{
			throw new PispowerAPIException(json.getInt("statusCode"), json.getString("message"));
		}
	}

	/**
	 * 对文件进行分片
	 * 
	 * @param file 待上传文件
	 * @param partSize 分片的大小
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private File[] splitFile(File file, int partSize)
		throws FileNotFoundException, IOException
	{
		String tmpDir = System.getProperty("java.io.tmpdir");
		File filePartDir =
				new File(tmpDir + "/onecloud-multipart-upload/" + file.getName() + System.currentTimeMillis());

		if (!filePartDir.exists() || !filePartDir.isDirectory())
		{
			filePartDir.mkdirs();
		}

		final FileInputStream in = new FileInputStream(file);
		long partCount = file.length() / partSize;
		long beginIndex = 0, endIndex = 0;

		if (file.length() % partSize > 0)
		{
			partCount++;
		}

		final byte[] buffer = new byte[BUFFER_SIZE];

		for (int i = 0; i < partCount; i++)
		{
			final int partNum = i + 1;
			final FileOutputStream out = new FileOutputStream(new File(filePartDir, "video." + partNum + ".part"));
			endIndex = endIndex + partSize;
			if (endIndex > file.length())
			{
				endIndex = file.length();
			}
			while (beginIndex < endIndex)
			{
				byte[] bff = buffer;

				if (endIndex - beginIndex < BUFFER_SIZE)
				{
					bff = new byte[(int) (endIndex - beginIndex)];
				}
				final int readCount = in.read(bff);
				beginIndex += readCount;
				out.write(bff);
			}
			out.close();
		}
		in.close();

		return filePartDir.listFiles();
	}

	/**
	 * 删除分片所在目录
	 * 
	 * @param fileDir
	 */
	private void deleteFileParts(File fileDir)
	{
		if (fileDir.exists() && fileDir.isDirectory())
		{
			File[] files = fileDir.listFiles();
			for (File f : files)
			{
				f.delete();
			}
			fileDir.delete();
		}
	}
}
