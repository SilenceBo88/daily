package com.zb.daily.UI;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import com.alibaba.fastjson.JSONArray;
import com.zb.daily.BaseActivity;
import com.zb.daily.Constant;
import com.zb.daily.MyApplication;
import com.zb.daily.R;
import com.zb.daily.adapter.AssetsAddListAdapter;
import com.zb.daily.model.Assets;
import com.zb.daily.util.SPUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @auther: zb
 * @Date: 2019/2/22 18:01
 * @Description: 添加资产页面
 */
public class AssetsAddActivity extends BaseActivity {

    //返回按钮
    private Button addPreButton;
    //资产全部列表
    private List<Assets> assetsAddAllList = new ArrayList<>();
    //资产账户集合
    private List<Assets> assetsAddList = new ArrayList<>();
    //负债账户集合
    private List<Assets> liabilityAddList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assets_add);

        //返回按钮
        addPreButton = findViewById(R.id.activity_assets_add_btn_pre);
        addPreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //资产和负债列表
        boolean bol = SPUtil.contains(MyApplication.getContext(), Constant.TEXT_ASSETS_ADD_LIST);
        String jsonString = "";
        if (bol){
            jsonString = SPUtil.get(MyApplication.getContext(), Constant.TEXT_ASSETS_ADD_LIST, jsonString).toString();
            assetsAddAllList = JSONArray.parseArray(jsonString, Assets.class);
            for (Assets assets : assetsAddAllList){
                if (assets.getType() == 1){
                    assetsAddList.add(assets);
                }
                if (assets.getType() == 2){
                    liabilityAddList.add(assets);
                }
            }
        }

        //资产账户的list适配
        RecyclerView assetsRecyclerView = findViewById(R.id.activity_assets_add_assets_recyclerView);
        LinearLayoutManager assetsLayoutManager = new LinearLayoutManager(MyApplication.getContext());
        assetsRecyclerView.setLayoutManager(assetsLayoutManager);
        AssetsAddListAdapter assetsAdapter = new AssetsAddListAdapter(assetsAddList);
        assetsRecyclerView.setAdapter(assetsAdapter);

        //负债账户的list适配
        RecyclerView liabilityRecyclerView = findViewById(R.id.activity_assets_add_liability_recyclerView);
        LinearLayoutManager liabilityLayoutManager = new LinearLayoutManager(MyApplication.getContext());
        liabilityRecyclerView.setLayoutManager(liabilityLayoutManager);
        AssetsAddListAdapter liabilityAdapter = new AssetsAddListAdapter(liabilityAddList);
        liabilityRecyclerView.setAdapter(liabilityAdapter);
    }

    //启动本活动
    public static void actionStart(Context context){
        Intent intent = new Intent();
        intent.setClass(context, AssetsAddActivity.class);
        context.startActivity(intent);
        /*((BaseActivity)context).startActivityForResult(intent,1);*/
    }
}
