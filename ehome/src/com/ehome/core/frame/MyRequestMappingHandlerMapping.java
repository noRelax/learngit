package com.ehome.core.frame;

import java.lang.reflect.Method;  
import java.util.ArrayList;  
import java.util.List;  
  
import org.springframework.core.annotation.AnnotationUtils;  
import org.springframework.web.accept.ContentNegotiationManager;  
import org.springframework.web.bind.annotation.RequestMapping;  
import org.springframework.web.servlet.mvc.condition.ConsumesRequestCondition;  
import org.springframework.web.servlet.mvc.condition.HeadersRequestCondition;  
import org.springframework.web.servlet.mvc.condition.ParamsRequestCondition;  
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;  
import org.springframework.web.servlet.mvc.condition.ProducesRequestCondition;  
import org.springframework.web.servlet.mvc.condition.RequestCondition;  
import org.springframework.web.servlet.mvc.condition.RequestMethodsRequestCondition;  
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;  
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;  

/**
 * 当 @RequestMapping 没有值的时候将路径默认映射为方法名
 * 仅支持 Method 
 * @author hsu
 *
 */
public class MyRequestMappingHandlerMapping extends RequestMappingHandlerMapping {

	private boolean useSuffixPatternMatch = true;

	private boolean useTrailingSlashMatch = true;

	private ContentNegotiationManager contentNegotiationManager = new ContentNegotiationManager();

	private final List<String> fileExtensions = new ArrayList<String>();

	@Override
	protected RequestMappingInfo getMappingForMethod(Method method, Class<?> handlerType) {
		RequestMappingInfo info = null;
		RequestMapping methodAnnotation = AnnotationUtils.findAnnotation(method, RequestMapping.class);
		if (methodAnnotation != null) {
			RequestCondition<?> methodCondition = getCustomMethodCondition(method);
			info = createRequestMappingInfo(methodAnnotation, methodCondition, method);
			RequestMapping typeAnnotation = AnnotationUtils.findAnnotation(handlerType, RequestMapping.class);
			if (typeAnnotation != null) {
				RequestCondition<?> typeCondition = getCustomTypeCondition(handlerType);
				info = createRequestMappingInfo(typeAnnotation, typeCondition, method).combine(info);
			}
		}
		return info;
	}

	protected RequestMappingInfo createRequestMappingInfo(RequestMapping annotation, RequestCondition<?> customCondition,Method method) {  
        String[] patterns = resolveEmbeddedValuesInPatterns(annotation.value());  
        if(patterns!=null && (patterns.length == 0)){  
//            patterns= new String[]{method.getName().toLowerCase()};  
            patterns= new String[]{method.getName()};  
        }  
        return new RequestMappingInfo(  
                new PatternsRequestCondition(patterns, getUrlPathHelper(), getPathMatcher(),  
                        this.useSuffixPatternMatch, this.useTrailingSlashMatch, this.fileExtensions),  
                new RequestMethodsRequestCondition(annotation.method()),  
                new ParamsRequestCondition(annotation.params()),  
                new HeadersRequestCondition(annotation.headers()),  
                new ConsumesRequestCondition(annotation.consumes(), annotation.headers()),  
                new ProducesRequestCondition(annotation.produces(), annotation.headers(), this.contentNegotiationManager),  
                customCondition);  
    }
}
