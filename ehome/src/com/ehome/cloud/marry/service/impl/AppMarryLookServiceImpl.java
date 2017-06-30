/*
 * 广州陨石互联网科技有限公司 项目名称 : Ehome 创建日期 : 2017年5月3日 修改历史 : 1. [2017年5月3日]创建文件 by helei
 */
package com.ehome.cloud.marry.service.impl;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.ehome.cloud.marry.model.MarryLookModel;
import com.ehome.cloud.marry.service.IAppMarryLookService;
import com.ehome.core.frame.BaseService;

/**
 * //TODO 看过我服务层
 * @author helei
 */
@Service("appMarryLookService")
public class AppMarryLookServiceImpl extends BaseService<MarryLookModel> implements IAppMarryLookService {

    /**
     * //TODO 添加override说明
     * @see com.ehome.cloud.marry.service.IAppMarryLookService#saveOrUpdateLookHistory(java.lang.Integer, java.lang.Integer)
     **/
    @Override
    public void saveOrUpdateLookHistory(Integer appUserId, Integer beLookUserId) throws Exception {
        // TODO Auto-generated method stub
        MarryLookModel entity = new MarryLookModel();
        entity.setLookUserId(appUserId);
        entity.setBeLookUserId(beLookUserId);
        MarryLookModel model = this.selectOne(entity);
        if (model == null) { //没有就插入
            MarryLookModel marryLookModel = new MarryLookModel();
            marryLookModel.setLookUserId(appUserId);
            marryLookModel.setBeLookUserId(beLookUserId);
            marryLookModel.setUpdateTime(new Date());
            this.saveNotNull(marryLookModel);
        } else { //有就更新
            model.setUpdateTime(new Date());
            this.updateNotNull(model);
        }
    }

}
