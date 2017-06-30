package com.ehome.cloud.app.marry.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.ehome.core.annotation.CodeField;
import com.ehome.core.model.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * @Title: AppMarryPhotoDto.java
 * @Description: TODO
 * @author hl@diandianwifi.com
 * @date 2017年4月25日 下午2:42:20
 * @version
 */
public class AppMarryPhotoDto extends BaseEntity implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1311665266177696506L;

    private Integer pictureId;

    private String pictureUrl;

    private String idea;

    private String location;

    private Integer appUserId;

    private Integer thumbUpNum;

    private Integer commentNum;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date publicTime;

    @CodeField(code = "CODE_IS_RECOMMENDED", renderField = "isRecommendedName")
    private Integer isRecommended;

    @CodeField(code = "CODE_IS_SHIEIDING", renderField = "isShieldingName")
    private Integer isShielding;

    private Integer selectRequire;

    private Integer sourceDevice;

    private List<Map<String, Object>> thumpUpList = new ArrayList<Map<String, Object>>();

    private String portrait;

    private String nickName;

    private String workPlace;

    private String height;

    @CodeField(code = "CODE_ANNUAL_INCOME", renderField = "annualIncomeName")
    private Integer annualIncome;

    private String age;

    private Integer isThumpUp;

    private String job;

    @CodeField(code = "CODE_EDUCATION", renderField = "educationName")
    private Integer education;

    @CodeField(code = "CODE_MEMBER_STATUS", renderField = "memberStatusName")
    private Integer memberStatus;

    @CodeField(code = "CODE_STAR", renderField = "starName")
    private Integer star;

    private Integer isLoved; // 0        

    private List<AppMarryCommentDto> appMarryCommentDtoList = new ArrayList<AppMarryCommentDto>();

    private String educationName;

    private String memberStatusName;

    private String starName;

    private String isShieldingName;

    private String annualIncomeName;

    private String isRecommendedName;

    public String getIsRecommendedName() {
        return isRecommendedName;
    }

    public void setIsRecommendedName(String isRecommendedName) {
        this.isRecommendedName = isRecommendedName;
    }

    public String getAnnualIncomeName() {
        return annualIncomeName;
    }

    public void setAnnualIncomeName(String annualIncomeName) {
        this.annualIncomeName = annualIncomeName;
    }

    public Integer getPictureId() {
        return pictureId;
    }

    public void setPictureId(Integer pictureId) {
        this.pictureId = pictureId;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getIdea() {
        return idea;
    }

    public void setIdea(String idea) {
        this.idea = idea;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getAppUserId() {
        return appUserId;
    }

    public void setAppUserId(Integer appUserId) {
        this.appUserId = appUserId;
    }

    public Integer getThumbUpNum() {
        return thumbUpNum;
    }

    public void setThumbUpNum(Integer thumbUpNum) {
        this.thumbUpNum = thumbUpNum;
    }

    public Integer getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(Integer commentNum) {
        this.commentNum = commentNum;
    }

    public Date getPublicTime() {
        return publicTime;
    }

    public void setPublicTime(Date publicTime) {
        this.publicTime = publicTime;
    }

    public Integer getIsRecommended() {
        return isRecommended;
    }

    public void setIsRecommended(Integer isRecommended) {
        this.isRecommended = isRecommended;
    }

    public Integer getIsShielding() {
        return isShielding;
    }

    public void setIsShielding(Integer isShielding) {
        this.isShielding = isShielding;
    }

    public Integer getSelectRequire() {
        return selectRequire;
    }

    public void setSelectRequire(Integer selectRequire) {
        this.selectRequire = selectRequire;
    }

    public Integer getSourceDevice() {
        return sourceDevice;
    }

    public void setSourceDevice(Integer sourceDevice) {
        this.sourceDevice = sourceDevice;
    }

    public List<Map<String, Object>> getThumpUpList() {
        return thumpUpList;
    }

    public void setThumpUpList(List<Map<String, Object>> thumpUpList) {
        this.thumpUpList = thumpUpList;
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

    public String getWorkPlace() {
        return workPlace;
    }

    public void setWorkPlace(String workPlace) {
        this.workPlace = workPlace;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public Integer getAnnualIncome() {
        return annualIncome;
    }

    public void setAnnualIncome(Integer annualIncome) {
        this.annualIncome = annualIncome;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public Integer getIsThumpUp() {
        return isThumpUp;
    }

    public void setIsThumpUp(Integer isThumpUp) {
        this.isThumpUp = isThumpUp;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public Integer getEducation() {
        return education;
    }

    public void setEducation(Integer education) {
        this.education = education;
    }

    public Integer getMemberStatus() {
        return memberStatus;
    }

    public void setMemberStatus(Integer memberStatus) {
        this.memberStatus = memberStatus;
    }

    public List<AppMarryCommentDto> getAppMarryCommentDtoList() {
        return appMarryCommentDtoList;
    }

    public void setAppMarryCommentDtoList(List<AppMarryCommentDto> appMarryCommentDtoList) {
        this.appMarryCommentDtoList = appMarryCommentDtoList;
    }

    public String getEducationName() {
        return educationName;
    }

    public void setEducationName(String educationName) {
        this.educationName = educationName;
    }

    public String getMemberStatusName() {
        return memberStatusName;
    }

    public void setMemberStatusName(String memberStatusName) {
        this.memberStatusName = memberStatusName;
    }

    public Integer getStar() {
        return star;
    }

    public void setStar(Integer star) {
        this.star = star;
    }

    public String getStarName() {
        return starName;
    }

    public void setStarName(String starName) {
        this.starName = starName;
    }

    public String getIsShieldingName() {
        return isShieldingName;
    }

    public void setIsShieldingName(String isShieldingName) {
        this.isShieldingName = isShieldingName;
    }

    public Integer getIsLoved() {
        return isLoved;
    }

    public void setIsLoved(Integer isLoved) {
        this.isLoved = isLoved;
    }

}
