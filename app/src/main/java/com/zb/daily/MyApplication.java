package com.zb.daily;


import android.app.Application;
import android.content.Context;
import com.hjq.toast.ToastUtils;
import com.zb.daily.dao.DBInit;
import com.zb.daily.util.SPUtil;
import org.litepal.LitePal;
import org.litepal.LitePalApplication;

/**
 * @auther: zb
 * @Date: 2019/2/23 21:34
 * @Description:替换原来的Application，方便在任意类中获取一些对象
 */
public class MyApplication extends Application {

    private static Context context;

    private static boolean data_init;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        //加载数据库第三方库
        LitePal.initialize(context);
        //初始化Toast工具类
        ToastUtils.init(this);

        data_init = (boolean)SPUtil.get(context, Constant.DATA_INIT_TEXT, false);
        if (!data_init){
            //初始化资产和负债列表
            DBInit.assetsInit();
            //设置数据已经初始化
            SPUtil.put(context, Constant.DATA_INIT_TEXT, true);
        }
    }

    public static Context getContext(){
        return context;
    }
}
