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
import com.zb.daily.dao.AssetsDao;
import com.zb.daily.dao.DBInit;
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
    //
    private AssetsDao assetsDao = new AssetsDao();

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
        DBInit.assetsInit();
        assetsList = assetsDao.findAssetsListByType(1);
        liabilityList = assetsDao.findAssetsListByType(2);

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
}
