package com.zb.daily.util;

import android.graphics.Color;
import android.graphics.Matrix;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;
import java.util.List;

public class BarChartManager {

    private BarChart mBarChart;
    private YAxis leftAxis;
    private YAxis rightAxis;
    private XAxis xAxis;

    //构造
    public BarChartManager(BarChart barChart) {
        this.mBarChart = barChart;
        leftAxis = mBarChart.getAxisLeft();
        rightAxis = mBarChart.getAxisRight();
        xAxis = mBarChart.getXAxis();
    }

    //初始化
    private void initLineChart() {
        //背景颜色
        mBarChart.setBackgroundColor(Color.WHITE);
        //网格
        mBarChart.setDrawGridBackground(false);
        //背景阴影
        mBarChart.setDrawBarShadow(false);
        mBarChart.setHighlightFullBarEnabled(false);
        //显示边界
        mBarChart.setDrawBorders(true);
        //设置动画效果
        mBarChart.animateY(1000, Easing.EasingOption.Linear);
        mBarChart.animateX(1000, Easing.EasingOption.Linear);
        //图例设置  (图表说明文字)
        Legend legend = mBarChart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);  //图例窗体的形状  目前是一条线 ，还有 圆圈 方块等形状
        legend.setTextSize(20f);  //图例文字的大小
        legend.setTextColor(Color.BLUE); //设置图例文字颜色
        //显示位置
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP); //图例说明文字在图表的上方
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER); //图例左居中
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);//图例的方向为水平
        legend.setDrawInside(false);//绘制在chart的外侧
        //XY轴的设置
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);//X轴设置显示位置在底部
        xAxis.setGranularity(1f);//设置最小间隔，防止当放大时，出现重复标签
        xAxis.setTextColor(Color.BLUE);//设置X轴字体颜色
        xAxis.setTextSize(10f);  //设置X轴字体大小
        xAxis.setDrawGridLines(false); //不绘制X轴网格，默认为绘制。
        //y轴设置
        leftAxis.setAxisMinimum(0f); //保证Y轴从0开始，不然会上移一点
        leftAxis.setAxisMaximum(5000);
        rightAxis.setAxisMinimum(0f);
        rightAxis.setAxisMaximum(5000);
        //将条目放大 可滑动
        mBarChart.invalidate();
        Matrix mMatrix = new Matrix();
        mMatrix.postScale(2f, 1f);  //X轴宽度放大2倍  竖直方向不变
        mBarChart.getViewPortHandler().refresh(mMatrix, mBarChart, false);
        mBarChart.animateY(800);
    }

    //展示柱状图单条
    public void showBarChart(List<Float> xAxisValues, List<Float> yAxisValues, String label, int color) {
        initLineChart(); //首先进项基本数据的初始化
        ArrayList entries = new ArrayList<>();//将数据源添加到图标
        for (int i = 0; i < xAxisValues.size(); i++) {
            entries.add(new BarEntry(xAxisValues.get(i), yAxisValues.get(i)));
        }
        BarDataSet barDataSet = new BarDataSet(entries, label);
        barDataSet.setColor(color);  //摄住柱状图颜色
        barDataSet.setValueTextSize(9f); //设置数值字体大小
        barDataSet.setFormLineWidth(1f);  //线条宽度

        barDataSet.setFormSize(15.f);  ///图例窗体的大小
        ArrayList dataSets = new ArrayList<>();
        dataSets.add(barDataSet);
        BarData data = new BarData(dataSets);
        xAxis.setLabelCount(entries.size() - 1, false);//设置X轴的刻度数
        mBarChart.setData(data);
    }

    //多条柱状图
    public void showBarChart(List<Float> xAxisValues, List<List<Float>> yAxisValues, List<String> labels, List<Integer> colours) {
        initLineChart(); //同理
        BarData data = new BarData();
        for (int i = 0; i < yAxisValues.size(); i++) {
            List<BarEntry> entries = new ArrayList<>();
            for (int j = 0; j < yAxisValues.get(i).size(); j++) {
                entries.add(new BarEntry(xAxisValues.get(j), yAxisValues.get(i).get(j)));
            }
            BarDataSet barDataSet = new BarDataSet(entries, labels.get(i));
            barDataSet.setColor(colours.get(i));
            barDataSet.setValueTextColor(colours.get(i));
            barDataSet.setValueTextSize(10f); //设置数值字体大小
            barDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
            data.addDataSet(barDataSet);
        }
        int amount = yAxisValues.size();
        float groupSpace = 0.12f; //柱状图组之间的间距
        float barSpace = (float) ((1 - 0.12) / amount / 10); // x4 DataSet
        float barWidth = (float) ((1 - 0.12) / amount / 10 * 9); // x4 DataSet
        xAxis.setLabelCount(xAxisValues.size() - 1, false);
        data.setBarWidth(barWidth);
        data.groupBars(0, groupSpace, barSpace);
        mBarChart.setData(data);
    }
}