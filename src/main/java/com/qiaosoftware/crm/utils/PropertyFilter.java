package com.qiaosoftware.crm.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.lang3.StringUtils;

@SuppressWarnings("rawtypes")
public class PropertyFilter {
	
	private String propertyName;   //属性名
	private Object propertyVal;    //属性值
    private Class propertyType;    //属性的类型
	private MatchType matchType;   //匹配的方式
	
	public String getPropertyName() {
		return propertyName;
	}

	public Object getPropertyVal() {
		return propertyVal;
	}

	public Class getPropertyType() {
		return propertyType;
	}

	public MatchType getMatchType() {
		return matchType;
	}
	
	static{
		DateConverter dateConverter = new DateConverter();
		dateConverter.setPatterns(new String[]{"yyyy-MM-dd", "yyyy-MM-dd hh:mm:ss"});
		//使 ConvertUtils 的 convert 还可以转换字符串到 Date 类型. 
		ConvertUtils.register(dateConverter, Date.class);
	}
	
	//把请求参数的 Map 转为 PropertyFilter 的集合. 
	public static List<PropertyFilter> parseRequestParamsToPropertyFilters(Map<String, Object> requestParams){
		List<PropertyFilter> filters = new ArrayList<>();
		
		if(requestParams == null || requestParams.size() == 0){
			return filters;
		}
		
		for(Map.Entry<String, Object> entry: requestParams.entrySet()){
			String propertyName = entry.getKey();
			Object propertyVal = entry.getValue();
			
			if(propertyVal == null || propertyVal.toString().trim().equals("")){
				continue;
			}
			
			filters.add(new PropertyFilter(propertyName, propertyVal));
		}
		
		return filters;
	}

	/** 构造器
	 *  传入的参数类似于: LIKES_custName=abc, GTD_birth=1990-12-12
	 */
	public PropertyFilter(String propertyName, Object propertyVal){
		String str1 = StringUtils.substringBefore(propertyName, "_"); //LIKES, GTD
		String MatchTypeCode = StringUtils.substring(str1, 0, str1.length() - 1); //LIKE, GT
		
		//Enum.valueOf 可以把一个字符串转为对应的枚举类的对象. 
		this.matchType = Enum.valueOf(MatchType.class, MatchTypeCode); //LIKE, GT
		
		String propertyTypeCode = StringUtils.substring(str1, str1.length() - 1);
		PropertyType propertyType = Enum.valueOf(PropertyType.class, propertyTypeCode);
		this.propertyType = propertyType.getPropertyType();
		
		this.propertyName = StringUtils.substringAfterLast(propertyName, "_");
		
		//把变量的类型直接转为需要的类型. 
		this.propertyVal = ConvertUtils.convert(propertyVal, this.getPropertyType());
		
		if(this.matchType == MatchType.LIKE){
			this.propertyVal = "%" + this.propertyVal + "%";
		}
	}
	
	//匹配方式的枚举类
	public enum MatchType{
		EQ, LT, LE, GT, GE, LIKE;
	}
	
	//属性类型的枚举类
	public enum PropertyType{
		
		I(Integer.class), D(Date.class), S(String.class), F(Float.class), L(Long.class);
		
		private Class propertyType;
		
		public Class getPropertyType() {
			return propertyType;
		}
		
		private PropertyType(Class propertyType){
			this.propertyType = propertyType;
		}
	}
	   
    public static void main(String[] args) {
//      String code = "LIKE";
//      MatchType matchType = Enum.valueOf(MatchType.class, code);
//      System.out.println(matchType.getClass());
        
        PropertyFilter filter = new PropertyFilter("GTD_birth", "1990-12-12");
        
        System.out.println(filter.getPropertyName());
        System.out.println(filter.getPropertyVal());
        System.out.println(filter.getMatchType());
        System.out.println(filter.getPropertyType());
    }
	
}
