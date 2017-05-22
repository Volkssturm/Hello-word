package com.example.text2.Fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * com.example.text2.Fragment
 * 徐世辉  1503A
 * <p>
 * 2017/5/3
 */

public class ImageAsy extends AsyncTask<String,Void,Bitmap> {
    public interface   ImageCallback{

        void claaback(Bitmap bitmap);
}

    private ImageCallback imageCallback;

    public ImageAsy(ImageCallback imageCallback) {
        this.imageCallback = imageCallback;
    }

    @Override
    protected Bitmap doInBackground(String... params) {

        try {
            String path = params[0];
            URL url = new URL(path);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5*1000);
            connection.setReadTimeout(5*1000);
            //服务器响应
            int code = connection.getResponseCode();
            if(code == HttpURLConnection.HTTP_OK){//判断服务器是否连接成功并相应
                //图片流
                InputStream is = connection.getInputStream();
                //将图片流转化成Bitmap位图，返回
                return BitmapFactory.decodeStream(is);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        if (bitmap!=null){
        imageCallback.claaback(bitmap);
    }}


}
