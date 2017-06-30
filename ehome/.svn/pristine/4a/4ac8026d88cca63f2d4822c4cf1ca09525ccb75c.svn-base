package com.baidu.ueditor.upload;

import com.baidu.ueditor.PathFormat;
import com.baidu.ueditor.define.BaseState;
import com.baidu.ueditor.define.State;
import com.ehome.cloud.sys.model.UploadFile;
import com.ehome.cloud.sys.service.IUploadFileService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;


public class BinaryUploader {
	
	public static final State save(HttpServletRequest request, Map<String, Object> conf, IUploadFileService uploadFileService) {
		boolean isAjaxUpload = request.getHeader("X_Requested_With") != null;
		if (!ServletFileUpload.isMultipartContent(request)) {
			return new BaseState(false, 5);
		}
		ServletFileUpload upload = new ServletFileUpload(
				new DiskFileItemFactory());
		if (isAjaxUpload) {
			upload.setHeaderEncoding("UTF-8");
		}
		try {
			SimpleDateFormat datepath = new SimpleDateFormat("yyyyMMdd");
			String DatePathStr = datepath.format(new Date());
			String savePath = request.getServletContext().getRealPath("/")+"/files/ueditorupload/"+DatePathStr+"/";
			String saveUrl  = request.getContextPath() + "/files/ueditorupload/"+DatePathStr+"/";
			
			//上传附件目录
			Path path = Paths.get(savePath);
			if(!Files.isDirectory(path)){
				try {
					Files.createDirectories(path);
				} catch (IOException e) {
					return new BaseState(false, e.getMessage());
				}
			}
	        //重新命名文件名字
			String newFileName = "", fileExt = "", fileName = "",currentFileName="";
			CommonsMultipartResolver multipartResolver=new CommonsMultipartResolver(request.getSession().getServletContext());
			//检查form中是否有enctype="multipart/form-data"
			if(multipartResolver.isMultipart(request)) {
				//将request变成多部分request
	            MultipartHttpServletRequest multiRequest=(MultipartHttpServletRequest)request;
	            //获取multiRequest 中所有的文件名
	            Iterator<String>it=multiRequest.getFileNames();
	            //遍历文件
	            while(it.hasNext()) {
	            	MultipartFile file=multiRequest.getFile(it.next().toString());
	            	if(file != null){
	            		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
	            		fileName = file.getOriginalFilename();
	            		fileExt = fileName.substring(fileName.lastIndexOf(".")).toLowerCase();
	            		currentFileName = df.format(new Date()) + "_" + new Random().nextInt(1000) + fileExt;
	            		//新文件名称
						newFileName = savePath + currentFileName;
						saveUrl = saveUrl + currentFileName;
						if (!validType(fileExt, (String[])conf.get("allowFiles"))) {
					        return new BaseState(false, 8);
					      }
						try {
							//上传文件
							file.transferTo(new File(newFileName));
							UploadFile uploadFile = new UploadFile(fileName, currentFileName, saveUrl,0);
			                uploadFileService.save(uploadFile);
						} catch (IllegalStateException | IOException e) {
							return new BaseState(false, e.getMessage());
						}
	            	}
	            }
			}
			
	        State storageState = new BaseState(Boolean.TRUE);
			if (storageState.isSuccess()) {
				storageState.putInfo("url", PathFormat.format(saveUrl));
				storageState.putInfo("type", fileExt);
				storageState.putInfo("original", fileName);
			}
			return storageState;
		} catch (Exception e) {
			return new BaseState(false, e.getMessage());
		}
	}

	private static boolean validType(String type, String[] allowTypes) {
		List<String> list = Arrays.asList(allowTypes);
		return list.contains(type);
	}
}