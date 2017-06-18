package com.cqupt.kindergarten.util;

import android.accounts.NetworkErrorException;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jakewharton.disklrucache.DiskLruCache;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

import static java.lang.String.valueOf;

/**
 * Created by SoulMateXD on 2016/10/30.
 */

    //模板代码。要熟练写出来
    //虽然很蠢，改进很多。。
public class HttpUtil {
    private static OkHttpClient mOkHttpClient = new OkHttpClient();

    public static String post(String url, HashMap<String, String> params){
        HttpURLConnection httpURLConnection = null;
        byte[] data = getRequestData(params, "UTF-8").toString().getBytes();
        try {
            URL mUrl = new URL(url);
            httpURLConnection = (HttpURLConnection) mUrl.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setConnectTimeout(4000);
            httpURLConnection.setReadTimeout(4000);
            httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
            httpURLConnection.setRequestProperty("Content-Length", valueOf(data.length));
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);

            OutputStream outputStream = httpURLConnection.getOutputStream();
            outputStream.write(data);
            outputStream.flush();
            outputStream.close();

            int responseCode = httpURLConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK){
                InputStream inputStream = httpURLConnection.getInputStream();
                return getResponseString(inputStream);
            }else {
                throw new NetworkErrorException("response code is" + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /*
         * Function  :   封装请求体信息
         * Param     :   params请求体内容，encode编码格式
         */
    public static StringBuffer getRequestData(Map<String, String> params, String encode) {
        StringBuffer stringBuffer = new StringBuffer();        //存储封装好的请求体信息
        try {
            for(Map.Entry<String, String> entry : params.entrySet()) {
                stringBuffer.append(entry.getKey())
                        .append("=")
                        .append(URLEncoder.encode(entry.getValue(), encode))
                        .append("&");
            }
            stringBuffer.deleteCharAt(stringBuffer.length() - 1);    //删除最后的一个"&"
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stringBuffer;
    }

    public static String get(String url){
        HttpURLConnection httpURLConnection = null;
        try {
            URL mUrl = new URL(url);
            httpURLConnection = (HttpURLConnection) mUrl.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setReadTimeout(5000);
            httpURLConnection.setConnectTimeout(5000);
            int responseCode = httpURLConnection.getResponseCode();
            if (responseCode == 200){  //请求成功
                InputStream inputStream = httpURLConnection.getInputStream();
                String response = getResponseString(inputStream);
                return response;
            }else {
                Log.d("Myresponse", "response status is" + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (httpURLConnection != null)
            httpURLConnection.disconnect();
        }
        return null;
    }

    public static Bitmap getBitMap(String url){
        Bitmap bitmap = null;
        HttpURLConnection httpURLConnection = null;
        try {
            URL mUrl = new URL(url);
            httpURLConnection = (HttpURLConnection) mUrl.openConnection();
            httpURLConnection.setRequestMethod("GET");
            int responseCode = httpURLConnection.getResponseCode();
            Log.e("HttpUtil", responseCode + "111");
            if (responseCode == 200){  //请求成功
                httpURLConnection = (HttpURLConnection) mUrl.openConnection();
                bitmap = BitmapFactory.decodeStream(httpURLConnection.getInputStream());
                Log.e("HttpUtil", responseCode + "");
            }else {
                throw new NetworkErrorException("response status is" + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (httpURLConnection != null)
                httpURLConnection.disconnect();
        }
        return bitmap;
    }

    public static Bitmap getBitMapDecoded(final String url, int reqWidth, int reqHeight){
        Bitmap bitmap = null;
        HttpURLConnection httpURLConnection = null;
        try {
            URL mUrl = new URL(url);
            httpURLConnection = (HttpURLConnection) mUrl.openConnection();
            httpURLConnection.setRequestMethod("GET");
            int responseCode = httpURLConnection.getResponseCode();
            Log.e("HttpUtil", responseCode + "111");
            if (responseCode == 200){  //请求成功
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeStream(httpURLConnection.getInputStream(), null, options);
                int inSampleSize = MyBitmapFactory.caculateInSampleSize(options, reqWidth, reqHeight);
                //decodeStream无法对同一inputstream解析两次，必须重新开一个
                httpURLConnection.disconnect();
                httpURLConnection = (HttpURLConnection) mUrl.openConnection();
                options.inJustDecodeBounds = false;
                options.inSampleSize = inSampleSize;
                bitmap = BitmapFactory.decodeStream(httpURLConnection.getInputStream(), null, options);
                Log.e("HttpUtil", responseCode + "");
            }else {
                throw new NetworkErrorException("response status is" + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (httpURLConnection != null)
                httpURLConnection.disconnect();
        }
        return bitmap;
    }
    private static String getResponseString(InputStream inputStream){
        //ByteArrayOutputStream实现了一个输出流，其中的数据被写入一个 byte 数组。
        // 缓冲区会随着数据的不断写入而自动增长。可使用 toByteArray()和 toString()获取数据。
        //可以用来缓存数据，多次写入一次获取
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] bytes = new byte[1024];
        int len = -1;

        try {
            //SoulMateXD啊，你当初写的时候为什么会把这里写成if呢？你TM算算你找了多久
            while ((len = inputStream.read(bytes)) != -1){
                byteArrayOutputStream.write(bytes, 0, len);
                Log.d("RESPONSE", new String(bytes, 0, len));
            }
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String state = byteArrayOutputStream.toString();
        try {
            byteArrayOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return state;
    }

    public static void mOkHttpPost(String url, ArrayList<String> keys, ArrayList<String> values, Callback callback){
        FormBody.Builder formBody = new FormBody.Builder();
        int i = 0;
        for (; i<keys.size(); i++){
            formBody.add(keys.get(i), values.get(i));
        }
        Request request = new Request.Builder()
                .url(url)
                .post(formBody.build())
                .build();
        mOkHttpClient.newCall(request).enqueue(callback);
    }

    public static void mOkHttpPost(final String url, final Map<String, Object> map, Callback callback) {
        FormBody.Builder formBody = new FormBody.Builder();
        if (map != null) {
            // map 里面是请求中所需要的 key 和 value
            for (Map.Entry entry : map.entrySet()) {
                formBody.add(valueOf(entry.getKey()), valueOf(entry.getValue()));
            }
        }
        Request request = new Request.Builder()
                .url(url)
                .post(formBody.build())
                .build();
        // readTimeout("请求超时时间" , 时间单位);
        mOkHttpClient.newCall(request)
                .enqueue(callback);

    }
    public static void mOkHttpGet(String getUrl, Callback callback){
        Request request = new Request.Builder()
                .url(getUrl)
                .build();
        okhttp3.Call call = mOkHttpClient.newCall(request);
        call.enqueue(callback);
    }


}






