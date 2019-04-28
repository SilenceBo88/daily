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
import android.view.*;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.zb.daily.MyApplication;
import com.zb.daily.R;
import com.zb.daily.UI.RecordAddActivity;
import com.zb.daily.adapter.AssetsMainListAdapter;
import com.zb.daily.adapter.RecordMainListAdapter;
import com.zb.daily.dao.RecordDao;
import com.zb.daily.model.Record;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @auther: zb
 * @Date: 2019/2/22 18:01
 * @Description: 记账主页面
 */
public class IndexFragment extends Fragment {

    public FragmentActivity activity;

    //悬浮按钮
    private FloatingActionButton fab;
    //滑动菜单
    private DrawerLayout drawerLayout;
    //菜单按钮
    private Button menuButton;
    private List<Record> recordList = new ArrayList<>();
    private RecordDao recordDao = new RecordDao();

    private TextView monthIncome;
    private TextView monthOutlay;

    static RecordMainListAdapter adapter = null;

    public static RecyclerView.Adapter getRecordAdapter() {
        return adapter;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        activity = getActivity();
        View view = inflater.inflate(R.layout.fragment_index, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //用Toolbar替换ActionBar
        setHasOptionsMenu(true);
        AppCompatActivity appCompatActivity= (AppCompatActivity) getActivity();
        Toolbar toolbar=  appCompatActivity.findViewById(R.id.fragment_index_toolbar);
        toolbar.setTitle("");
        appCompatActivity.setSupportActionBar(toolbar);

        //菜单按钮打开滑动窗口
        menuButton = activity.findViewById(R.id.fragment_index_btn_menu);
        drawerLayout = activity.findViewById(R.id.drawer_layout);
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);//打开滑动菜单
            }
        });

        //悬浮按钮点击事件
        fab = activity.findViewById(R.id.fragment_index_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RecordAddActivity.actionStart(activity);
            }
        });

        monthIncome = activity.findViewById(R.id.tv_month_income);
        monthOutlay = activity.findViewById(R.id.tv_month_outlay);
        String month = new SimpleDateFormat("yyyy-MM").format(new Date());
        double out = recordDao.getMonthSummary(month, 1);
        double in = recordDao.getMonthSummary(month, 2);
        monthIncome.setText(String.valueOf(in));
        monthOutlay.setText(String.valueOf(out));

        recordList = recordDao.findRecordList();

        //记录的list适配
        RecyclerView recyclerView = activity.findViewById(R.id.fragment_index_recordRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(MyApplication.getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new RecordMainListAdapter(recordList);
        recyclerView.setAdapter(adapter);

        adapter.setSubClickListener(new RecordMainListAdapter.SubClickListener() {
            @Override
            public void OnTopicClickListener(String s) {
                String month = new SimpleDateFormat("yyyy-MM").format(new Date());
                double out = recordDao.getMonthSummary(month, 1);
                double in = recordDao.getMonthSummary(month, 2);
                monthIncome.setText(String.valueOf(in));
                monthOutlay.setText(String.valueOf(out));
            }
        });
    }
}
