package com.ehome.cloud.guangchang.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Table;

import com.ehome.core.model.BaseEntity;

/**
 * 广场匿名聊举报实体类
 * 
 * @Title:CommentModel
 * @author:张宗奎
 * @date:2017年6月8日
 * @version:
 */
@Table(name = "t_gc_report")
public class ReportModel extends BaseEntity implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 7162063056736239128L;
    
    private Integer type;

    private Integer discussionId;

    private Date reportTime;
    
    public Integer getType() {
        return type;
    }
    
    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getDiscussionId() {
        return discussionId;
    }

    public void setDiscussionId(Integer discussionId) {
        this.discussionId = discussionId;
    }

    public Date getReportTime() {
        return reportTime;
    }

    public void setReportTime(Date reportTime) {
        this.reportTime = reportTime;
    }
}