package com.zb.daily.UI;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import com.alibaba.fastjson.JSONArray;
import com.zb.daily.Constant;
import com.zb.daily.MyApplication;
import com.zb.daily.R;
import com.zb.daily.adapter.AssetsAddAdapter;
import com.zb.daily.dao.AssetsDao;
import com.zb.daily.model.Assets;
import com.zb.daily.util.SPUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @auther: zb
 * @Date: 2019/2/22 18:01
 * @Description: 添加资产列表页面
 */
public class AddAssetsActivity extends AppCompatActivity {

    //菜单按钮
    private Button addPreButton;
    //资产全部列表
    private List<Assets> assetsAddAllList = new ArrayList<>();
    //资产账户集合
    private List<Assets> assetsAddList = new ArrayList<>();
    //负债账户集合
    private List<Assets> liabilityAddList = new ArrayList<>();
    //资产数据库查询
    private AssetsDao assetsAddDao = new AssetsDao();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_assets);

        //返回按钮
        addPreButton = findViewById(R.id.assets_add_btn_pre);
        addPreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddAssetsActivity.this.finish();
            }
        });

        //资产和负债列表
        boolean bol = SPUtil.contains(MyApplication.getContext(), Constant.ASSETS_ADD_TEXT);
        String jsonString = "";
        if (bol){
            jsonString = SPUtil.get(MyApplication.getContext(), Constant.ASSETS_ADD_TEXT, jsonString).toString();
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

        //资产账户的滑动控件
        RecyclerView assetsRecyclerView = findViewById(R.id.assets_add_assets_recyclerView);
        LinearLayoutManager assetsLayoutManager = new LinearLayoutManager(MyApplication.getContext());
        assetsRecyclerView.setLayoutManager(assetsLayoutManager);
        AssetsAddAdapter assetsAdapter = new AssetsAddAdapter(assetsAddList);
        assetsRecyclerView.setAdapter(assetsAdapter);

        //负债账户的滑动控件
        RecyclerView liabilityRecyclerView = findViewById(R.id.assets_add_liability_recyclerView);
        LinearLayoutManager liabilityLayoutManager = new LinearLayoutManager(MyApplication.getContext());
        liabilityRecyclerView.setLayoutManager(liabilityLayoutManager);
        AssetsAddAdapter liabilityAdapter = new AssetsAddAdapter(liabilityAddList);
        liabilityRecyclerView.setAdapter(liabilityAdapter);
    }
}
