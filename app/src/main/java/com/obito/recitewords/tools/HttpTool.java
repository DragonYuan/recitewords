package com.obito.recitewords.tools;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpRetryException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by obito on 17-8-26.
 */

public class HttpTool {
    //发送get请求的方法
    public static String sendGet(String sUrl) {
        String re = null;
        URL url;
        HttpURLConnection httpURLConnection = null;
        try {
           // sUrl = URLEncoder.encode(sUrl, "UTF-8");
            url = new URL(sUrl);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            InputStream in = httpURLConnection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            re = sb.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
            Log.e("HttpTool",e.toString());
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("HttpToolIO",e.toString());
        } finally {
           // httpURLConnection.disconnect();
        }

        return re;
    }
}
