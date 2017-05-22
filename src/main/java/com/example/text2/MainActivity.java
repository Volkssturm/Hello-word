package com.example.text2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.example.text2.Fragment.Fr1;
import com.example.text2.Fragment.Fr2;
import com.example.text2.Fragment.Fr3;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private int theme = 0;
    private android.support.v4.app.FragmentManager fragmentManager;
    private Fr1 fr1;
    private Fr2 fr2;
    private Fr3 fr3;
    private UMAuthListener umAuthListener = new UMAuthListener() {
        @Override
        public void onStart(SHARE_MEDIA platform) {
            //授权开始的回调
        }
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            Set<String> set = data.keySet();
            SharedPreferences qq = getSharedPreferences("QQ", MODE_PRIVATE);
            SharedPreferences.Editor edit = qq.edit();
            for (String string : set) {
                String str = data.get(string);
                // 设置头像
                String touxiang = data.get("profile_image_url");
                edit.putString("头像",touxiang);
                // 设置昵称
                String nicheng = data.get("screen_name");
                edit.putString("昵称",nicheng);
                edit.putBoolean("状态",true);
                edit.commit();
                if (string.equals("province")) {
                }
                if (string.equals("city")) {
                }
                if (string.equals("gender")) {
                }
                if (string.equals("uid")) {
                }
                if (string.equals("yellow_vip_level")) {
                }
            }
            Intent intentqq=new Intent(MainActivity.this,QQactivity.class);
            startActivity(intentqq);
//         Toast.makeText(MainActivityQQ.this,"QQ已授权登录",Toast.LENGTH_SHORT).show();
            finish();
        }
        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            Toast.makeText( getApplicationContext(), "授权错误", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            Toast.makeText( getApplicationContext(), "授权失败", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Night_styleutils.changeStyle(this, theme, savedInstanceState);
         requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (savedInstanceState == null) {
            theme = UiUtils.getAppTheme(MainActivity.this);
        } else {
            theme = savedInstanceState.getInt("theme");
        }
        setTheme(theme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);





        Button button= (Button) findViewById(R.id.button1);
        Button button2= (Button) findViewById(R.id.button2);
        Button button3= (Button) findViewById(R.id.button3);
        Button button4= (Button) findViewById(R.id.button4);
        Button button5= (Button) findViewById(R.id.button5);

        button.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button5.setOnClickListener(this);
        fragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fr1 = new Fr1();
        fr2 = new Fr2();
        fr3 = new Fr3();
        fragmentTransaction.add(R.id.flm, fr1);
        fragmentTransaction.add(R.id.flm, fr2).hide(fr2);
        fragmentTransaction.add(R.id.flm, fr3).hide(fr3);
        fragmentTransaction.commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case  R.id.button1:
                android.support.v4.app.FragmentTransaction fragmentTransaction1 = fragmentManager.beginTransaction();
                fragmentTransaction1.show(fr1).hide(fr2).hide(fr3).commit();

                break;
            case  R.id.button2:
                android.support.v4.app.FragmentTransaction fragmentTransaction2 = fragmentManager.beginTransaction();
                fragmentTransaction2.show(fr2).hide(fr1).hide(fr3).commit();
                break;
            case  R.id.button3:
                android.support.v4.app.FragmentTransaction fragmentTransaction3 = fragmentManager.beginTransaction();
                fragmentTransaction3.show(fr3).hide(fr2).hide(fr1).commit();
                break;
            case  R.id.button4:
                UiUtils.switchAppTheme(MainActivity.this);
                MainActivity.this.reload();
                break;
            case  R.id.button5:

                getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

                UMShareAPI.get(MainActivity.this).getPlatformInfo(MainActivity.this,
                        SHARE_MEDIA.QQ, umAuthListener);
                break;
        }
    }
    public void reload() {
        Intent intent = getIntent();
        overridePendingTransition(R.anim.activity_in, R.anim.activity_out);//进入动画
        finish();
        overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
        startActivity(intent);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    protected void onDestroy() {
        super.onDestroy();
        UMShareAPI.get(MainActivity.this).deleteOauth(MainActivity.this, SHARE_MEDIA.QQ,null);}
}
