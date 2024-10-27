package com.changlu.common.utils.sm2;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.SM2;
import org.bouncycastle.crypto.engines.SM2Engine;
import org.springframework.util.StringUtils;

import java.security.KeyPair;


public class SM2Util {

    public static KeyPair generateKeyPair() {
        return SecureUtil.generateKeyPair("SM2");
    }

    /**
     * 加密
     * @param str
     * @return
     */
    public static String encrypt(String str, String privateKey, String publicKey) {
        try {
            if (checkKeyIsEmpty(privateKey, publicKey)) {
                SM2 sm2 = new SM2(privateKey, publicKey);
                sm2.setMode(SM2Engine.Mode.C1C3C2);
                return sm2.encryptHex(str, KeyType.PublicKey);
            }
            return str;
        } catch (Exception e) {
            throw new RuntimeException("sm2加密失败" + e);
        }
    }

    /**
     * 解密
     * @param str 加密之后的字符串
     * @param privateKey 私钥
     * @param publicKey 公钥
     * @return 原文密码
     */
    public static String decrypt(String str, String privateKey, String publicKey) {
        try {
            if (checkKeyIsEmpty(privateKey, publicKey)) {
                SM2 sm2 = new SM2(privateKey, publicKey);
                sm2.setMode(SM2Engine.Mode.C1C3C2);
                return sm2.decryptStr(str, KeyType.PrivateKey);
            }
            return str;
        } catch (Exception e) {
            throw new RuntimeException("sm2解密失败" + e);
        }
    }

    private static boolean checkKeyIsEmpty(String privateKey, String publicKey) {
        if (StringUtils.isEmpty(privateKey) || StringUtils.isEmpty(publicKey)) {
            return false;
        }
        return true;
    }

}
