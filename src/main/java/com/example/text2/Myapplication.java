package com.example.text2;

import android.app.Application;
import android.os.Environment;
import android.util.Log;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

import java.io.File;

/**
 * Created by 罗许忠 on 2017/5/2.
 */

public class Myapplication extends Application {
    {
//此处红色标注的是你在QQ平台上请求的ID与KEY


        PlatformConfig.setQQZone("1106085961", "Oxljxh9s4xI4QKvj");

          }

    @Override
    public void onCreate() {
        super.onCreate();

    //在application中初始化sdk，这个初始化最好放在application的程序入口中，防止意外发生：

        UMShareAPI.get(this);
        File cacheDir = StorageUtils.getOwnCacheDirectory(this,
                Environment.getExternalStorageDirectory().getPath());
        Log.e(" 22222222222", "onCreate: 缓存路径-------------------》"+Environment.getExternalStorageDirectory().getPath());
        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(this)
                .threadPoolSize(3)//配置线程数量
                .memoryCache(new LruMemoryCache(2 * 1024 * 1024))//内存缓存图片 2M
                .diskCache(new UnlimitedDiskCache(cacheDir))//配饰sdcard缓存路径
                .diskCacheSize(50 * 1024 * 1024)//sdcard上缓存50M的图片
                .diskCacheFileCount(100)//缓存文件的数量   100个
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .build();


        //配置缓存选项
        ImageLoader.getInstance().init(configuration);
    }

}
