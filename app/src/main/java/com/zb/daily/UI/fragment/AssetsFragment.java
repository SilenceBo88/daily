package com.zb.daily.UI.fragment;

import android.content.Intent;
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
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.alibaba.fastjson.JSON;
import com.zb.daily.MyApplication;
import com.zb.daily.R;
import com.zb.daily.UI.AssetsAddActivity;
import com.zb.daily.UI.AssetsTransferActivity;
import com.zb.daily.UI.helper.AssetsMainItemTouchCallback;
import com.zb.daily.UI.helper.MyItemTouchCallback;
import com.zb.daily.UI.helper.StartDragListener;
import com.zb.daily.adapter.AssetsMainListAdapter;
import com.zb.daily.dao.AssetsDao;
import com.zb.daily.model.Assets;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * @auther: zb
 * @Date: 2019/2/22 18:01
 * @Description: 资产管理页面，实现StartDragListener主要用来实现长按交换item数据
 */
public class AssetsFragment extends Fragment implements StartDragListener {

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
    //资产数据库查询
    private AssetsDao assetsDao = new AssetsDao();
    //触摸事件帮助类
    private ItemTouchHelper itemtouchhelper;
    //触摸事件帮助类
    private ItemTouchHelper itemtouchhelper2;

    static AssetsMainListAdapter assetsAdapter = null;
    static AssetsMainListAdapter liabilityAdapter = null;

    public static RecyclerView.Adapter getAssetsAdapter() {
        return assetsAdapter;
    }

    public static RecyclerView.Adapter getLiabilityAdapter() {
        return liabilityAdapter;
    }

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
        Toolbar toolbar=  appCompatActivity.findViewById(R.id.fragment_assets_toolbar);
        toolbar.setTitle("");
        appCompatActivity.setSupportActionBar(toolbar);

        //菜单按钮打开滑动窗口
        menuButton = activity.findViewById(R.id.fragment_assets_btn_menu);
        drawerLayout = activity.findViewById(R.id.drawer_layout);
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);//打开滑动菜单
            }
        });

        //添加按钮点击事件，打开添加资产页面
        addButton = activity.findViewById(R.id.fragment_assets_btn_add);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AssetsAddActivity.actionStart(activity);
            }
        });

        //悬浮按钮点击事件，打开转账页面
        fab = activity.findViewById(R.id.fragment_assets_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AssetsTransferActivity.actionStart(activity);
            }
        });

        //查询资产和负债列表
        assetsList = assetsDao.findAssetsListByType(1);
        liabilityList = assetsDao.findAssetsListByType(2);

        //资产账户的list适配
        RecyclerView assetsRecyclerView = activity.findViewById(R.id.fragment_assets_assetsRecyclerView);
        LinearLayoutManager assetsLayoutManager = new LinearLayoutManager(MyApplication.getContext());
        assetsRecyclerView.setLayoutManager(assetsLayoutManager);
        assetsAdapter = new AssetsMainListAdapter(assetsList, this);
        assetsRecyclerView.setAdapter(assetsAdapter);

        //负债账户的list适配
        RecyclerView liabilityRecyclerView = activity.findViewById(R.id.fragment_assets_liabilityRecyclerView);
        LinearLayoutManager liabilityLayoutManager = new LinearLayoutManager(MyApplication.getContext());
        liabilityRecyclerView.setLayoutManager(liabilityLayoutManager);
        liabilityAdapter = new AssetsMainListAdapter(liabilityList, this);
        liabilityRecyclerView.setAdapter(liabilityAdapter);

        //资产账户RecyclerView 注册item触摸事件，来实现长按交换item数据
        ItemTouchHelper.Callback callback = new AssetsMainItemTouchCallback(assetsAdapter);
        itemtouchhelper = new ItemTouchHelper(callback);
        itemtouchhelper.attachToRecyclerView(assetsRecyclerView);

        //负债账户RecyclerView 注册item触摸事件，来实现长按交换item数据
        ItemTouchHelper.Callback callback2 = new AssetsMainItemTouchCallback(liabilityAdapter);
        itemtouchhelper2 = new ItemTouchHelper(callback2);
        itemtouchhelper2.attachToRecyclerView(liabilityRecyclerView);
    }

    //实现长按交换item数据
    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        itemtouchhelper.startDrag(viewHolder);
        itemtouchhelper2.startDrag(viewHolder);
    }
}
