package com.zb.daily.UI.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.zb.daily.R;
import com.zb.daily.UI.MainActivity;
import com.zb.daily.util.SPUtil;

/**
 * @auther: zb
 * @Date: 2019/4/29 22:31
 * @Description: 图表页面
 */
public class SettingFragment extends Fragment {

    public FragmentActivity activity;

    //滑动菜单
    private DrawerLayout drawerLayout;
    //菜单按钮
    private Button menuButton;
    private LinearLayout linearLayout;
    private CheckBox checkBox;
    private TextView textView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        activity = getActivity();
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //用Toolbar替换ActionBar
        setHasOptionsMenu(true);
        AppCompatActivity appCompatActivity= (AppCompatActivity) getActivity();
        Toolbar toolbar=  appCompatActivity.findViewById(R.id.fragment_setting_toolbar);
        toolbar.setTitle("");
        appCompatActivity.setSupportActionBar(toolbar);

        //菜单按钮打开滑动窗口
        menuButton = activity.findViewById(R.id.fragment_setting_btn_menu);
        drawerLayout = activity.findViewById(R.id.drawer_layout);
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);//打开滑动菜单
            }
        });

        String budget = (String) SPUtil.get(activity, "month_budget", "0");
        textView = activity.findViewById(R.id.fragment_setting_budge_text);
        textView.setText(budget);
        checkBox = activity.findViewById(R.id.fragment_setting_checkBox);
        linearLayout = activity.findViewById(R.id.fragment_setting_budget);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInputDialog();
            }
        });

        boolean checked = (boolean) SPUtil.get(activity, "record_lock", false);
        checkBox.setChecked(checked);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    SPUtil.put(activity, "record_lock", true);
                }else{
                    SPUtil.put(activity, "record_lock", false);
                }
            }
        });
    }

    private void showInputDialog(){
        final EditText editText = new EditText(activity);
        editText.setInputType(InputType.TYPE_CLASS_NUMBER);
        AlertDialog.Builder inputDialog =
                new AlertDialog.Builder(activity);
        inputDialog.setTitle("输入月预算").setView(editText);
        inputDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SPUtil.put(activity, "month_budget", editText.getText().toString());
                        textView.setText(editText.getText().toString());
                    }
                }).show();
    }
}
