package com.zb.daily.UI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import com.alibaba.fastjson.JSON;
import com.hjq.toast.ToastUtils;
import com.zb.daily.Constant;
import com.zb.daily.R;
import com.zb.daily.dao.AssetsDao;
import com.zb.daily.model.Assets;

import java.text.DecimalFormat;

/**
 * @auther: zb
 * @Date: 2019/3/9 18:01
 * @Description: 新建资产页面
 */
public class AddAssetsDetailActivity extends AppCompatActivity {

    //返回按钮
    private Button addDetailPreButton;
    //保存按钮
    private Button addDetailSaveButton;
    //图片
    private ImageView addDetailimageView;
    //名称
    private EditText addDetailName;
    //备注
    private EditText addDetailRemark;
    //余额
    private EditText addDetailBalance;
    //查询数据库
    private AssetsDao assetsDao = new AssetsDao();

    private Assets assets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_assets_detail);

        addDetailPreButton = findViewById(R.id.assets_add_Detail_btn_pre);
        addDetailSaveButton = findViewById(R.id.assets_add_Detail_btn_save);
        addDetailimageView = findViewById(R.id.assets_add_Detail_image);
        addDetailName = findViewById(R.id.assets_add_Detail_name);
        addDetailRemark = findViewById(R.id.assets_add_Detail_remark);
        addDetailBalance = findViewById(R.id.assets_add_Detail_balance);

        String jsonString = getIntent().getStringExtra("assets");
        assets = JSON.parseObject(jsonString, Assets.class);

        addDetailimageView.setImageResource(assets.getImageId());
        addDetailName.setText(assets.getName());

        //返回上一个活动
        addDetailPreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddAssetsDetailActivity.this.finish();
            }
        });

        //保存新的资产
        addDetailSaveButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String name = addDetailName.getText().toString();
                String remark = addDetailRemark.getText().toString();
                String balanceString = addDetailBalance.getText().toString();

                if (name.isEmpty()){
                    ToastUtils.show("资产名称不能为空");
                    return;
                }
                if (remark.isEmpty()){
                    remark = "";
                }
                double balance = 0.00;
                if (!balanceString.isEmpty()){
                    balance = Double.valueOf(balanceString);
                    DecimalFormat df = new DecimalFormat( "0.00");
                    balance = Double.valueOf(df.format(balance));
                }

                Assets temp = new Assets();
                temp.setImageId(assets.getImageId());
                temp.setType(assets.getType());
                temp.setName(name);
                temp.setRemark(remark);
                temp.setBalance(balance);

                if (assetsDao.saveAssets(temp)){
                    ToastUtils.show("添加成功");
                    Intent intent = new Intent();
                    intent.setClass(AddAssetsDetailActivity.this, MainActivity.class);
                    intent.putExtra("to", Constant.TO_ASSETS_FRAGMENT);
                    startActivityForResult(intent,1);
                }
            }
        });
    }
}
