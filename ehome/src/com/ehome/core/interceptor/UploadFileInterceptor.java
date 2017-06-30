package com.ehome.core.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.ehome.core.util.SpringPropertiesUtil;

public class UploadFileInterceptor extends HandlerInterceptorAdapter {

    private final String keyName = "upload_support_type";

    private boolean isSupport(String type) {

        String allowTypes = SpringPropertiesUtil.get(keyName);

        for (String allowType : allowTypes.split(",")) {
            if (type.equalsIgnoreCase(allowType.trim())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        HttpServletRequest req = (HttpServletRequest) request;
        MultipartResolver res = new org.springframework.web.multipart.commons.CommonsMultipartResolver();
        if (res.isMultipart(req)) {

            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) req;

            List<MultipartFile> multipartFiles = multipartRequest.getFiles("files");

            for (MultipartFile multipartFile : multipartFiles) {
                String fileName = multipartFile.getOriginalFilename();
                String fileType = fileName.substring(fileName.lastIndexOf(".") + 1);

                boolean isSupport = isSupport(fileType);
                if (!isSupport) {
                    response.getWriter().write("不支持上传的文件类型:" + fileName);
                }
                return isSupport;
            }
        }

        return super.preHandle(request, response, handler);
    }

}
