package com.ebiz.utils.http;


import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class HttpUtils {
    static {
        //proxy();
    }

    /**
     * 代理设置
     */
//    private static void proxy() {
//        System.setProperty("http.proxyHost", "");
//        System.setProperty("http.proxyPort", "");
//        System.setProperty("http.nonProxyHosts", "");
//    }

    /**
     * 信任所有SSL证书
     *
     * @throws HttpFailException
     */
    private static void initSSL() throws HttpFailException {
        try {
            SslUtils.ignoreSsl();
        } catch (Exception e) {
            throw new HttpFailException("信任所有SSL证书失败");
        }
    }

    public static String get(String url, String encode, int maxTimeout) throws HttpFailException {
        long start = System.currentTimeMillis();

        initSSL();
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) new URL(url).openConnection();
            if (maxTimeout > 0) {
                connection.setConnectTimeout(maxTimeout);
            }

            if (connection.getResponseCode() == 200) {
                BufferedReader br = null;
                if (encode == null) {
                    br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                } else {
                    br = new BufferedReader(new InputStreamReader(connection.getInputStream(), encode));
                }
                StringBuffer result = new StringBuffer();
                String tmp = null;
                while ((tmp = br.readLine()) != null) {
                    result.append(tmp);
                    result.append("\n");
                }

                return result.toString();
            } else {
                throw new HttpFailException(connection.getResponseCode(), System.currentTimeMillis() - start);
            }
        } catch (Exception err) {
            int stateCode = -100;
            if (connection != null) {
                try {
                    stateCode = connection.getResponseCode();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            throw new HttpFailException(stateCode, System.currentTimeMillis() - start, err);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    public static String get(String url) throws HttpFailException {
        return get(url, "utf-8", -1);
    }

    public static String get(String url, int maxTimeout) throws HttpFailException {
        return get(url, "utf-8", maxTimeout);
    }

    private static String post(String url, String data, String encode, int maxTimeout, boolean isJson) throws HttpFailException {
        long start = System.currentTimeMillis();
        initSSL();
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setUseCaches(false);
            if (isJson) {
                connection.setRequestProperty("Content-Type", "application/json");
            } else {
                connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            }
            connection.setRequestProperty("encoding", "UTF-8");
            if (maxTimeout > 0) {
                connection.setConnectTimeout(maxTimeout);
            }

            byte[] bt = data.getBytes("UTF-8");
            connection.setRequestProperty("Content-Length", String.valueOf(bt.length));
            connection.getOutputStream().write(bt);

            if (connection.getResponseCode() == 200) {
                BufferedReader br = null;
                if (encode == null) {
                    br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                } else {
                    br = new BufferedReader(new InputStreamReader(connection.getInputStream(), encode));
                }
                StringBuffer result = new StringBuffer();
                String tmp = null;
                while ((tmp = br.readLine()) != null) {
                    result.append(tmp);
                    result.append("\n");
                }

                return result.toString();
            } else {
                throw new HttpFailException(connection.getResponseCode(), System.currentTimeMillis() - start);
            }
        } catch (Exception err) {
            int stateCode = -100;
            if (connection != null) {
                try {
                    stateCode = connection.getResponseCode();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            throw new HttpFailException(stateCode, System.currentTimeMillis() - start, err);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    public static String post(String url, Map<String, Object> params) throws HttpFailException {
        String json = JSONObject.toJSONString(params);
        return post(url, json, "utf-8", -1, true);
    }

    public static String post(String url, Map<String, Object> params, int maxTimeout) throws HttpFailException {
        String json = JSONObject.toJSONString(params);
        return post(url, json, "utf-8", maxTimeout, true);
    }

    public static String post(String url, Map<String, Object> params, String encode, int maxTimeout) throws HttpFailException {
        String json = JSONObject.toJSONString(params);
        return post(url, json, encode, maxTimeout, true);

    }
}
