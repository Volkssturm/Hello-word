package com.example.text2.Fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.text2.Bean1;
import com.example.text2.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * com.example.text2.Fragment
 * 徐世辉  1503A
 * <p>
 * 2017/5/3
 */

public class MyAdapter  extends BaseAdapter{
    private Context context;
    private List<Bean1.DataBean> data;
    private final DisplayImageOptions displayImageOptions;

    public MyAdapter(Context context, List<Bean1.DataBean> data) {
        this.context = context;
        this.data = data;

        //是否内存缓存
//是否sdcard缓存
//构建图片缓存的选项
        displayImageOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)//是否内存缓存
                .cacheOnDisk(true)//是否sdcard缓存
                .build();
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHandler viewHandler;
        if (convertView==null){
            viewHandler=new ViewHandler();
            convertView=View.inflate(context, R.layout.item,null);
            viewHandler.imageView= (ImageView) convertView.findViewById(R.id.item_image);
            viewHandler.textView= (TextView) convertView.findViewById(R.id.item_text);
            viewHandler.textView1= (TextView) convertView.findViewById(R.id.item_text2);
            convertView.setTag(viewHandler);
        }else {
            viewHandler= (ViewHandler) convertView.getTag();
        }
       viewHandler.textView.setText(data.get(position).getTITLE());
       viewHandler.textView1.setText(data.get(position).getSUBTITLE());
        if (data.get(position).getIMAGEURL()!=null) {
            //viewHandler.getimage(data.get(position).getIMAGEURL());
            ImageLoader.getInstance().displayImage(data.get(position).getIMAGEURL(),viewHandler.imageView,displayImageOptions);
        }else {
            viewHandler.imageView.setVisibility(View.GONE);
        }
        return convertView;
    }
    class ViewHandler{
        private ImageView imageView;
        private TextView textView,textView1;


    public void getimage(String url){

        ImageAsy imageAsy=new ImageAsy(new ImageAsy.ImageCallback() {
            @Override
            public void claaback(Bitmap bitmap) {

                if (bitmap!=null){
                 imageView.setImageBitmap(bitmap);
                }
            }
        });
        imageAsy.execute(url);
    }}
}
