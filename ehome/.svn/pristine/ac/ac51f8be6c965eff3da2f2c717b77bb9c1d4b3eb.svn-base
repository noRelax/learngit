package com.ehome.cloud.marry.service;

import java.util.List;
import java.util.Map;

import com.ehome.cloud.app.marry.dto.AppMarryCommentDto;
import com.ehome.cloud.app.marry.dto.AppMarryPhotoDto;
import com.ehome.cloud.marry.model.MarryPhoto;
import com.ehome.core.frame.IService;

/**
 * @Title: IAppMarryPhotoService.java 
 * @Description: TODO 
 * @author hl@diandianwifi.com   
 * @date 2017年4月18日 下午3:55:08 
 * @version 
 */
public interface IAppMarryPhotoService extends IService<MarryPhoto> {

    public AppMarryPhotoDto getPhotoDetail(Integer appUserId, Integer photoId) throws Exception;

    public List<AppMarryCommentDto> getComment(Integer photoId, Integer page, Integer rows) throws Exception;

    public void publishPhoto(Integer appUserId, Integer pictureId, Integer sourceDevice, String pictureUrl, String idea, String location,
            String publicTime) throws Exception;

    public void deletePhoto(Integer photoId) throws Exception;

    public Integer addComment(Integer appUserId, Integer photoId, String comment, Integer sourceDevice) throws Exception;
    
    public void addReply(Integer commentId, Integer fromUserId, Integer toUserId, String replyMsg) throws Exception;
    
    public Map<String, Integer> thumpUp(Integer photoId, Integer appUserId, Integer sourceDevice) throws Exception;
    
}
