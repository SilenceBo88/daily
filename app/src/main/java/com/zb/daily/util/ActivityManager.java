package com.zb.daily.util;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * @auther: zb
 * @Date: 2019/3/25 09:50
 * @Description: 活动管理
 */
public class ActivityManager {

    public static List<Activity> activities = new ArrayList<>();

    //添加一个活动
    public static void addActivity(Activity activity) {
        activities.add(activity);
    }

    //关闭一个活动
    public static void removeActivity(Activity activity) {
        activities.remove(activity);
    }

    //关闭所有活动
    public static void finishAll() {
        for (Activity activity : activities) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }
}
