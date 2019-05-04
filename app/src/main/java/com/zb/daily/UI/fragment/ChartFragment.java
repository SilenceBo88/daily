package com.zb.daily.UI.fragment;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.NumberPicker;
import android.widget.TextView;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.*;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.zb.daily.R;
import com.zb.daily.dao.RecordDao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

/**
 * @auther: zb
 * @Date: 2019/2/22 18:01
 * @Description: 图表页面
 */
public class ChartFragment extends Fragment {

    public FragmentActivity activity;

    //悬浮按钮
    private FloatingActionButton fab;
    //滑动菜单
    private DrawerLayout drawerLayout;
    //菜单按钮
    private Button menuButton;

    /*private BarChart barChart;*/
    private PieChart mPieChart;
    private PieChart mPieChart2;
    private String dateMonth = "";
    private TextView dateTextView;

    private RecordDao recordDao = new RecordDao();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        activity = getActivity();
        View view = inflater.inflate(R.layout.fragment_chart, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //用Toolbar替换ActionBar
        setHasOptionsMenu(true);
        AppCompatActivity appCompatActivity= (AppCompatActivity) getActivity();
        Toolbar toolbar=  appCompatActivity.findViewById(R.id.fragment_chart_toolbar);
        toolbar.setTitle("");
        appCompatActivity.setSupportActionBar(toolbar);

        //菜单按钮打开滑动窗口
        menuButton = activity.findViewById(R.id.fragment_chart_btn_menu);
        drawerLayout = activity.findViewById(R.id.drawer_layout);
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);//打开滑动菜单
            }
        });

        dateMonth = new SimpleDateFormat("yyyy-MM").format(new Date());
        dateTextView = activity.findViewById(R.id.fragment_chart_date);
        dateTextView.setText(dateMonth);

        mPieChart = activity.findViewById(R.id.fragment_chart_pie);
        mPieChart2 = activity.findViewById(R.id.fragment_chart_pie2);
        pieChartInit(mPieChart, 1, dateMonth);
        pieChartInit(mPieChart2, 2, dateMonth);

        dateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog();
            }
        });
    }

    //初始化饼图
    private void pieChartInit(PieChart pieChart, int type, String month){
        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5, 10, 5, 5);
        pieChart.setDragDecelerationFrictionCoef(0.95f);
        //设置中间文件
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.setTransparentCircleColor(Color.WHITE);
        pieChart.setTransparentCircleAlpha(110);
        pieChart.setHoleRadius(58f);
        pieChart.setTransparentCircleRadius(61f);
        pieChart.setDrawCenterText(true);
        pieChart.setRotationAngle(0);
        // 触摸旋转
        pieChart.setRotationEnabled(true);
        pieChart.setHighlightPerTapEnabled(true);

        //变化监听
        /*mPieChart.setOnChartValueSelectedListener(this);*/
        double summary = recordDao.getMonthSummary(month, type);
        Map<String, Double> map = recordDao.getMonthSummaryByCategory(month, type);
        if (type == 1){
            pieChart.setCenterText(generateCenterSpannableText("总支出：" + summary));
        }else {
            pieChart.setCenterText(generateCenterSpannableText("总收入：" + summary));
        }
        //模拟数据
        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();
        for (String key : map.keySet()){
            entries.add(new PieEntry((float) (map.get(key) / summary), key));
        }
        //设置数据
        setData(pieChart, entries);
        pieChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);
        Legend l = pieChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);
        // 输入标签样式
        pieChart.setEntryLabelColor(Color.WHITE);
        pieChart.setEntryLabelTextSize(12f);
    }

    //设置中间文字
    private SpannableString generateCenterSpannableText(String text) {
        SpannableString s = new SpannableString(text);
        return s;
    }
    //设置数据
    private void setData(PieChart pieChart, ArrayList<PieEntry> entries) {
        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);

        //数据和颜色
        ArrayList<Integer> colors = new ArrayList<Integer>();
        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);
        colors.add(ColorTemplate.getHoloBlue());
        dataSet.setColors(colors);
        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);
        pieChart.setData(data);
        pieChart.highlightValues(null);
        //刷新
        pieChart.invalidate();
    }

    //日期弹出框
    private void showDateDialog() {
        View view = LayoutInflater.from(activity).inflate(R.layout.fragment_chart_dialog_date, null);
        final DatePicker dateTime = view.findViewById(R.id.fragment_chart_dialog_date_pick);
        NumberPicker view0 = (NumberPicker) ((ViewGroup) ((ViewGroup) dateTime.getChildAt(0)).getChildAt(0)).getChildAt(0); //获取最前一位的宽度
        NumberPicker view1 = (NumberPicker) ((ViewGroup) ((ViewGroup) dateTime.getChildAt(0)).getChildAt(0)).getChildAt(1); //获取中间一位的宽度
        NumberPicker view2 = (NumberPicker) ((ViewGroup) ((ViewGroup) dateTime.getChildAt(0)).getChildAt(0)).getChildAt(2);//获取最后一位的宽度
        //年的最大值为2100
        //月的最大值为11
        //日的最大值为28,29,30,31
        int value0 = view0.getMaxValue();//获取第一个View的最大值
        int value1 = view1.getMaxValue();//获取第二个View的最大值
        int value2 = view2.getMaxValue();//获取第三个View的最大值
        if (value0 > 25 && value0 < 252) {
            view0.setVisibility(View.GONE);
        } else if (value1 > 25 && value1 < 252) {
            view1.setVisibility(View.GONE);
        } else if (value2 > 25 && value2 < 252) {
            view2.setVisibility(View.GONE);
        }

        dateTime.updateDate(dateTime.getYear(), dateTime.getMonth(), dateTime.getDayOfMonth());

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setView(view);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int month = dateTime.getMonth() + 1;
                String monthStr = "";
                if (month < 10){
                    monthStr = "0" + month;
                }else {
                    monthStr = "" + month;
                }

                int day = dateTime.getDayOfMonth();
                String dayStr = "";
                if (day < 10){
                    dayStr = "0" + day;
                }else {
                    dayStr = "" + day;
                }

                String time = "" + dateTime.getYear() + "-" + monthStr;
                dateMonth = time;
                dateTextView.setText(time);
                pieChartInit(mPieChart, 1, dateMonth);
                pieChartInit(mPieChart2, 2, dateMonth);
            }
        });
        builder.setNegativeButton("取消", null);
        AlertDialog dialog = builder.create();
        dialog.show();

        //自动弹出键盘问题解决
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }
}
