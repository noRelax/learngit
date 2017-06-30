package com.ehome.core.util;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.springframework.cglib.beans.BeanCopier.Generator;

/**
 * 代码生成辅助类，可以生成mapper.xml , entity, interface 
 * @author hsu
 *
 */
public class CodeHelper {

    
    public static void main(String[] args) throws Exception {
        
        List<String> warnings = new ArrayList<String>();
        boolean overwrite = true;
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = cp.parseConfiguration(
                Generator.class.getResourceAsStream("/mybatis/generatorConfig.xml"));
        DefaultShellCallback callback = new DefaultShellCallback(overwrite);
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
        myBatisGenerator.generate(null);
        
        System.out.println("Mission completed!!!");
        
    }
}
