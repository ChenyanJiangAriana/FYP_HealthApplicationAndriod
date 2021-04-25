package com.jk.dayu.jkapp.service;

import com.alibaba.fastjson.JSONObject;
import com.jk.dayu.jkapp.untils.InputStr2StrUntil;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Map;
import java.util.Set;


public class HttpUntil {

    public static String postUntil(String url,String json_body) {
        String mesg = null;
        //构建HttpClient实例
        HttpClient httpClient = new HttpClient();
        //设置请求超时时间
        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(90000);
        //设置响应超时时间
        httpClient.getHttpConnectionManager().getParams().setSoTimeout(90000);

        //构造PostMethod的实例
        PostMethod postMethod = new PostMethod(url);
        postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
        Map map = JSONObject.parseObject(json_body, Map.class);
        Set<Object> set = map.keySet();
        for (Object s : set) {
            System.out.println(map.get(s).toString());
            postMethod.addParameter((String) s, map.get(s).toString());
        }
        try {
            httpClient.executeMethod(postMethod);
            mesg = postMethod.getResponseBodyAsString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //关闭连接释放资源的方法
            postMethod.releaseConnection();
            //((SimpleHttpConnectionManager)httpClient.getHttpConnectionManager()).shutdown();
            httpClient.getHttpConnectionManager().closeIdleConnections(0);
        }
        return mesg;
    }
    public static String getUntil(String path){
        try {
            URL url = new URL(path);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(5000);
            connection.setRequestMethod("GET");
            //获得结果码
            int responseCode = connection.getResponseCode();
            if(responseCode ==200){
                //请求成功 获得返回的流
                InputStream is = connection.getInputStream();
                return InputStr2StrUntil.InputStr2Str_byteArr(is,null);
                // IOSUtil.inputStream2String(is);
            }else {
                //请求失败
                return null;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
