package com.zb.daily;


import android.app.Application;
import android.content.Context;
import org.litepal.LitePal;
import org.litepal.LitePalApplication;

/**
 * @auther: zb
 * @Date: 2019/2/23 21:34
 * @Description:替换原来的Application，方便在任意类中获取一些对象
 */
public class MyApplication extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        //加载数据库第三方库
        LitePal.initialize(context);
    }

    public static Context getContext(){
        return context;
    }
}
