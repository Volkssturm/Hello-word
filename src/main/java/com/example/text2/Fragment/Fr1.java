package com.example.text2.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.text2.Bean1;
import com.example.text2.R;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import static com.example.text2.R.styleable.View;

/**
 * com.example.text2.Fragment
 * 徐世辉  1503A
 * <p>
 * 2017/5/3
 */

public class Fr1 extends Fragment {

    private ListView listView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fr1,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        listView = (ListView) getView().findViewById(R.id.fr1_lv);
        int connType = Getitem.getAPNType(getActivity());
        if (connType == 1){
            Log.e("onActivityCreated: ","是wifi网络" );
            getids("http://www.93.gov.cn/93app/data.do");
        }else if (connType >1){
            Log.e("onActivityCreated: ","是移动网络" );
            AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
                    .setTitle("移动网络继续访问")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            getids("http://www.93.gov.cn/93app/data.do");
                        }
                    })
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(getActivity(),"将不再移动数据下访问",Toast.LENGTH_SHORT).show();
                        }
                    }).show();
        }else {
            Log.e("onActivityCreated: ","没有网络" );
            AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
                    .setTitle("没有网络")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(getActivity(),"等待网络链接",Toast.LENGTH_SHORT).show();
                            try {
                                if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
                                {
                                    File file = new File(Environment.getExternalStorageDirectory(),"66.txt");
                                    if(file.exists())
                                        try {
                                            FileInputStream fileInputStream= new FileInputStream(file);
                                            ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
                                            byte[] b =new byte[1024];
                                            int read = 0;
                                            while ((read = fileInputStream.read(b))!= -1){
                                                byteArrayOutputStream.write(b,0,read);
                                            }
                                           String ll= byteArrayOutputStream.toString();
                                            Gson gson=new Gson();

                                            Bean1 bean=gson.fromJson(ll, Bean1.class);
                                            List<Bean1.DataBean> data1= bean.getData();

                                            MyAdapter m= new MyAdapter(getActivity(),data1);
                                            listView.setAdapter(m);


                                        } catch (FileNotFoundException e1) {
                                            e1.printStackTrace();
                                        }



                                } }
                            catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    }).show();
        }


    }

    public void  getids(String url){

        new AsyncTask<String,Void,String>(){
            @Override
            protected String doInBackground(String... params) {
                String p=params[0];
                return new Getitem().getitems(p,0,0);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Log.e( "onPostExecute: ","发那个地方公开就爱看上到几点"+s );

                Gson gson=new Gson();
                try {
                    if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
                {
                    File file = new File(Environment.getExternalStorageDirectory(),"66.txt");
                    if(file.exists())
                    {file.createNewFile();
                    }
                   FileOutputStream fileOutputStream= new FileOutputStream(file);
                    fileOutputStream.write(s.getBytes());

                    fileOutputStream.close();
                } }
                catch (IOException e) {
                e.printStackTrace();
            }
                Bean1 bean=gson.fromJson(s, Bean1.class);
                List<Bean1.DataBean> data= bean.getData();

               MyAdapter m= new MyAdapter(getActivity(),data);
                listView.setAdapter(m);


            }
        }.execute(url);
    }



}
