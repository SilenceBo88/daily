package com.zb.daily.UI;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.zb.daily.BaseActivity;
import com.zb.daily.Constant;
import com.zb.daily.MyApplication;
import com.zb.daily.R;
import com.zb.daily.UI.fragment.IndexFragment;
import com.zb.daily.UI.fragment.IndexRecordInFragment;
import com.zb.daily.UI.fragment.IndexRecordOutFragment;
import com.zb.daily.UI.fragment.IndexTransferFragment;

/**
 * @auther: zb
 * @Date: 2019/4/2 16:31
 * @Description: 添加记录活动，即记账活动
 */
public class RecordAddActivity extends BaseActivity {

    private Button preButton;
    private Button outButton;
    private Button inButton;
    private Button transferButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_add);

        preButton = findViewById(R.id.activity_record_btn_pre);
        outButton = findViewById(R.id.activity_record_btn_out);
        inButton = findViewById(R.id.activity_record_btn_in);
        transferButton = findViewById(R.id.activity_record_btn_transfer);

        //默认加载支出记录页面
        replaceFragment(new IndexRecordOutFragment());

        preButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.actionStart(getApplicationContext(), Constant.TO_INDEX_FRAGMENT);
            }
        });

        outButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                outButton.setBackgroundResource(R.drawable.button_pressed);
                inButton.setBackgroundResource(R.drawable.button_normal);
                transferButton.setBackgroundResource(R.drawable.button_normal);
                replaceFragment(new IndexRecordOutFragment());
            }
        });

        inButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                outButton.setBackgroundResource(R.drawable.button_normal);
                inButton.setBackgroundResource(R.drawable.button_pressed);
                transferButton.setBackgroundResource(R.drawable.button_normal);
                replaceFragment(new IndexRecordInFragment());
            }
        });

        transferButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                outButton.setBackgroundResource(R.drawable.button_normal);
                inButton.setBackgroundResource(R.drawable.button_normal);
                transferButton.setBackgroundResource(R.drawable.button_pressed);
                replaceFragment(new IndexTransferFragment());
            }
        });
    }

    //启动本活动
    public static void actionStart(Context context){
        Intent intent = new Intent();
        intent.setClass(context, RecordAddActivity.class);
        /*context.startActivity(intent);*/
        ((BaseActivity)context).startActivityForResult(intent,1001);
    }

    //动态切换fragment
    public void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.activity_record_content_frame, fragment);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        MainActivity.actionStart(getApplicationContext(), Constant.TO_INDEX_FRAGMENT);
    }
}
