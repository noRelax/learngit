/*
 * 广州陨石互联网科技有限公司 项目名称 : Ehome 创建日期 : 2017年5月3日 修改历史 : 1. [2017年5月3日]创建文件 by helei
 */
package com.ehome.cloud.marry.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Table;

import com.ehome.core.model.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * //TODO 添加类/接口功能描述
 * @author helei
 */
@Table(name = "t_marry_look")
public class MarryLookModel extends BaseEntity implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -4893005319074180171L;

    @Column(name = "look_user_id")
    private Integer lookUserId;

    @Column(name = "be_look_user_id")
    private Integer beLookUserId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "update_date")
    private Date updateTime;

    public Integer getLookUserId() {
        return lookUserId;
    }

    public void setLookUserId(Integer lookUserId) {
        this.lookUserId = lookUserId;
    }

    public Integer getBeLookUserId() {
        return beLookUserId;
    }

    public void setBeLookUserId(Integer beLookUserId) {
        this.beLookUserId = beLookUserId;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

}
