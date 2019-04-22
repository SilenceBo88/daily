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
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zb.daily.BaseActivity;
import com.zb.daily.Constant;
import com.zb.daily.R;
import com.zb.daily.UI.fragment.IndexRecordInFragment;
import com.zb.daily.UI.fragment.IndexRecordOutFragment;
import com.zb.daily.UI.fragment.IndexRecordUpdateInFragment;
import com.zb.daily.UI.fragment.IndexRecordUpdateOutFragment;
import com.zb.daily.model.Category;
import com.zb.daily.model.Record;

/**
 * @auther: zb
 * @Date: 2019/4/19 21:31
 * @Description: 修改记录活动
 */
public class RecordUpdateActivity extends AppCompatActivity {

    private Button preButton;
    private Button outButton;
    private Button inButton;
    Record record;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_update);

        String jsonStr = getIntent().getStringExtra("record");
        record = JSON.parseObject(jsonStr, Record.class);

        preButton = findViewById(R.id.activity_record_update_btn_pre);
        outButton = findViewById(R.id.activity_record_btn_update_out);
        inButton = findViewById(R.id.activity_record_btn_update_in);

        //默认加载支出记录页面
        if (record.getType() == 1){
            outButton.setBackgroundResource(R.drawable.button_pressed);
            inButton.setBackgroundResource(R.drawable.button_normal);
            replaceFragment(new IndexRecordUpdateOutFragment(record));
        }else {
            outButton.setBackgroundResource(R.drawable.button_normal);
            inButton.setBackgroundResource(R.drawable.button_pressed);
            replaceFragment(new IndexRecordUpdateInFragment(record));
        }

        preButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.actionStart(getApplicationContext(), Constant.TO_INDEX_FRAGMENT);
                finish();
            }
        });

        outButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                outButton.setBackgroundResource(R.drawable.button_pressed);
                inButton.setBackgroundResource(R.drawable.button_normal);
                replaceFragment(new IndexRecordUpdateOutFragment(record));
            }
        });

        inButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                outButton.setBackgroundResource(R.drawable.button_normal);
                inButton.setBackgroundResource(R.drawable.button_pressed);
                replaceFragment(new IndexRecordUpdateInFragment(record));
            }
        });
    }

    //启动本活动
    public static void actionStart(Context context, Record record){
        Intent intent = new Intent();
        intent.setClass(context, RecordUpdateActivity.class);
        intent.putExtra("record", JSONObject.toJSONString(record));
        context.startActivity(intent);
        /*((BaseActivity)context).startActivityForResult(intent,1002);*/
    }

    //动态切换fragment
    public void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.activity_record_content_update_frame, fragment);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        MainActivity.actionStart(getApplicationContext(), Constant.TO_INDEX_FRAGMENT);
    }
}
