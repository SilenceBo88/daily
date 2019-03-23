package com.zb.daily.UI;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hjq.toast.ToastUtils;
import com.zb.daily.Constant;
import com.zb.daily.R;
import com.zb.daily.dao.AssetsDao;
import com.zb.daily.model.Assets;

public class AssetsDetailActivity extends AppCompatActivity {

    //返回按钮
    private Button assetsDetailPreButton;
    private ImageView assetsDetailImage;
    private TextView assetsDetailName;
    private TextView assetsDetailRemark;
    private TextView assetsDetailBalance;
    private View assetsDetailLine;

    private Assets assets;
    //查询数据库
    private AssetsDao assetsDao = new AssetsDao();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assets_detail);

        Toolbar toolbar = findViewById(R.id.assets_detail_toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        assetsDetailPreButton = findViewById(R.id.assets_detail_btn_pre);
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

        assetsDetailPreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(AssetsDetailActivity.this, MainActivity.class);
                intent.putExtra("to", Constant.TO_ASSETS_FRAGMENT);
                startActivityForResult(intent,1);
            }
        });

        //todo 资产变动情况列表
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            Intent intent = new Intent();
            intent.setClass(AssetsDetailActivity.this, MainActivity.class);
            intent.putExtra("to", Constant.TO_ASSETS_FRAGMENT);
            startActivityForResult(intent,1);
        }
        return true;

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
                Intent intent = new Intent();
                intent.setClass(this, UpdateAssetsActivity.class);
                intent.putExtra("assets", JSONObject.toJSONString(assets));
                startActivityForResult(intent,1);
                break;
            case R.id.assets_detail_delete:
                showNormalDialog();
                break;
            default:
                break;
        }
        return true;
    }

    private void showNormalDialog(){
        /* @setIcon 设置对话框图标
         * @setTitle 设置对话框标题
         * @setMessage 设置对话框消息提示
         * setXXX方法返回Dialog对象，因此可以链式设置属性
         */
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(AssetsDetailActivity.this);
        normalDialog.setTitle("删除资产");
        normalDialog.setMessage("确定要删除该资产么，不会删除该资产下的现有收支记录");
        normalDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //...To-do
                        if(assetsDao.deleteAssets(assets.getId())){
                            ToastUtils.show("删除成功");
                            Intent intent = new Intent();
                            intent.setClass(AssetsDetailActivity.this, MainActivity.class);
                            intent.putExtra("to", Constant.TO_ASSETS_FRAGMENT);
                            startActivityForResult(intent,1);
                        }
                    }
                });
        normalDialog.setNegativeButton("取消",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        // 显示
        normalDialog.show();
    }
}
