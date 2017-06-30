package com.ehome.core.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 默认覆盖java.lang.Object的几个方法
 * @author hsu
 *
 */
public class TopObject {

	@Override
	public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

	@Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this,
            ToStringStyle.MULTI_LINE_STYLE);
    }

	@Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }
    
}
