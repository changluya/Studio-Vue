/**
 * @description TODO
 * @author changlu
 * @date 2024/07/14 18:37
 * @version 1.0
 */
package com.changlu.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.changlu.common.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Description: JSON工具库
 * @Author: changlu
 * @Date: 2024.7.28 18:37
 */
public class JsonObjectUtil {

    private static final Logger log = LoggerFactory.getLogger(JsonObjectUtil.class);

    /**
     * 传入jsonObject获取到指定key的对象
     * @param jsonObject JSONObject
     * @param key String
     * @param clazz Class<T>
     * @return T
     */
    public static  <T> T getJSONObjectByKey(JSONObject jsonObject, String key, Class<T> clazz) {
        T obj = null;
        try {
            obj = JSONObject
                    .parseObject(
                            jsonObject.getString(key),
                            clazz
                    );
        }catch (Exception ex) {
            log.error(String.format("反序列化key：%s失败！", key), ex);
        }
        return obj;
    }

    /**
     * 将json字符串转为jsonObject对象
     * @param jsonStr String
     * @return JSONObject
     * @author changlu
     */
    public static JSONObject parseStringToJsonObject(String jsonStr) {
        JSONObject extraJSONObject = null;
        try {
            extraJSONObject = JSONObject.parseObject(jsonStr);
            if (extraJSONObject == null)
                extraJSONObject = new JSONObject();
        }catch (Exception ex) {
            log.error(String.format("jsonStr：%s转JSONObject失败！", jsonStr), ex);
        }
        return extraJSONObject;
    }

    /**
     * 将jsonStr转换为指定class对象（先将json进行解码）
     * @param jsonStr String
     * @param clazz Class
     * @return Object
     */
    public static Object transferJsonToObjectByBase64(String jsonStr, Class clazz) {
        String decodedToStr = Base64Util.decodeToStr(jsonStr);
        return transferJsonToObject(decodedToStr, clazz);
    }

    /**
     * 将jsonStr转换为指定class对象
     * @param jsonStr String
     * @param clazz Class
     * @return Object
     */
    public static Object transferJsonToObject(String jsonStr, Class clazz) {
        Object obj = null;
        try {
            // 使用Json库解析该json串的形式
            Object jsonObject = JSON.parse(jsonStr);
            // 若是JSONArray格式，一般为[] => List，则使用JSONObject.parseArray来进行解析
            if (jsonObject instanceof JSONArray) {
                obj = JSONObject.parseArray(jsonObject.toString(), clazz);
            }else if (jsonObject instanceof String) {
                obj = jsonObject;
            }else { //JSONObject格式
                obj = JSONObject.parseObject(jsonObject.toString(), clazz);
            }
        }catch (Exception ex) {
            log.error(String.format("解析json串：%s => %s 失败！", jsonStr, clazz), ex);
        }
        return obj;
    }

    /**
     * 将对象转为json字符串
     */
    public static String transferObjectToJson(Object obj) {
        return JSON.toJSONString(obj);
    }



}
