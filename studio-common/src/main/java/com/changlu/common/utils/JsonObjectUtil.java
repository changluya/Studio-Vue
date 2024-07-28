/**
 * @description TODO
 * @author changlu
 * @date 2024/07/14 18:37
 * @version 1.0
 */
package com.changlu.common.utils;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Description:
 * @Author: changlu
 * @Date: 18:37
 */
public class JsonObjectUtil {

    private static final Logger log = LoggerFactory.getLogger(JsonObjectUtil.class);

    //获取到指定key的对象
    public static  <T> T getUserExtraObjectByKey(JSONObject extraJSONObject, String key, Class<T> clazz) {
        T obj = null;
        try {
            obj = JSONObject
                    .parseObject(
                            extraJSONObject.getString(key),
                            clazz
                    );
        }catch (Exception ex) {
            log.error("反序列化key：%s失败！", key);
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
        JSONObject extraJSONObject = JSONObject.parseObject(jsonStr);
        if (extraJSONObject == null) extraJSONObject = new JSONObject();
        return extraJSONObject;
    }

}
