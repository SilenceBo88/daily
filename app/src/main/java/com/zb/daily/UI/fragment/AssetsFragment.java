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
import android.widget.Toast;
import com.zb.daily.MyApplication;
import com.zb.daily.R;
import com.zb.daily.UI.AddAssetsActivity;
import com.zb.daily.UI.helper.MyItemTouchCallback;
import com.zb.daily.UI.helper.StartDragListener;
import com.zb.daily.adapter.AssetsAdapter;
import com.zb.daily.dao.AssetsDao;
import com.zb.daily.model.Assets;

import java.util.ArrayList;
import java.util.List;

/**
 * @auther: zb
 * @Date: 2019/2/22 18:01
 * @Description: 资产列表页面
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
            Intent intent = new Intent();
            intent.setClass(activity, AddAssetsActivity.class);
            startActivityForResult(intent,1);
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

        //查询资产和负债列表
        assetsList = assetsDao.findAssetsListByType(1);
        liabilityList = assetsDao.findAssetsListByType(2);

        //资产账户的滑动控件
        RecyclerView assetsRecyclerView = activity.findViewById(R.id.assets_assets_recyclerView);
        LinearLayoutManager assetsLayoutManager = new LinearLayoutManager(MyApplication.getContext());
        assetsRecyclerView.setLayoutManager(assetsLayoutManager);
        AssetsAdapter assetsAdapter = new AssetsAdapter(assetsList, this);
        assetsRecyclerView.setAdapter(assetsAdapter);

        //负债账户的滑动控件
        RecyclerView liabilityRecyclerView = activity.findViewById(R.id.assets_liability_recyclerView);
        LinearLayoutManager liabilityLayoutManager = new LinearLayoutManager(MyApplication.getContext());
        liabilityRecyclerView.setLayoutManager(liabilityLayoutManager);
        AssetsAdapter liabilityAdapter = new AssetsAdapter(liabilityList, this);
        liabilityRecyclerView.setAdapter(liabilityAdapter);

        //assetsRecyclerView 注册item滑动事件
        ItemTouchHelper.Callback callback = new MyItemTouchCallback(assetsAdapter);
        itemtouchhelper = new ItemTouchHelper(callback);
        itemtouchhelper.attachToRecyclerView(assetsRecyclerView);

        //liabilityRecyclerView 注册item滑动事件
        ItemTouchHelper.Callback callback2 = new MyItemTouchCallback(liabilityAdapter);
        itemtouchhelper2 = new ItemTouchHelper(callback2);
        itemtouchhelper2.attachToRecyclerView(liabilityRecyclerView);
    }

    //执行点击图标进行拖动的效果
    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        itemtouchhelper.startDrag(viewHolder);
        itemtouchhelper2.startDrag(viewHolder);
    }
}
