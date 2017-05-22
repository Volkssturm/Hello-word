package com.example.text2.Fragment;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;





/**
 * com.example.text2.Fragment
 * 徐世辉  1503A
 * <p>
 * 2017/5/3
 */

public class Getitem {
    public   String getitems(String url,int channelId,int startNum)  {
        try {
            URL url1=new URL(url);
            HttpURLConnection httpURLConnection= (HttpURLConnection) url1.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setReadTimeout(5000);
            httpURLConnection.setConnectTimeout(5000);

            // 要发送给服务器的请求的实体内容
            OutputStream os = httpURLConnection.getOutputStream();
            os.write(("channelId="+channelId+"&startNum="+startNum).getBytes());
            PrintWriter writer = new PrintWriter(os);
            //把数据刷出去
            writer.flush();

           int code= httpURLConnection.getResponseCode();
            if (code==200){
                InputStream inputStream= httpURLConnection.getInputStream();
                ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
                byte[] b =new byte[1024];
                int read = 0;
                while ((read = inputStream.read(b))!= -1){
                    byteArrayOutputStream.write(b,0,read);
                }
                return byteArrayOutputStream.toString();
            }


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return "";
    }

    /**
     * 获取当前的网络状态 ：没有网络0：WIFI网络1：移动数据2
     *
     * @param context
     * @return
     */
    public static int getAPNType(Context context) {
        int netType = 0;
        ConnectivityManager connMgr = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo == null) {
            return netType;
        }
        int nType = networkInfo.getType();
        if (nType == ConnectivityManager.TYPE_WIFI) {
            netType = 1;// wifi
        } else if (nType == ConnectivityManager.TYPE_MOBILE) {
            //有移动数据
            //int nSubType = networkInfo.getSubtype();
            /*TelephonyManager mTelephony = (TelephonyManager) context
                    .getSystemService(Context.TELEPHONY_SERVICE);
            if (nSubType == TelephonyManager.NETWORK_TYPE_UMTS
                    && !mTelephony.isNetworkRoaming()) {
                netType = 2;// 3G
            } else {
                netType = 3;// 2G
            }*/
            netType = 2;
        }
        return netType;
    }

}
