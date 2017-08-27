package com.obito.recitewords.tools;

import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.util.Log;

import com.obito.recitewords.bmobobject.Words;
import com.obito.recitewords.callbackinterface.TranlateCallBack;

import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by obito on 17-8-27.
 */

public class TranslateTool{
    public static final String id = "58324b291e729cdf";//appKey!!!!
    public static final String key = "JpC0A1Vrcop6o3mh9r8j2pq23edpvxpe";

    //翻译的方法
    public static void translate(final String word, final TranlateCallBack callBack){
        new Thread(){
            @Override
            public void run() {
                try {
                    //创建url
                    String url = createUrl(word);
                    //发送http请求 并获取json数据
                    String json = HttpTool.sendGet(url);
                    //解析json数据
                    Words words=ParseDataTool.parseJsonData(json);
                    callBack.onFinish(words);
                } catch (UnsupportedEncodingException e) {
                    callBack.onError(e);
                } catch (JSONException e) {
                    callBack.onError(e);
                }
            }
        }.start();

    }


    private static String createUrl(String word) throws UnsupportedEncodingException {
        //将要翻译的文本转换为utf-8编码
        word = new String(word.getBytes(), "UTF-8");
        //生成随机数
        long random = SystemClock.uptimeMillis();
        //生成签名
        String md5 = md5(id + word + random + key);
        //将要发送的所有数据转换成utf-8编码
        String id = URLEncoder.encode(TranslateTool.id, "utf-8");
        String key = URLEncoder.encode(TranslateTool.key, "utf-8");
        word = URLEncoder.encode(word, "utf-8");
        String ran = URLEncoder.encode(random + "", "utf-8");
        md5 = URLEncoder.encode(md5, "utf-8");
        //拼接出URL
        String url= "http://openapi.youdao.com/api?q=" +
                word +
                "&from=auto&to=zh_CHS" +
                "&appKey=" + id +
                "&salt=" + random +
                "&sign=" + md5;
        return url;
    }


    /**
     * 生成32位MD5摘要
     *
     * @param string
     * @return
     */
    private static String md5(String string) {
        if (string == null) {
            return null;
        }
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            byte[] btInput = string.getBytes("utf-8");
            /** 获得MD5摘要算法的 MessageDigest 对象 */
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            /** 使用指定的字节更新摘要 */
            mdInst.update(btInput);
            /** 获得密文 */
            byte[] md = mdInst.digest();
            /** 把密文转换成十六进制的字符串形式 */
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (byte byte0 : md) {
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
                /**
                 * byte0: xxxx xxxx>>>0000 xxxx
                 *                                               &
                 * oxf:    0000 1111       0000 1111
                 *
                 */
            }
            return new String(str);
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            return null;
        }
    }

}
