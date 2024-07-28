package com.changlu.common.utils;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Base64Util {
    public Base64Util() {
    }

    public static String baseEncode(String text) {
        return Base64.getEncoder().encodeToString(text.getBytes(StandardCharsets.UTF_8));
    }

    public static String baseDecode(String encode) {
        return new String(Base64.getDecoder().decode(encode), StandardCharsets.UTF_8);
    }

    public static byte[] baseEncode(byte[] encode) {
        return Base64.getEncoder().encode(encode);
    }

    public static byte[] baseDecode(byte[] encode) {
        return Base64.getDecoder().decode(encode);
    }

    public static String encode(byte[] data) {
        return (new BASE64Encoder()).encode(data).replaceAll("\r|\n", "");
    }

    public static String encode(String str) {
        return Base64.getEncoder().encodeToString(str.getBytes(StandardCharsets.UTF_8));
    }

    public static String decodeToStr(String str) {
        return new String(Base64.getDecoder().decode(str), StandardCharsets.UTF_8);
    }

    public static byte[] decode(String base64) {
        try {
            return (new BASE64Decoder()).decodeBuffer(base64);
        } catch (IOException var2) {
            IOException e = var2;
            e.printStackTrace();
            return new byte[0];
        }
    }
}
