package com.cqupt.kindergarten.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by SoulMateXD on 2017/7/13.
 */
public class GlideImageDownloadUtil {

    private Context context;
    private String dir;

    public GlideImageDownloadUtil() {
    }

    public GlideImageDownloadUtil(Context context, String dir) {
        super();
        this.context = context;
        this.dir = dir;
    }

    //Glide保存图片
    public void savePicture(final String fileName, String url){
        SimpleTarget<byte[]> target = new SimpleTarget<byte[]>() {
            @Override
            public void onResourceReady(byte[] bytes, GlideAnimation<? super byte[]> glideAnimation) {
                try {
                    savaFileToSD(fileName,bytes);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        Glide.with(context.getApplicationContext()) //支持后台运行
                .load(url).asBitmap().toBytes().into(target);
    }
    //往SD卡写入文件的方法
    public void savaFileToSD(String filename, byte[] bytes) throws Exception {
        //如果手机已插入sd卡,且app具有读写sd卡的权限
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            String filePath = Environment.getExternalStorageDirectory().getCanonicalPath()+"/" + dir;
            File dir1 = new File(filePath);
            if (!dir1.exists()){
                dir1.mkdirs();
            }
            filename = filePath+ "/" + filename;
            //这里就不要用openFileOutput了,那个是往手机内存中写数据的
            FileOutputStream output = new FileOutputStream(filename);
            output.write(bytes);
            //将bytes写入到输出流中
            output.close();
            //关闭输出流

            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            Uri uri = Uri.fromFile(new File(filename));
            intent.setData(uri);
            context.sendBroadcast(intent);
            Toast.makeText(context, "图片已成功保存到"+filePath, Toast.LENGTH_SHORT).show();
        } else Toast.makeText(context, "SD卡不存在或者不可读写", Toast.LENGTH_SHORT).show();
    }

}