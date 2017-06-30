package com.ehome.core.util;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtilsBean;


/*
 * @Title:MapUtils
 * @Description:TODO
 * @author:zsh
 * @date:2017年2月22日
 * @version 1.0,2017年2月22日
 * @{tags}
  */
public class MapUtils extends org.apache.commons.collections.MapUtils {
	/**
	 * 将Map转换为Object
	 * 
	 * @param clazz
	 *            目标对象的类
	 * @param map
	 *            待转换Map
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public static <T, V> T toObject(Class<T> clazz, Map<String, V> map) throws InstantiationException, IllegalAccessException, InvocationTargetException {
		T object = clazz.newInstance();
		return toObject(object, map);
	}

	/**
	 * 将Map转换为Object
	 * 
	 * @param clazz
	 *            目标对象的类
	 * @param map
	 *            待转换Map
	 * @param toCamelCase
	 *            是否去掉下划线
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public static <T, V> T toObject(Class<T> clazz, Map<String, V> map, boolean toCamelCase) throws InstantiationException, IllegalAccessException, InvocationTargetException {
		T object = clazz.newInstance();
		return toObject(object, map, toCamelCase);
	}

	/**
	 * 将Map转换为Object
	 * 
	 * @param object
	 *            目标对象
	 * @param map
	 *            待转换Map
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public static <T, V> T toObject(T object, Map<String, V> map) throws InstantiationException, IllegalAccessException, InvocationTargetException {
		return toObject(object, map, false);
	}

	public static <T, V> T toObject(T object, Map<String, V> map, boolean toCamelCase) throws InstantiationException, IllegalAccessException, InvocationTargetException {
		if (toCamelCase)
			map = toCamelCaseMap(map);
		BeanUtils.populate(object, map);
		return object;
	}

	/**
	 * 对象转Map
	 * 
	 * @param object
	 *            目标对象
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	public static Map<String, String> toMap(Object object) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		return BeanUtils.describe(object);
	}

	/**
	 * 转换为Collection<Map<K, V>>
	 * 
	 * @param collection
	 *            待转换对象集合
	 * @return 转换后的Collection<Map<K, V>>
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	public static <T> Collection<Map<String, String>> toMapList(Collection<T> collection) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		List<Map<String, String>> retList = new ArrayList<Map<String, String>>();
		if (collection != null && !collection.isEmpty()) {
			for (Object object : collection) {
				Map<String, String> map = toMap(object);
				retList.add(map);
			}
		}
		return retList;
	}

	/**
	 * 转换为Collection,同时为字段做驼峰转换<Map<K, V>>
	 * 
	 * @param collection
	 *            待转换对象集合
	 * @return 转换后的Collection<Map<K, V>>
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	public static <T> Collection<Map<String, String>> toMapListForFlat(Collection<T> collection) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		List<Map<String, String>> retList = new ArrayList<Map<String, String>>();
		if (collection != null && !collection.isEmpty()) {
			for (Object object : collection) {
				Map<String, String> map = toMapForFlat(object);
				retList.add(map);
			}
		}
		return retList;
	}

	/**
	 * 转换成Map并提供字段命名驼峰转平行
	 * 
	 * @param clazz
	 *            目标对象所在类
	 * @param object
	 *            目标对象
	 * @param map
	 *            待转换Map
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	public static Map<String, String> toMapForFlat(Object object) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		Map<String, String> map = toMap(object);
		return toUnderlineStringMap(map);
	}

	/**
	 * 将Map的Keys去下划线<br>
	 * (例:branch_no -> branchNo )<br>
	 * 
	 * @param map
	 *            待转换Map
	 * @return
	 */
	public static <V> Map<String, V> toCamelCaseMap(Map<String, V> map) {
		Map<String, V> newMap = new HashMap<String, V>();
		for (String key : map.keySet()) {
			if(key.contains("_")){
				newMap.put(key, map.get(key));
				safeAddToMap(newMap, JavaBeanUtil.toCamelCaseString(key), map.get(key));
			}else{
				newMap.put(key, map.get(key));
			}
		}
		return newMap;
	}

	/**
	 * 将Map的Keys去下划线<br>
	 * (例:branch_no -> branchNo )<br>
	 * 
	 * @param map
	 *            待转换Map
	 * @return
	 */
	public static <V> Map<String, V> toCamelCaseMapNoRepeat(Map<String, V> map) {
		Map<String, V> newMap = new HashMap<String, V>();
		for (String key : map.keySet()) {
			if(key.contains("_")){
				safeAddToMap(newMap, JavaBeanUtil.toCamelCaseString(key), map.get(key));
			}else{
				newMap.put(key, map.get(key));
			}
		}
		return newMap;
	}
	
	/**
	 * 将Map的Keys转译成下划线格式的<br>
	 * (例:branchNo -> branch_no)<br>
	 * 
	 * @param map
	 *            待转换Map
	 * @return
	 */
	public static <V> Map<String, V> toUnderlineStringMap(Map<String, V> map) {
		Map<String, V> newMap = new HashMap<String, V>();
		for (String key : map.keySet()) {
			newMap.put(JavaBeanUtil.toUnderlineString(key), map.get(key));
		}
		return newMap;
	}
	
	
//	 public static String getString(Object...args){
//		 if(args!=null && args.length==3){
//			 for(Object obj:args){
//				 if(obj instanceof Map){  
//					 System.out.println("传入了一个map："+obj);
//					 
//				 }else if(obj instanceof String){
//					 System.out.println("传入了一个字符串："+obj);
//				 }  
//			 }
//		 }else if(args!=null && args.length==1){
//			 if(args[0] instanceof Map){
//				return ((Map)args[0]).get("")
//			 }
//		 }
//	    } 
	
	public static String getString(Map<String, Object> map,String key,String defaultValue){
		return map.get(key)==null?defaultValue:map.get(key).toString();
	}
	
	public static Integer getInteger(Map<String, Object> map,String key,Integer defaultValue){
		if(null==map.get(key) || "".equals(map.get(key).toString())){
			return defaultValue;
		}else{
			return Integer.parseInt(map.get(key).toString());
		}
	}
	
	/**
     * 将一个 Map 对象转化为一个 JavaBean
     * @param type 要转化的类型
     * @param map 包含属性值的 map
     * @return 转化出来的 JavaBean 对象
     * @throws IntrospectionException 如果分析类属性失败
     * @throws IllegalAccessException 如果实例化 JavaBean 失败
     * @throws InstantiationException 如果实例化 JavaBean 失败
     * @throws InvocationTargetException 如果调用属性的 setter 方法失败
     */ 
    @SuppressWarnings("rawtypes") 
    public static Object convertMap(Class type, Map map) 
            throws IntrospectionException, IllegalAccessException, 
            InstantiationException, InvocationTargetException { 
        BeanInfo beanInfo = Introspector.getBeanInfo(type); // 获取类属性 
        Object obj = type.newInstance(); // 创建 JavaBean 对象 
 
        // 给 JavaBean 对象的属性赋值 
        PropertyDescriptor[] propertyDescriptors =  beanInfo.getPropertyDescriptors(); 
        for (int i = 0; i< propertyDescriptors.length; i++) { 
            PropertyDescriptor descriptor = propertyDescriptors[i]; 
            String propertyName = descriptor.getName(); 
 
            if (map.containsKey(propertyName)) { 
                // 下面一句可以 try 起来，这样当一个属性赋值失败的时候就不会影响其他属性赋值。
            	try {
            		Object value = map.get(propertyName); 
            		
            		Object[] args = new Object[1]; 
            		args[0] = value; 
            		// XXX 目前只支持String Interger 类型转化 set 参数只有一个
            		Class []  paramType = descriptor.getWriteMethod().getParameterTypes();//判断方法的参数类型
            		if("class java.lang.Integer".equals(paramType[0].toString())){//注 前端Integer的参数要么传 要么不传 不能传"" 此处做了处理 ""默认为空
            			if("".equals(args[0].toString())){
            				args[0] = null;
            			}else{
            				args[0]=Integer.parseInt(args[0].toString());
            			}
            			descriptor.getWriteMethod().invoke(obj, args);
            		}else if("class java.util.Date".equals(paramType[0].toString())){
            			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            			Date date=sdf.parse(args[0].toString());
            			descriptor.getWriteMethod().invoke(obj, date);
            		}else{
            			descriptor.getWriteMethod().invoke(obj, args);//String类型
            		}
				} catch (Exception e) {
					e.printStackTrace();				}
            } 
        } 
        return obj; 
    }
    
    /**
	 * bean转为map
	 * @param obj
	 * @return
	 */
	public static Map<String, Object> beanToMap(Object obj) { 
        Map<String, Object> params = new HashMap<String, Object>(0); 
        try { 
            PropertyUtilsBean propertyUtilsBean = new PropertyUtilsBean(); 
            PropertyDescriptor[] descriptors = propertyUtilsBean.getPropertyDescriptors(obj); 
            for (int i = 0; i < descriptors.length; i++) { 
                String name = descriptors[i].getName(); 
                if (!"class".equals(name)) { 
                    params.put(name, propertyUtilsBean.getNestedProperty(obj, name)); 
                } 
            } 
        } catch (Exception e) { 
            e.printStackTrace(); 
        } 
        return params; 
}
	
	


}
