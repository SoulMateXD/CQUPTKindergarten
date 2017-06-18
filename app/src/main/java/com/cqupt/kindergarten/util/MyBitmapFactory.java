package com.cqupt.kindergarten.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import com.jakewharton.disklrucache.DiskLruCache;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static java.lang.String.valueOf;

/**
 * Created by SoulMateXD on 2017/6/12.
 */

public class MyBitmapFactory {

    public static int getMaxMemoryKB(){
        return (int) (Runtime.getRuntime().maxMemory()/1024);
    }

    public static BitmapFactory.Options getOptions(Context context, int id){
        BitmapFactory.Options options = new BitmapFactory.Options();
        BitmapFactory.decodeResource(context.getResources(), id, options);
        return options;
    }

    //如果没想好要多宽多长，就输入0吧;
    public static int caculateInSampleSize(BitmapFactory.Options options,
                                           int reqWidth, int reqHeight){
        int width = options.outWidth;
        int height = options.outHeight;
        int inSampleSize = 1;
        if ( reqHeight == 0 || reqWidth == 0){
            return inSampleSize;
        }
        if (height > reqHeight || width > reqWidth){
            int halfHeight = height / 2;
            int halfWidth = width / 2;
            while ((halfHeight / inSampleSize >= reqHeight) && (halfWidth/inSampleSize >= reqWidth)){
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    public static Bitmap decodeSampleBitmapFromResource(Resources res, int resId,
                                                        int width, int height){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);
        int inSampleSize = caculateInSampleSize(options, width, height);
        options.inSampleSize = inSampleSize;
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    public static Bitmap decodeSampleBitmapFromSnapshot(DiskLruCache.Snapshot snapshot,
                                                        int reqWidth, int reqHeight){
        Bitmap bitmap = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        FileInputStream inputStream = (FileInputStream) snapshot.getInputStream(0);
        try {
            FileDescriptor fileDescriptor = inputStream.getFD();
            BitmapFactory.decodeFileDescriptor(fileDescriptor, null, options);
            options.inSampleSize = caculateInSampleSize(options, reqWidth, reqHeight);
            options.inJustDecodeBounds = false;
            bitmap = BitmapFactory.decodeFileDescriptor(fileDescriptor, null, options);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    public static Bitmap decodeSampleBitmapFromSnapshot(DiskLruCache.Snapshot snapshot){
        Bitmap bitmap = null;
        FileInputStream inputStream = (FileInputStream) snapshot.getInputStream(0);
        try {
            FileDescriptor fileDescriptor = inputStream.getFD();
            bitmap = BitmapFactory.decodeFileDescriptor(fileDescriptor);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    public static File getCacheDiskDir(Context context, String uniqueName){
        String cachePath;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()){
            cachePath = context.getExternalCacheDir().getPath();
        }else {
            cachePath = context.getCacheDir().getPath();
        }
        return new File(cachePath + File.separator + uniqueName);
    }

    //这个是抄的， 等有时间了再研究
    //将String 转换为MD5码的形式，便于做key
    public static String hashKeyFromString(String string){
        String cacheKey;
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(string.getBytes());
            cacheKey = bytesToHexString(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            cacheKey = valueOf(string.hashCode());
        }
        return cacheKey;
    }

    private static String bytesToHexString(byte[] bytes){
        StringBuilder stringBuilder = new StringBuilder();
        for (int i=0; i<bytes.length; i++){
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1){
                stringBuilder.append('0');
            }
            stringBuilder.append(hex);
        }
        return stringBuilder.toString();
    }

    //将请求到的response 写入所给的outputStream中
    public static boolean downloadUrlToStream(String urlString, OutputStream outputStream){
        HttpURLConnection urlConnection = null;
        BufferedInputStream in = null;
        BufferedOutputStream out = null;

        try {
            URL url = new URL(urlString);
            urlConnection = (HttpURLConnection) url.openConnection();
            in = new BufferedInputStream(urlConnection.getInputStream());
            out = new BufferedOutputStream(outputStream);
            int b;   //为什么是int?
            while ((b = in.read()) != -1){
                out.write(b);
            }
            return
                    true;
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (urlConnection != null)
                urlConnection.disconnect();
            try {
                if (in != null)
                    in.close();
                if (out != null)
                    out.flush();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static boolean putBitmapToOutputStream(Bitmap bitmap, OutputStream outputStream){
        BufferedInputStream in;
        BufferedOutputStream out;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        in = new BufferedInputStream(new ByteArrayInputStream(byteArrayOutputStream.toByteArray()));
        out = new BufferedOutputStream(outputStream);
        int b;
        try {
            while ((b = in.read()) != -1){
                out.write(b);
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
