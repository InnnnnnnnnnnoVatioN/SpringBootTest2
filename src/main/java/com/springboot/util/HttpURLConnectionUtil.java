package com.springboot.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpURLConnectionUtil {
    public static String doGet(String urlStr) {

        //代表一次url的连接
        HttpURLConnection conn = null;
        //输入流
        InputStream is = null;
        //读取工具
        BufferedReader br = null;
        //拼接工具 StringBuilder
        StringBuilder result = new StringBuilder();
        try {
            URL url = new URL(urlStr);
            conn = (HttpURLConnection) url.openConnection();
            //设置请求的方法
            conn.setRequestMethod("GET");
            //设置连接时间和读取时间
            //连接时间：发送请求端 连接到 url目标地址端的时间 （受距离的长短和网络速度的影响）
            //读取时间：指连接成功后获取数据的时间 (受到数据量和服务器处理速度的影响）
            conn.setConnectTimeout(15000);
            conn.setReadTimeout(60000);
            //设置请求头参数的方式  如指定接收json数据 服务端的key值为 content - type
            conn.setRequestProperty("Accept", "application/json");
            //发送请求
            conn.connect();
            //判断返回的响应码
            //响应码不为200 不正常
            if (conn.getResponseCode() != 200) {
                //TODO 此处应该增加异常处理
                return "error code ";
            }
            is = conn.getInputStream();
            br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String line;
            //响应码为200 正常
            //逐行读取 不为空就继续
            while ((line = br.readLine()) != null) {
                result.append(line);
                System.out.println(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
                if (is != null) {
                    is.close();
                }


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result.toString();

    }

    public static void main(String[] args) {
        String str = "https://view.inews.qq.com/g2/getOnsInfo?name=disease_other";
        String result = doGet(str);
        System.out.println(result);
    }
}
