package com.ehome.core.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

//import javafx.scene.control.TooltipBuilder;

/**
 * 实体类基类
 * 
 * @Title:BaseEntity
 * @Description:TODO
 * @author:张钟武
 * @date:2017年1月20日 上午10:54:05
 * @version:
 */
public class BaseEntity extends TopObject implements Serializable {

	private static final long serialVersionUID = -5010895152405229703L;

	@Id
	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Integer id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}
