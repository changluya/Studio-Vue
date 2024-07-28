/**
 * @description TODO
 * @author changlu
 * @date 2024/07/14 18:37
 * @version 1.0
 */
package com.changlu.common.utils;

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
        //进行base64编码处理，同时转换为指定的对象
        return JSONObject.parseObject(Base64Util.decodeToStr(jsonStr), clazz);
    }

}
