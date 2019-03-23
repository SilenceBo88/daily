package com.zb.daily.UI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hjq.toast.ToastUtils;
import com.zb.daily.Constant;
import com.zb.daily.R;
import com.zb.daily.dao.AssetsDao;
import com.zb.daily.model.Assets;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class UpdateAssetsActivity extends AppCompatActivity {

    //返回按钮
    private Button updatePreButton;
    //保存按钮
    private Button updateSaveButton;
    //图片
    private ImageView updateImageView;
    //名称
    private EditText updateName;
    //备注
    private EditText updateRemark;
    //余额
    private EditText updateBalance;
    //查询数据库
    private AssetsDao assetsDao = new AssetsDao();

    private Assets assets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_assets);

        updatePreButton = findViewById(R.id.assets_update_btn_pre);
        updateSaveButton = findViewById(R.id.assets_update_btn_save);
        updateImageView = findViewById(R.id.assets_update_image);
        updateName = findViewById(R.id.assets_update_name);
        updateRemark = findViewById(R.id.assets_update_remark);
        updateBalance = findViewById(R.id.assets_update_balance);

        String jsonString = getIntent().getStringExtra("assets");
        assets = JSON.parseObject(jsonString, Assets.class);

        updateImageView.setImageResource(assets.getImageId());
        updateName.setText(assets.getName());
        updateRemark.setText(assets.getRemark());
        updateBalance.setText(assets.getBalance().toString());

        //返回上一个活动
        updatePreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateAssetsActivity.this.finish();
            }
        });

        //保存新的资产
        updateSaveButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String name = updateName.getText().toString();
                String remark = updateRemark.getText().toString();
                String balanceString = updateBalance.getText().toString();

                if (name.isEmpty()){
                    ToastUtils.show("资产名称不能为空");
                    return;
                }
                if (remark.isEmpty()){
                    remark = "";
                }
                double balance = 0.00;
                if (!balanceString.isEmpty()){
                    BigDecimal bal = new BigDecimal(balanceString);
                    BigDecimal bal2= bal.setScale(2, RoundingMode.DOWN);
                    balance = bal2.doubleValue();
                }

                Assets temp = new Assets();
                temp.setId(assets.getId());
                temp.setImageId(assets.getImageId());
                temp.setType(assets.getType());
                temp.setName(name);
                temp.setRemark(remark);
                temp.setBalance(balance);

                if (assetsDao.updateAssets(temp)){
                    ToastUtils.show("修改成功");
                    Intent intent = new Intent();
                    intent.setClass(UpdateAssetsActivity.this, AssetsDetailActivity.class);
                    intent.putExtra("assets", JSONObject.toJSONString(temp));
                    startActivityForResult(intent,1);
                }
            }
        });
    }
}
