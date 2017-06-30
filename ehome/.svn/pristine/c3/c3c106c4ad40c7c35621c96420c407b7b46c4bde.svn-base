package com.ehome.cloud.app.marry.dto;

import java.io.Serializable;

import com.ehome.core.annotation.CodeField;
import com.ehome.core.model.BaseEntity;

/**
 * @Title: AppMarryLoveDto
 * @Description: TODO
 * @author hl@diandianwifi.com
 * @date 2017年4月20日 下午2:34:37
 * @version
 */
public class AppMarryLoveDto extends BaseEntity implements Serializable {

    /**
     *      
     */
    private static final long serialVersionUID = 6786317335855616837L;

    private Integer appUserId; // t_app_use 表主键ID

    private Integer MemberId; // t_member表主键ID

    private String portrait;

    private String nickName;

    private String jobTitle;

    private String workPlace;

    @CodeField(code = "CODE_ANNUAL_INCOME", renderField = "annualIncomeName")
    private Integer annualIncome;
    private Integer isLoved; //是否已喜欢
    
    @CodeField(code = "CODE_MEMBER_STATUS", renderField = "memberStatuName")
    private Integer memberStatu; // 会员状态

    private String memberStatuName;
    private String annualIncomeName;

    public Integer getAppUserId() {
        return appUserId;
    }

    public void setAppUserId(Integer appUserId) {
        this.appUserId = appUserId;
    }

    public Integer getMemberId() {
        return MemberId;
    }

    public void setMemberId(Integer memberId) {
        MemberId = memberId;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getWorkPlace() {
        return workPlace;
    }

    public void setWorkPlace(String workPlace) {
        this.workPlace = workPlace;
    }

    public Integer getMemberStatu() {
        return memberStatu;
    }

    public void setMemberStatu(Integer memberStatu) {
        this.memberStatu = memberStatu;
    }

    public String getMemberStatuName() {
        return memberStatuName;
    }

    public void setMemberStatuName(String memberStatuName) {
        this.memberStatuName = memberStatuName;
    }

    public Integer getIsLoved() {
        return isLoved;
    }

    public void setIsLoved(Integer isLoved) {
        this.isLoved = isLoved;
    }

    public Integer getAnnualIncome() {
        return annualIncome;
    }

    public void setAnnualIncome(Integer annualIncome) {
        this.annualIncome = annualIncome;
    }

    public String getAnnualIncomeName() {
        return annualIncomeName;
    }

    public void setAnnualIncomeName(String annualIncomeName) {
        this.annualIncomeName = annualIncomeName;
    }

}
