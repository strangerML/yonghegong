package com.xcjy.infra.utils.json;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.NameFilter;
import com.alibaba.fastjson.serializer.PropertyFilter;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.ValueFilter;

public class JsonUtils {

    /**
     * 将java对象转换成json字符串（如果对象包含日期属性，可以指定日期格式，不包含传null, 默认日期格式为：yyyy-MM-dd HH:mm:ss）
     * @param object 任意类型，包括集合
     * @param dateFormat
     * @param features
     * @return
     */
    public static String toJSONString(Object object,String dateFormat, SerializerFeature... features) {
    	return toJSONStringWithFilter(object, dateFormat, null, null, null, features);
    }
    
    /**
     * 将java对象转换成json字符串（过滤掉不想生成的属性）（如果对象包含日期属性，可以指定日期格式，不包含传null，默认日期格式为：yyyy-MM-dd HH:mm:ss）
     * @param object 任意类型，包括集合
     * @param excludeProperties 需要过滤掉的属性名
     * @param dateFormat
     * @param features
     * @return
     * @deprecated replaced by <b>toJSONStringFilterProperties</b> method
     */
    @Deprecated
    public static String toJSONStringExcludeProperties(Object object, final String[] excludeProperties,String dateFormat, SerializerFeature... features) {
    	PropertyFilter propertyFilter = new PropertyFilter() {
			@Override
			public boolean apply(Object source, String name, Object value) {
				if (Arrays.asList(excludeProperties).contains(name)) {
					return false;
				} else {
					return true;
				}
			}
        	
        };
    	return toJSONStringWithFilter(object, dateFormat, propertyFilter, null, null, features);
    }
    
    /**
     * 将java对象转换成json字符串（只将指定的属性转换成json字符串）（如果指定的日期属性包含日期，可以指定日期格式，不包含传null，默认日期格式为：yyyy-MM-dd HH:mm:ss）
     * @param object
     * @param includeProperties 生成json字符串后所包含的属性
     * @param dateFormat
     * @param features
     * @return
     * @deprecated replaced by <b>toJSONStringFilterProperties</b> method
     */
    @Deprecated
    public static String toJSONStringIncludeProperties(Object object, final String[] includeProperties,String dateFormat, SerializerFeature... features) {
    	PropertyFilter propertyFilter = new PropertyFilter() {
			@Override
			public boolean apply(Object source, String name, Object value) {
				if (Arrays.asList(includeProperties).contains(name)) {
					return true;
				} else {
					return false;
				}
			}
        	
        };
    	return toJSONStringWithFilter(object, dateFormat, propertyFilter, null, null, features);
    }
    
    /**
     * 将java对象转换成json字符串（过滤指定属性）（如果指定的日期属性包含日期，可以指定日期格式，不包含传null，默认日期格式为：yyyy-MM-dd HH:mm:ss）
     * @param object　待转换的java对象
     * @param filterProperties　需要过滤的属性
     * @param isInclude 是否含指定属性
     * @param dateFormat
     * @param features
     * @return
     */
    public static String toJSONStringFilterProperties(Object object, final String[] filterProperties, final boolean isInclude, String dateFormat, SerializerFeature... features) {
    	PropertyFilter propertyFilter = filterProperties(filterProperties, isInclude);
    	return toJSONStringWithFilter(object, dateFormat, propertyFilter, null, null, features);
    }
    
    /**
     * 生成json字符串，使用指定过滤器
     * @param object
     * @param dateFormat
     * @param propertyFilter 属性过滤器
     * @param valueFilter 值过滤器
     * @param nameFilter 属性名称过滤器
     * @param features 特性设置
     * @return
     */
    public static String toJSONStringWithFilter(Object object, String dateFormat, PropertyFilter propertyFilter, ValueFilter valueFilter, NameFilter nameFilter, SerializerFeature... features) {
    	SerializeWriter out = new SerializeWriter();
        try {
            JSONSerializer serializer = new JSONSerializer(out);
            for (com.alibaba.fastjson.serializer.SerializerFeature feature : features) {
                serializer.config(feature, true);
            }
            if (propertyFilter != null) {
            	serializer.getPropertyFilters().add(propertyFilter);
            }
            
            if (valueFilter != null) {
            	serializer.getValueFilters().add(valueFilter);
            }
            
            if (nameFilter != null) {
            	serializer.getNameFilters().add(nameFilter);
            }
            
            if (StringUtils.isNotEmpty(dateFormat)) {
            	serializer.config(SerializerFeature.WriteDateUseDateFormat, true);
                serializer.setDateFormat(dateFormat);
            }

            serializer.write(object);

            return out.toString();
        } finally {
            out.close();
        }
    }
    
    /**
     * 将json字符串转换成指定javaBean，若json字符串中包含日期，则日期格式必须为yyyy-MM-dd HH:mm:ss或yyyy-MM-dd
     * @param <T>
     * @param json
     * @param clazz
     * @return
     */
    public static <T> T parseObject(String json, Class<T> clazz, Feature... features) {
    	T value = JSON.parseObject(json, clazz, features);
    	return value;
    }
    
    /**
     * 将json字符串转换成指定list，若json字符串中包含日期，则日期格式必须为yyyy-MM-dd HH:mm:ss或yyyy-MM-dd
     * @param <T>
     * @param json
     * @param clazz
     * @return
     */
    public static <T> List<T> parseArray(String json, Class<T> clazz) {
    	return JSON.parseArray(json, clazz);
    }
    
    /**
     * 用于重命名属性
     * @param renameMap key : 原始属性名　　value : 重命名后的属性名
     * @return
     */
    public static NameFilter renameProperty(final Map<String, String> renameMap) {
    	NameFilter nameFilter = new NameFilter() {
		    public String process(Object source, String name, Object value) {
		    	if (renameMap.containsKey(name)) {
		    		return renameMap.get(name);
		    	}
		        return name;
		    }
		};
    	return nameFilter;
    }
    
    /**
     * 用于过滤属性，只包含指定属性
     * @param properties 属性数组
     * @param isInclude 是否包含这些属性
     * @return
     */
    public static PropertyFilter filterProperties(final String[] properties, final boolean isInclude) {
    	PropertyFilter propertyFilter = new PropertyFilter() {
			@Override
			public boolean apply(Object source, String name, Object value) {
				if (isInclude) {
					return Arrays.asList(properties).contains(name);
				} else {
					return !Arrays.asList(properties).contains(name);
				}
			}
        };
    	return propertyFilter;
    }
    
    
}