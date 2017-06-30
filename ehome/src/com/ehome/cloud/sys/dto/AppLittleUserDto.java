package com.ehome.cloud.sys.dto;

import java.io.Serializable;

import com.ehome.core.model.BaseEntity;
public class AppLittleUserDto extends BaseEntity implements Serializable {
	
    private static final long serialVersionUID = 3031976528505138530L;
    private String portrait;
    private String nickName;
    
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
}
