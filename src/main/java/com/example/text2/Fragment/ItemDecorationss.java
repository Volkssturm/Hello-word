package com.example.text2.Fragment;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;

import com.example.text2.MainActivity;

/**
 * com.example.text2.Fragment
 * 徐世辉  1503A
 * 类作用：本类---
 * 思路：
 * 1.
 * 2.
 * 3.
 * 2017/5/18 20:04
 */

public class ItemDecorationss extends RecyclerView.ItemDecoration {

    private Context context;
    private int whith;
    private int heigh;

    public ItemDecorationss(Context context) {
        this.context = context;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        Paint paint =new Paint();
        paint.setColor(Color.BLUE);
        int child=parent.getChildCount();
        DisplayMetrics displayMetrics=new DisplayMetrics();

        ((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        whith = displayMetrics.widthPixels;
        heigh = displayMetrics.heightPixels;
        for (int i = 0; i <child ; i++) {
        View v =parent.getChildAt(i);
            c.drawLine(v.getLeft(),v.getBottom(),whith,v.getBottom()+3,paint);
        }

    }



    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
    }
}
