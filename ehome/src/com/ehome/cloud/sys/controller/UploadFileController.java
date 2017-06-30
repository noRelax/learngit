package com.ehome.cloud.sys.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ehome.cloud.sys.model.UploadFile;
import com.ehome.cloud.sys.service.IUploadFileService;
import com.ehome.core.frame.BaseController;

@RequestMapping(value = "upload")
@Controller
public class UploadFileController extends BaseController {

	private static final String TITLE = "title";

	private static final String CONTENT = "content";

	@Autowired
	private IUploadFileService uploadFileService;

	@RequestMapping(value = "op/single")
	public String toUploadSingle() {
		return "/upload/single.html";
	}

	@RequestMapping(value = "op/multi")
	public String toUploadMuiti() {
		return "/upload/multi.html";
	}

	private String getUploadFilePath(HttpServletRequest request) {
		String path = request.getSession().getServletContext().getRealPath("/");
		if (request.getRequestURI().endsWith("/single")
				|| request.getRequestURI().endsWith("multi")) {
			path = path + "/upload/";
		} else if (request.getRequestURI().endsWith("one")
				|| request.getRequestURI().endsWith("more")) {
			path = path + "/files/";
		} else {

		}
		return path;
	}

	/**
	 * 上传单个文件, 客户端用/single，后台用/one
	 * @param request
	 * @param file  form表单type=file的name 必须为file
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "single", "one" })
	public Map<String, Object> uploadSingle(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(required = false, defaultValue = "file") MultipartFile file) {
		//        logger.info("" + request.getParameterMap());
		//TODO: only4 test
		//        response.setContentType("text/html; charset=utf-8");
		//        response.addHeader("Access-Control-Allow-Origin", "*");
		//        response.addHeader("Access-Control-Allow-Methods", "POST");
		//        response.addHeader("Access-Control-Allow-Headers", "Content-Type");
		//        response.addHeader("Access-Control-Max-Age", "1800");// 30 min
		return saveFile(request, getUploadFilePath(request), file);
	}

	private String getRandom() {
		return Long.toHexString(System.nanoTime());
	}

	private String getNowDate() {
		return DateFormatUtils.format(Calendar.getInstance(), "yyyyMMdd");
		//require java8
		//        return LocalDate.now().toString().replaceAll("-", "");
	}

	/**
	 * 上传多个文件, 客户端用/mulit，后台用/more
	 * @param request
	 * @param file  form表单type=file的name 必须为files
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "multi", "more" })
	public List<Map<String, Object>> uploadMulti(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(required = false, defaultValue = "files") MultipartFile[] files) {
		//TODO: only4 test
		//        response.setContentType("text/html; charset=utf-8");
		//        response.addHeader("Access-Control-Allow-Origin", "*");
		//        response.addHeader("Access-Control-Allow-Methods", "POST");
		//        response.addHeader("Access-Control-Allow-Headers", "Content-Type");
		//        response.addHeader("Access-Control-Max-Age", "1800");// 30 min
		List<Map<String, Object>> infos = new ArrayList<Map<String, Object>>();
		if (files != null && files.length > 0) {
			for (MultipartFile file : files) {
				infos.add(saveFile(request, getUploadFilePath(request), file));
			}
		}
		return infos;
	}

	private Map<String, Object> saveFile(HttpServletRequest request,
			String filePath, MultipartFile file) {
		String title = request.getParameter(TITLE) == null ? "" : request
				.getParameter(TITLE).toString();
		String content = request.getParameter(CONTENT) == null ? "" : request
				.getParameter(CONTENT).toString();
		Map<String, Object> info = new HashMap<>();
		File f = new File(filePath + getNowDate() + "/");
		if (!f.exists()) {
			f.mkdirs();
		}
		if (!file.isEmpty()) {
			try {
				String originalFilename = file.getOriginalFilename();
				String targetPath = getNowDate()
						+ "/"
						+ getRandom()
						+ originalFilename.substring(originalFilename
								.lastIndexOf("."));
				File diskPath = new File(filePath + targetPath);
				file.transferTo(diskPath);
				int original = -1;
				if (filePath.endsWith("/upload/")) {
					original = 0;
					info.put("path", "/upload/" + targetPath);
				} else if (filePath.endsWith("/files/")) {
					original = 1;
					info.put("path", "/files/" + targetPath);
				} else {
					original = -1;
					info.put("path", "");
				}
				UploadFile uploadFile = new UploadFile(
						file.getOriginalFilename(), diskPath.getName(), info
								.get("path").toString(), original, title,
						content);
				uploadFileService.save(uploadFile);
				info.put("id", uploadFile.getId());
				info.put("originalName", file.getOriginalFilename());
				info.put("currentName", diskPath.getName());
				//                info.put("filePath", diskPath.getAbsolutePath());
			} catch (IllegalStateException e) {
				logger.info(e.getMessage());
				e.printStackTrace();
			} catch (IOException e) {
				logger.info(e.getMessage());
				e.printStackTrace();
			}
		}
		return info;
	}
}
