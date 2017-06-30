package com.ehome.activiti.utils;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"com.ehome.core.onlinepay","com.ehome.core.ueditor.Controller"})
public class ModulesInit {
	
	public ModulesInit() {
		
	}

}
