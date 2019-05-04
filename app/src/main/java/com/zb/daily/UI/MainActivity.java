package com.zb.daily.UI;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.view.MenuItem;
import com.alibaba.fastjson.JSON;
import com.hjq.toast.ToastUtils;
import com.zb.daily.BaseActivity;
import com.zb.daily.Constant;
import com.zb.daily.R;
import com.zb.daily.UI.fragment.*;
import com.zb.daily.adapter.AssetsMainListAdapter;
import com.zb.daily.adapter.CategoryMainListAdapter;
import com.zb.daily.adapter.RecordMainListAdapter;
import com.zb.daily.dao.AssetsDao;
import com.zb.daily.model.Assets;
import com.zb.daily.model.Category;
import com.zb.daily.model.Record;
import com.zb.daily.util.ActivityManager;
import org.litepal.tablemanager.Connector;

import java.util.ArrayList;
import java.util.List;

/**
 * @auther: zb
 * @Date: 2019/2/20 14:09
 * @Description: 主活动，包含多个fragment
 */
public class MainActivity extends BaseActivity {

    //滑动菜单
    private DrawerLayout drawerLayout;
    //滑动菜单里的菜单项
    private NavigationView navigationView;
    //声明一个long类型变量：用于存放上一点击“返回键”的时刻
    private long mExitTime;
    private AssetsDao assetsDao = new AssetsDao();

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
            case Constant.TO_CATEGORY_FRAGMENT:
                navigationView.setCheckedItem(R.id.nav_category);
                replaceFragment(new CategoryFragment());
                break;
            case Constant.TO_SETTING_FRAGMENT:
                navigationView.setCheckedItem(R.id.nav_site);
                replaceFragment(new SettingFragment());
                break;
            case Constant.TO_ABOUT_FRAGMENT:
                navigationView.setCheckedItem(R.id.nav_about);
                replaceFragment(new AboutFragment());
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
                    case R.id.nav_category:
                        replaceFragment(new CategoryFragment());
                        break;
                    case R.id.nav_site:
                        replaceFragment(new SettingFragment());
                        break;
                    case R.id.nav_about:
                        replaceFragment(new AboutFragment());
                        break;
                }
                menuItem.setCheckable(true);//选项可选
                menuItem.setChecked(true);//选项被选中
                drawerLayout.closeDrawers();//关闭滑动窗口
                return true;
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
           /* case 1001:
                //添加记录页面返回局部刷新item
                if (resultCode == RESULT_OK){
                    String returnData = data.getStringExtra("record_add_return");
                    List<Record> recordList = new ArrayList<>();
                    recordList = JSON.parseObject(returnData, recordList.getClass());

                    RecordMainListAdapter adapter = (RecordMainListAdapter) IndexFragment.getRecordAdapter();
                    adapter.getRecordList().addAll(recordList);
                    adapter.notifyDataSetChanged();
                }
                break;*/
            /*case 3001:
                //资产详情页面返回局部刷新item
                if (resultCode == RESULT_OK){
                    String returnData = data.getStringExtra("assets_detail_return");
                    int position = data.getIntExtra("position_detail_return", -1);
                    Assets assets = JSON.parseObject(returnData, Assets.class);
                    if (assets.getType() == 1){
                        AssetsMainListAdapter adapter = (AssetsMainListAdapter) AssetsFragment.getAssetsAdapter();
                        adapter.getAssetsList().set(position, assets);
                        adapter.notifyItemChanged(position);
                    }
                    if (assets.getType() == 2){
                        AssetsMainListAdapter adapter = (AssetsMainListAdapter) AssetsFragment.getLiabilityAdapter();
                        adapter.getAssetsList().set(position, assets);
                        adapter.notifyItemChanged(position);
                    }
                }
                break;*/
            /*case 3002:
                //资产转账页面返回全局刷新item
                if (resultCode == RESULT_OK){
                    List<Assets> assetsList = assetsDao.findAssetsListByType(1);
                    List<Assets> liabilityList = assetsDao.findAssetsListByType(2);

                    AssetsMainListAdapter assetsListAdapter = (AssetsMainListAdapter) AssetsFragment.getAssetsAdapter();
                    assetsListAdapter.getAssetsList().clear();
                    assetsListAdapter.getAssetsList().addAll(assetsList);

                    AssetsMainListAdapter liabilityListAdapter = (AssetsMainListAdapter) AssetsFragment.getLiabilityAdapter();
                    liabilityListAdapter.getAssetsList().clear();
                    liabilityListAdapter.getAssetsList().addAll(liabilityList);

                    assetsListAdapter.notifyDataSetChanged();
                    liabilityListAdapter.notifyDataSetChanged();
                }
                break;*/
            case 4001:
                //分类添加页面返回局部刷新item
                if (resultCode == RESULT_OK){
                    String returnData = data.getStringExtra("category_add_return");
                    Category category = JSON.parseObject(returnData, Category.class);
                    if (category.getType() == 1){
                        CategoryMainListAdapter adapter = (CategoryMainListAdapter) CategoryFragment.getOutAdapter();
                        adapter.getCategoryList().add(category);
                        adapter.notifyItemInserted(adapter.getItemCount() + 1);
                    }
                    if (category.getType() == 2){
                        CategoryMainListAdapter adapter = (CategoryMainListAdapter) CategoryFragment.getInAdapter();
                        adapter.getCategoryList().add(category);
                        adapter.notifyItemInserted(adapter.getItemCount() + 1);
                    }
                }
                break;
            case 4002:
                //分类修改页面返回局部刷新item
                if (resultCode == RESULT_OK){
                    String returnData = data.getStringExtra("category_update_return");
                    int position = data.getIntExtra("position_update_return", -1);
                    Category category = JSON.parseObject(returnData, Category.class);
                    if (category.getType() == 1){
                        CategoryMainListAdapter adapter = (CategoryMainListAdapter) CategoryFragment.getOutAdapter();
                        adapter.getCategoryList().set(position, category);
                        adapter.notifyItemChanged(position);
                    }
                    if (category.getType() == 2){
                        CategoryMainListAdapter adapter = (CategoryMainListAdapter) CategoryFragment.getInAdapter();
                        adapter.getCategoryList().set(position,category);
                        adapter.notifyItemChanged(position);
                    }
                }
                break;
            default:
        }
    }

    //动态切换fragment
    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.content_frame, fragment);
        transaction.commit();
    }

    //双击退出程序
    @Override
    public void onBackPressed() {
        //与上次点击返回键时刻作差
        if ((System.currentTimeMillis() - mExitTime) > 2000) {
            //大于2000ms则认为是误操作，使用Toast进行提示
            ToastUtils.show("再按一次退出程序");
            //并记录下本次点击“返回键”的时刻，以便下次进行判断
            mExitTime = System.currentTimeMillis();
        } else {
            //小于2000ms则认为是用户确实希望退出程序-调用System.exit()方法进行退出

            //程序完全退出
            ActivityManager.finishAll();
            android.os.Process.killProcess(android.os.Process.myPid());

            //返回桌面，后台不退出
            /*Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            startActivity(intent);*/
        }
    }

    //启动本活动
    public static void actionStart(Context context, int to){
        Intent intent = new Intent();
        intent.setClass(context, MainActivity.class);
        intent.putExtra("to", to);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
        context.startActivity(intent);
        /*((BaseActivity)context).startActivityForResult(intent,1);*/
    }
}
