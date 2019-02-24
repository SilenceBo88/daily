package com.zb.daily.UI.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import com.zb.daily.MyApplication;
import com.zb.daily.R;
import com.zb.daily.adapter.AssetsAdapter;
import com.zb.daily.model.Assets;

import java.util.ArrayList;
import java.util.List;

/**
 * @auther: zb
 * @Date: 2019/2/22 18:01
 * @Description: 资产列表页面
 */
public class AssetsFragment extends Fragment {

    public FragmentActivity activity;

    //悬浮按钮
    private FloatingActionButton fab;
    //滑动菜单
    private DrawerLayout drawerLayout;
    //菜单按钮
    private Button menuButton;
    //添加按钮
    private Button addButton;
    //资产账户集合
    private List<Assets> assetsList = new ArrayList<>();
    //负债账户集合
    private List<Assets> liabilityList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        activity = getActivity();
        View view = inflater.inflate(R.layout.fragment_assets, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //用Toolbar替换ActionBar
        setHasOptionsMenu(true);
        AppCompatActivity appCompatActivity= (AppCompatActivity) getActivity();
        Toolbar toolbar=  appCompatActivity.findViewById(R.id.assets_toolbar);
        toolbar.setTitle("");
        appCompatActivity.setSupportActionBar(toolbar);

        //菜单按钮打开滑动窗口
        menuButton = activity.findViewById(R.id.assets_btn_menu);
        drawerLayout = activity.findViewById(R.id.drawer_layout);
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);//打开滑动菜单
            }
        });

        //添加按钮点击事件
        addButton = activity.findViewById(R.id.assets_btn_add);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "添加资产账户", Toast.LENGTH_SHORT).show();
            }
        });

        //悬浮按钮点击事件
        fab = activity.findViewById(R.id.assets_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "转账", Toast.LENGTH_SHORT).show();
            }
        });

        //初始化资产和负债列表
        init();

        //资产账户的滑动控件
        RecyclerView assetsRecyclerView = activity.findViewById(R.id.assets_assets_recyclerView);
        LinearLayoutManager assetsLayoutManager = new LinearLayoutManager(MyApplication.getContext());
        assetsRecyclerView.setLayoutManager(assetsLayoutManager);
        AssetsAdapter assetsAdapter = new AssetsAdapter(assetsList);
        assetsRecyclerView.setAdapter(assetsAdapter);

        //负债账户的滑动控件
        RecyclerView liabilityRecyclerView = activity.findViewById(R.id.assets_liability_recyclerView);
        LinearLayoutManager liabilityLayoutManager = new LinearLayoutManager(MyApplication.getContext());
        liabilityRecyclerView.setLayoutManager(liabilityLayoutManager);
        AssetsAdapter liabilityAdapter = new AssetsAdapter(liabilityList);
        liabilityRecyclerView.setAdapter(liabilityAdapter);
    }

    //初始化资产和负债列表
    private void init() {
        Assets assets3 = new Assets();
        assets3.setName("现金");
        assets3.setImageId(R.drawable.assets_cash);
        assets3.setBalance(200.00);
        assetsList.add(assets3);

        Assets assets1 = new Assets();
        assets1.setName("支付宝");
        assets1.setImageId(R.drawable.assets_alipay);
        assets1.setBalance(500.00);
        assetsList.add(assets1);

        Assets assets2 = new Assets();
        assets2.setName("微信");
        assets2.setImageId(R.drawable.assets_wechat);
        assets2.setBalance(1000.00);
        assetsList.add(assets2);

        Assets assets4 = new Assets();
        assets4.setName("储蓄卡");
        assets4.setImageId(R.drawable.assets_save);
        assets4.setBalance(10000.00);
        assetsList.add(assets4);

        Assets assets5 = new Assets();
        assets5.setName("充值卡");
        assets5.setImageId(R.drawable.assets_recharge);
        assets5.setBalance(100.00);
        assetsList.add(assets5);

        Assets assets6 = new Assets();
        assets6.setName("应收账");
        assets6.setImageId(R.drawable.assets_receipt);
        assets6.setBalance(200.00);
        assetsList.add(assets6);

        Assets liability2 = new Assets();
        liability2.setName("信用卡");
        liability2.setImageId(R.drawable.assets_credit);
        liability2.setBalance(200.00);
        liabilityList.add(liability2);

        Assets liability3 = new Assets();
        liability3.setName("花呗");
        liability3.setImageId(R.drawable.assets_flower);
        liability3.setBalance(300.00);
        liabilityList.add(liability3);

        Assets liability4 = new Assets();
        liability4.setName("京东白条");
        liability4.setImageId(R.drawable.assets_jd);
        liability4.setBalance(200.00);
        liabilityList.add(liability4);

        Assets liability1 = new Assets();
        liability1.setName("应付账");
        liability1.setImageId(R.drawable.assets_pay);
        liability1.setBalance(1200.00);
        liabilityList.add(liability1);
    }
}
