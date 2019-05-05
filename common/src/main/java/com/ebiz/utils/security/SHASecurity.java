package com.ebiz.utils.security;

import java.security.MessageDigest;

/**
 * 通用加密与签名类
 */
public class SHASecurity {

    private static String sha(String decript, String type, String encoding) {
        try {
            MessageDigest digest = MessageDigest.getInstance(type);
            digest.update(decript.getBytes(encoding));
            byte[] messageDigest = digest.digest();

            StringBuffer hexString = new StringBuffer();

            for (int i = 0; i < messageDigest.length; i++) {
                String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
                if (shaHex.length() < 2) {
                    hexString.append(0);
                }
                hexString.append(shaHex);
            }
            return hexString.toString();
        } catch (Exception e) {
            return null;
        }
    }

    public static String toSHA1(String str) {
        return sha(str, "SHA-1", "utf-8");
    }

    public static String toSHA1(String str, String charset) {
        return sha(str, "SHA-1", charset);
    }

    public static String toSHA256(String str) {
        return sha(str, "SHA-256", "utf-8");
    }

    public static String toSHA256(String str, String charset) {
        return sha(str, "SHA-256", charset);
    }

    public static String toSHA512(String str) {
        return sha(str, "SHA-512", "utf-8");
    }

    public static String toSHA512(String str, String charset) {
        return sha(str, "SHA-512", charset);
    }
}