package com.zb.daily;


import android.app.Application;
import android.content.Context;
import com.hjq.toast.ToastUtils;
import com.zb.daily.dao.DBInit;
import com.zb.daily.util.SPUtil;
import org.litepal.LitePal;

/**
 * @auther: zb
 * @Date: 2019/2/23 21:34
 * @Description: 替换原来的Application，方便在任意类中获取一些对象
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

        //数据初始化
        data_init = (boolean)SPUtil.get(context, Constant.TEXT_DATA_INIT, false);
        if (!data_init){
            //初始化资产和负债列表
            DBInit.assetsInit();
            //初始化分类列表
            DBInit.categoryInit();
            //初始化记录列表
            DBInit.recordInit();

            //设置数据已经初始化
            SPUtil.put(context, Constant.TEXT_DATA_INIT, true);
        }
    }

    public static Context getContext(){
        return context;
    }
}
