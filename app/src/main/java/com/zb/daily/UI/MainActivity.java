package com.zb.daily.UI;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import com.hjq.toast.ToastUtils;
import com.zb.daily.Constant;
import com.zb.daily.R;
import com.zb.daily.UI.fragment.AssetsFragment;
import com.zb.daily.UI.fragment.ChartFragment;
import com.zb.daily.UI.fragment.IndexFragment;
import org.litepal.tablemanager.Connector;

/**
 * @auther: zb
 * @Date: 2019/2/20 14:09
 * @Description: 主活动
 */
public class MainActivity extends AppCompatActivity {

    //滑动菜单
    private DrawerLayout drawerLayout;
    //滑动菜单里的菜单项
    private NavigationView navigationView;
    //声明一个long类型变量：用于存放上一点击“返回键”的时刻
    private long mExitTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //创建数据库
        Connector.getDatabase();

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        //替换fragment
        int to = getIntent().getIntExtra("to",Constant.TO_INDEX_FRAGMENT);
        switch (to){
            case Constant.TO_INDEX_FRAGMENT:
                navigationView.setCheckedItem(R.id.nav_main);
                replaceFragment(new IndexFragment());
                break;
            case Constant.TO_CHART_FRAGMENT:
                navigationView.setCheckedItem(R.id.nav_chart);
                replaceFragment(new ChartFragment());
                break;
            case Constant.TO_ASSETS_FRAGMENT:
                navigationView.setCheckedItem(R.id.nav_assets);
                replaceFragment(new AssetsFragment());
                break;
        }

        //处理点击菜单项产生的事件
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.nav_main:
                        replaceFragment(new IndexFragment());
                        break;
                    case R.id.nav_chart:
                        replaceFragment(new ChartFragment());
                        break;
                    case R.id.nav_assets:
                        replaceFragment(new AssetsFragment());
                        break;
                }
                menuItem.setCheckable(true);//选项可选
                menuItem.setChecked(true);//选项被选中
                drawerLayout.closeDrawers();//关闭滑动窗口
                return true;
            }
        });
    }

    //动态切换fragment
    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.content_frame, fragment);
        transaction.commit();
    }

    //退出程序
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //判断用户是否点击了“返回键”
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //与上次点击返回键时刻作差
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                //大于2000ms则认为是误操作，使用Toast进行提示
                ToastUtils.show("再按一次退出程序");
                //并记录下本次点击“返回键”的时刻，以便下次进行判断
                mExitTime = System.currentTimeMillis();
            } else {
                //小于2000ms则认为是用户确实希望退出程序-调用System.exit()方法进行退出
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                startActivity(intent);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
