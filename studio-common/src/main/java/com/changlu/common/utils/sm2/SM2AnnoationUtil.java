package com.changlu.common.utils.sm2;

import com.changlu.common.annoation.SM2;
import java.lang.reflect.Field;

public class SM2AnnoationUtil {

    // 加密验证处理
    public static void serializeSM2(Object o, String privateKey, String publicKey) {
        if (o == null) return;
        Field[] fields = o.getClass().getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            field.setAccessible(true);
            SM2 annotation = field.getAnnotation(SM2.class);
            if (annotation != null) {
                // 注解中的值
                String sm2Action = annotation.action();
                if ("encrypt,decrypt".equals(sm2Action) || "encrypt".equals(sm2Action)) {
                    // 指定属性的值
                    String value = "";
                    try {
                        value = (String)field.get(o);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException("标注SM2注解的属性应当为字符串类型, exception：%s", e);
                    }
                    // 默认为字符串类型
                    try {
                        field.set(o, SM2Util.encrypt(value, privateKey, publicKey));
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException("标注SM2注解的属性加密异常， exception：%s", e);
                    }
                    //System.out.println(String.format("filedName:%s, sm2值为：%s", filedName, sm2Action));
                }
            }
        }
    }

    public static void deserializerSM2(Object o, String privateKey, String publicKey) {
        if (o == null) return;
        Field[] fields = o.getClass().getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            field.setAccessible(true);
            SM2 annotation = field.getAnnotation(SM2.class);
            if (annotation != null) {
                // 注解中的值
                String sm2Action = annotation.action();
                if ("encrypt,decrypt".equals(sm2Action) || "decrypt".equals(sm2Action)) {
                    // 指定属性的值
                    String value = "";
                    try {
                        value = (String)field.get(o);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException("标注SM2注解的属性应当为字符串类型, exception：%s", e);
                    }
                    // 默认为字符串类型
                    try {
                        field.set(o, SM2Util.decrypt(value, privateKey, publicKey));
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException("标注SM2注解的属性解密异常， exception：%s", e);
                    }
                    //System.out.println(String.format("filedName:%s, sm2值为：%s", filedName, sm2Action));
                }
            }
        }
    }

}
