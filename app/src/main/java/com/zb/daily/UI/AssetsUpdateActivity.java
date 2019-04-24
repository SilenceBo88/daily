package com.zb.daily.UI;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hjq.toast.ToastUtils;
import com.zb.daily.BaseActivity;
import com.zb.daily.R;
import com.zb.daily.dao.AssetsDao;
import com.zb.daily.dao.AssetsUpdateDao;
import com.zb.daily.model.Assets;
import com.zb.daily.model.AssetsUpdate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @auther: zb
 * @Date: 2019/3/23 15:27
 * @Description: 修改资产页面
 */
public class AssetsUpdateActivity extends BaseActivity {

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
    private AssetsUpdateDao assetsUpdateDao = new AssetsUpdateDao();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assets_update);

        updatePreButton = findViewById(R.id.activity_assets_update_btn_pre);
        updateSaveButton = findViewById(R.id.activity_assets_update_btn_save);
        updateImageView = findViewById(R.id.activity_assets_update_image);
        updateName = findViewById(R.id.activity_assets_update_name);
        updateRemark = findViewById(R.id.activity_assets_update_remark);
        updateBalance = findViewById(R.id.activity_assets_update_balance);

        //获得活动传递的数据
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
                finish();
            }
        });

        //保存新的资产
        updateSaveButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String name = updateName.getText().toString().trim();
                String remark = updateRemark.getText().toString().trim();
                String balanceString = updateBalance.getText().toString().trim();

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
                    AssetsUpdate assetsUpdate = new AssetsUpdate();
                    assetsUpdate.setDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
                    assetsUpdate.setFromMoney(assets.getBalance());
                    assetsUpdate.setToMoney(balance);
                    assetsUpdate.setAssetsId(assets.getId());
                    if (assetsUpdateDao.saveAssets(assetsUpdate)){
                        Log.d("assetsUpdate:", assetsUpdate.toString());
                        Log.d("AssetsUpdateActivity:", "资产修改添加成功");
                    }

                    //修改成功，返回资产详情页面
                    ToastUtils.show("修改成功");
                    Intent intent = new Intent();
                    intent.putExtra("assets_update_return", JSONObject.toJSONString(temp));
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
    }

    //启动本活动
    public static void actionStart(Context context, Assets assets){
        Intent intent = new Intent();
        intent.setClass(context, AssetsUpdateActivity.class);
        intent.putExtra("assets", JSONObject.toJSONString(assets));
        /*context.startActivity(intent);*/
        ((BaseActivity)context).startActivityForResult(intent, 1);
    }
}
