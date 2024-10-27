package com.changlu.common.annoation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface SM2{

    /**
     * 可实现行为：
     * 1、encrypt 仅支持加密
     * 2、decrypt 仅支持解密
     * 3、encrypt,decrypt 支持加解密
     */
    public String action() default "encrypt,decrypt";


}
