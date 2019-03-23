package com.zb.daily.UI;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.alibaba.fastjson.JSON;
import com.hjq.toast.ToastUtils;
import com.zb.daily.R;
import com.zb.daily.model.Assets;

public class AssetsDetailActivity extends AppCompatActivity {

    private ImageView assetsDetailImage;
    private TextView assetsDetailName;
    private TextView assetsDetailRemark;
    private TextView assetsDetailBalance;
    private View assetsDetailLine;

    private Assets assets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assets_detail);

        Toolbar toolbar = findViewById(R.id.assets_detail_toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        assetsDetailImage = findViewById(R.id.assets_detail_image);
        assetsDetailName = findViewById(R.id.assets_detail_name);
        assetsDetailRemark = findViewById(R.id.assets_detail_remark);
        assetsDetailBalance = findViewById(R.id.assets_detail_balance);
        assetsDetailLine = findViewById(R.id.assets_detail_line);

        String jsonString = getIntent().getStringExtra("assets");
        assets = JSON.parseObject(jsonString, Assets.class);

        assetsDetailImage.setImageResource(assets.getImageId());
        assetsDetailName.setText(assets.getName());
        assetsDetailRemark.setText(assets.getRemark());
        assetsDetailBalance.setText(assets.getBalance().toString());
        if (assets.getRemark().isEmpty()){
            assetsDetailLine.setVisibility(View.GONE);
        }

        //todo 资产变动情况列表
    }



    //创建菜单
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.assets_detail_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.assets_detail_update:
                ToastUtils.show("你点击了修改！");
                break;
            case R.id.assets_detail_delete:
                ToastUtils.show("你点击了删除！");
                break;
            default:
                break;
        }
        return true;
    }
}
