package com.qiaosoftware.crm.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.util.WebUtils;

public class MyWebUtilForQueryCondition {
    
    /**
     * 关于带查询条件的思路:
     * 1. 在 Handler 中应该可以批量的获取查询条件对应的请求参数. WebUtils.getParametersStartingWith()
     * 2. 因为要编写较为通用的代码. 所以查询条件中应该包含除了 propertyName 和 propertVal 之外的更多的信息. 实际上需要包含四个信息
     * 属性名; 属性值; 属性的类型(从浏览器过来的总是字符串. 而后台的可能是其他数据类型, 例如 Date 类型.); 匹配的方式(在 mybatis 中若匹配方式为
     * LIKE 则需要把参数值的前后添加 %).
     * 于是创建了 PropertyFilter 
     * 
     * 3. 具体的步骤
     * 1). 先在 Handler 中获取请求参数的 Map. 
     * 2). 把需要在 WEB 环境下的其他查询条件也加入到查询条件的 Map 中
     * 3). 调用 Service 方法获取 Page 对象
     * 
     * 4). 先来添加非 WEB 环境下的其他的请求参数. 
     * 5). 把请求参数的 Map 转为 mybatis 可用的 Map. 实际上先把请求参数的 Map 转为了 PropertyFilter 的集合, 再转为 mybatis 使用的
     * 参数的 Map. 
     * 
     * 6). 关于携带查询条件的问题:
     * {"EQI_chanceId"=100, "LIKES_custName"="abc"}
     * ①. 把获取请求参数的 Map 转为一个查询字符串. 且把参数名前面添加 search_
     * search_EQI_chanceId=100&search_LIKES_custName=abc
     * ②. 把改字符串回传给页面
     * request.setAttribute()
     * ③. 下一次发请求时, 再把该查询字符串传回来. 
     * <a href="...?search_EQI_chanceId=100&search_LIKES_custName=abc">下一页</a>
     */
    public static Map<String, Object> forQueryCondition(HttpServletRequest request) {
        //1. 获取查询条件的参数. 
        //1.1 为每个请求参数都使用 @RequestParam 注解. 若参数比较多, 则会麻烦
        //1.2 调用 WebUtils.getParametersStartingWith() 方法来获取指定前缀的请求参数的 Map.
        //返回值为去除了前缀的 name=value 对. 
        Map<String, Object> requestParams = WebUtils.getParametersStartingWith(request, "search_");
        Map<String, Object> params = parseRequestParams2MybatisParams(requestParams);
        System.out.println(params);
        return params;
    }
    
    /**
     * 转换为mybatis所用的map
     * @param requestParams
     * @return
     */
    private static Map<String, Object> parseRequestParams2MybatisParams(Map<String, Object> requestParams) {
        List<PropertyFilter> filters = PropertyFilter.parseRequestParamsToPropertyFilters(requestParams);
        Map<String, Object> params = new HashMap<>();
        for(PropertyFilter filter: filters){
            params.put(filter.getPropertyName(), filter.getPropertyVal());
        }
        return params;
    }
    
    /**
     * 解决点击下一页时携带查询条件的问题
     * @param params
     * @return
     */
    public static String parseRequestParams2QueryString(Map<String, Object> params, String prefix) {
        
        if(params == null || params.size() == 0){
            return "";
        }
        
        if (prefix == null) {
            prefix = "";
        }
        
        StringBuilder queryString = new StringBuilder();
        
        for(Map.Entry<String, Object> entry: params.entrySet()){
            if(entry.getValue() == null || entry.getValue().toString().trim().equals("")){
                continue;
            }
            queryString.append(prefix)
                       .append(entry.getKey())
                       .append("=")
                       .append(entry.getValue())
                       .append("&");
        }
        
        if(queryString.length() > 0){
            queryString.replace(queryString.length() - 1, queryString.length(), "");
        }
        return queryString.toString();
    }

}
