package com.zb.daily.UI;

import android.content.Context;
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
import com.zb.daily.BaseActivity;
import com.zb.daily.Constant;
import com.zb.daily.R;
import com.zb.daily.dao.AssetsDao;
import com.zb.daily.model.Assets;

/**
 * @auther: zb
 * @Date: 2019/3/23 14:01
 * @Description: 资产详情页面
 */
public class AssetsDetailActivity extends BaseActivity {

    //返回按钮
    private Button assetsDetailPreButton;
    //图片
    private ImageView assetsDetailImage;
    //名称
    private TextView assetsDetailName;
    //备注
    private TextView assetsDetailRemark;
    //余额
    private TextView assetsDetailBalance;
    //分割线
    private View assetsDetailLine;
    //查询数据库
    private AssetsDao assetsDao = new AssetsDao();
    private Assets assets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assets_detail);

        //toolbar替换ActionBar
        Toolbar toolbar = findViewById(R.id.activity_assets_detail_toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        assetsDetailPreButton = findViewById(R.id.activity_assets_detail_btn_pre);
        assetsDetailImage = findViewById(R.id.activity_assets_detail_image);
        assetsDetailName = findViewById(R.id.activity_assets_detail_name);
        assetsDetailRemark = findViewById(R.id.activity_assets_detail_remark);
        assetsDetailBalance = findViewById(R.id.activity_assets_detail_balance);
        assetsDetailLine = findViewById(R.id.activity_assets_detail_line);

        //获取活动传递的数据
        String jsonString = getIntent().getStringExtra("assets");
        assets = JSON.parseObject(jsonString, Assets.class);
        assetsDetailImage.setImageResource(assets.getImageId());
        assetsDetailName.setText(assets.getName());
        assetsDetailRemark.setText(assets.getRemark());
        assetsDetailBalance.setText(assets.getBalance().toString());
        if (assets.getRemark().isEmpty()){
            assetsDetailLine.setVisibility(View.GONE);
        }

        //返回上一个活动
        assetsDetailPreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.actionStart(AssetsDetailActivity.this, Constant.TO_ASSETS_FRAGMENT);
            }
        });

        //todo 资产变动情况列表
    }

    //重写系统返回按钮
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            MainActivity.actionStart(AssetsDetailActivity.this, Constant.TO_ASSETS_FRAGMENT);
        }
        return true;
    }

    //创建菜单
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.activity_assets_detail_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.assets_detail_update:
                //去修改资产页面
                AssetsUpdateActivity.actionStart(AssetsDetailActivity.this, assets);
                break;
            case R.id.assets_detail_delete:
                //弹出删除资产确定框
                showDeleteAssetsDialog();
                break;
            default:
                break;
        }
        return true;
    }

    //删除资产确定框
    private void showDeleteAssetsDialog(){
        final AlertDialog.Builder normalDialog = new AlertDialog.Builder(AssetsDetailActivity.this);
        normalDialog.setTitle("删除资产");
        normalDialog.setMessage("确定要删除该资产么，不会删除该资产下的现有收支记录");
        normalDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(assetsDao.deleteAssets(assets.getId())){
                            //删除成功，返回资产页面
                            MainActivity.actionStart(AssetsDetailActivity.this, Constant.TO_ASSETS_FRAGMENT);
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

    //启动本活动
    public static void actionStart(Context context, Assets assets){
        Intent intent = new Intent();
        intent.setClass(context, AssetsDetailActivity.class);
        intent.putExtra("assets", JSONObject.toJSONString(assets));
        context.startActivity(intent);
        /*((BaseActivity)context).startActivityForResult(intent,1);*/
    }
}
