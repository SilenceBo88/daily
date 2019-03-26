package com.zb.daily.UI.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import com.zb.daily.MyApplication;
import com.zb.daily.R;
import com.zb.daily.UI.helper.MyItemTouchCallback;
import com.zb.daily.UI.helper.StartDragListener;
import com.zb.daily.adapter.CategoryMainListAdapter;
import com.zb.daily.dao.CategoryDao;
import com.zb.daily.model.Category;

import java.util.ArrayList;
import java.util.List;

/**
 * @auther: zb
 * @Date: 2019/2/22 18:01
 * @Description: 分类管理页面，实现StartDragListener主要用来实现长按交换item数据
 */
public class CategoryFragment extends Fragment implements StartDragListener {

    public FragmentActivity activity;

    //滑动菜单
    private DrawerLayout drawerLayout;
    //菜单按钮
    private Button menuButton;
    //添加按钮
    private Button addButton;
    //支出集合
    private List<Category> outList = new ArrayList<>();
    //收入集合
    private List<Category> inList = new ArrayList<>();
    //资产数据库查询
    private CategoryDao categoryDao = new CategoryDao();
    //触摸事件帮助类
    private ItemTouchHelper itemtouchhelper;
    //触摸事件帮助类
    private ItemTouchHelper itemtouchhelper2;

    static CategoryMainListAdapter outAdapter = null;
    static CategoryMainListAdapter inAdapter = null;

    public static RecyclerView.Adapter getOutAdapter() {
        return outAdapter;
    }

    public static RecyclerView.Adapter getInAdapter() {
        return inAdapter;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        activity = getActivity();
        View view = inflater.inflate(R.layout.fragment_category, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //用Toolbar替换ActionBar
        setHasOptionsMenu(true);
        AppCompatActivity appCompatActivity= (AppCompatActivity) getActivity();
        Toolbar toolbar=  appCompatActivity.findViewById(R.id.fragment_category_toolbar);
        toolbar.setTitle("");
        appCompatActivity.setSupportActionBar(toolbar);

        //菜单按钮打开滑动窗口
        menuButton = activity.findViewById(R.id.fragment_category_btn_menu);
        drawerLayout = activity.findViewById(R.id.drawer_layout);
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);//打开滑动菜单
            }
        });

        //添加按钮点击事件，打开添加资产页面
        addButton = activity.findViewById(R.id.fragment_category_btn_add);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*AssetsAddActivity.actionStart(activity);*/
            }
        });

        //查询支出与收入列表
        outList = categoryDao.findAssetsListByType(1);
        inList = categoryDao.findAssetsListByType(2);

        //支出的list适配
        RecyclerView outRecyclerView = activity.findViewById(R.id.fragment_category_outRecyclerView);
        LinearLayoutManager outLayoutManager = new LinearLayoutManager(MyApplication.getContext());
        outRecyclerView.setLayoutManager(outLayoutManager);
        outAdapter = new CategoryMainListAdapter(outList, this);
        outRecyclerView.setAdapter(outAdapter);

        //收入的list适配
        RecyclerView inRecyclerView = activity.findViewById(R.id.fragment_category_inRecyclerView);
        LinearLayoutManager inLayoutManager = new LinearLayoutManager(MyApplication.getContext());
        inRecyclerView.setLayoutManager(inLayoutManager);
        inAdapter = new CategoryMainListAdapter(inList, this);
        inRecyclerView.setAdapter(inAdapter);

        //支出RecyclerView 注册item触摸事件，来实现长按交换item数据
        ItemTouchHelper.Callback callback = new MyItemTouchCallback(outAdapter);
        itemtouchhelper = new ItemTouchHelper(callback);
        itemtouchhelper.attachToRecyclerView(outRecyclerView);

        //收入RecyclerView 注册item触摸事件，来实现长按交换item数据
        ItemTouchHelper.Callback callback2 = new MyItemTouchCallback(inAdapter);
        itemtouchhelper2 = new ItemTouchHelper(callback2);
        itemtouchhelper2.attachToRecyclerView(inRecyclerView);
    }

    //实现长按交换item数据
    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        itemtouchhelper.startDrag(viewHolder);
        itemtouchhelper2.startDrag(viewHolder);
    }
}
