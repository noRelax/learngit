package com.ehome.core.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletResponse;

public class ZipUtil {
	public static void zipFiles(List<File> srcfile, File zipfile) {
		byte[] buf = new byte[1024];
		try {
			// Create the ZIP file
			ZipOutputStream out = new ZipOutputStream(new FileOutputStream(
					zipfile));
			// Compress the files
			for (int i = 0; i < srcfile.size(); i++) {
				File file = srcfile.get(i);
				FileInputStream in = new FileInputStream(file);
				// Add ZIP entry to output stream.
				out.putNextEntry(new ZipEntry(file.getName()));
				// Transfer bytes from the file to the ZIP file
				int len;
				while ((len = in.read(buf)) > 0) {
					out.write(buf, 0, len);
				}
				// Complete the entry
				out.closeEntry();
				in.close();
			}
			// Complete the ZIP file
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void downFile(HttpServletResponse response,
			String serverPath, String str) {
		try {
			String path = serverPath + "\\" + str;
			File file = new File(path);
			if (file.exists()) {
				InputStream ins = new FileInputStream(path);
				BufferedInputStream bins = new BufferedInputStream(ins);// 放到缓冲流里面
				OutputStream outs = response.getOutputStream();// 获取文件输出IO流
				BufferedOutputStream bouts = new BufferedOutputStream(outs);
				response.setContentType("application/x-download");// 设置response内容的类型
				response.setHeader(
						"Content-disposition",
						"attachment;filename="
								+ URLEncoder.encode(str, "UTF-8"));// 设置头部信息
				int bytesRead = 0;
				byte[] buffer = new byte[8192];
				// 开始向网络传输文件流
				while ((bytesRead = bins.read(buffer, 0, 8192)) != -1) {
					bouts.write(buffer, 0, bytesRead);
				}
				bouts.flush();// 这里一定要调用flush()方法
				ins.close();
				bins.close();
				outs.close();
				bouts.close();
				file.delete();
			} else {
				response.sendRedirect("../error.jsp");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
