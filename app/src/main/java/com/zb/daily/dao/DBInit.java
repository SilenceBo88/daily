package com.zb.daily.dao;

import com.alibaba.fastjson.JSONArray;
import com.zb.daily.Constant;
import com.zb.daily.MyApplication;
import com.zb.daily.R;
import com.zb.daily.model.Assets;
import com.zb.daily.model.Category;
import com.zb.daily.model.Record;
import com.zb.daily.util.SPUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @auther: zb
 * @Date: 2019/2/25 21:56
 * @Description: 初始化数据库中的数据，安装app时执行一次
 */
public class DBInit {

    //资产数据初始化
    public static void assetsInit(){

        AssetsDao assetsDao = new AssetsDao();

        //资产集合
        List<Assets> assetsList = new ArrayList<>();

        Assets assets1 = new Assets(R.drawable.assets_cash, "现金", 0.00, 1, "");
        Assets assets2 = new Assets(R.drawable.assets_wechat, "微信", 470.00, 1, "");
        Assets assets3 = new Assets(R.drawable.assets_alipay, "支付宝", 70.00, 1, "");
        Assets assets4 = new Assets(R.drawable.assets_save, "储蓄卡", 370.00, 1, "");
        Assets assets5 = new Assets(R.drawable.assets_recharge, "充值卡", 0.00, 1,"饭卡，公交卡");
        Assets assets6 = new Assets(R.drawable.assets_receipt, "应收账", 0.00, 1,"别人欠我的钱");

        Assets liability1 = new Assets(R.drawable.assets_credit, "信用卡", 0.00, 2, "");
        Assets liability2 = new Assets(R.drawable.assets_flower, "花呗", 150.00, 2, "");
        Assets liability3 = new Assets(R.drawable.assets_jd, "京东白条", 200.00, 2, "");
        Assets liability4 = new Assets(R.drawable.assets_pay, "应付账", 0.00, 2,"我欠别人的钱");

        assetsList.add(assets1);
        assetsList.add(assets2);
        assetsList.add(assets3);
        assetsList.add(assets4);
        assetsList.add(assets5);
        assetsList.add(assets6);
        assetsList.add(liability1);
        assetsList.add(liability2);
        assetsList.add(liability3);
        assetsList.add(liability4);

        //一份保存到数据库，以便正常crud
        assetsDao.saveAssetsList(assetsList);

        //另一份以json格式保存到SharedPreference中，在添加资产页面初始化list时使用
        String jsonString = JSONArray.toJSONString(assetsList);
        SPUtil.put(MyApplication.getContext(), Constant.TEXT_ASSETS_ADD_LIST, jsonString);
    }

    //资产数据初始化
    public static void categoryInit(){
        CategoryDao categoryDao = new CategoryDao();

        //分类集合
        List<Category> categoryList = new ArrayList<>();

        Category outCategory1 = new Category(R.drawable.category_food, "餐饮", 1);
        Category outCategory2 = new Category(R.drawable.category_dailyuse, "日用", 1);
        Category outCategory3 = new Category(R.drawable.category_clothes, "衣服", 1);
        Category outCategory4 = new Category(R.drawable.category_medical, "医疗", 1);
        Category outCategory5 = new Category(R.drawable.category_snack, "零食", 1);
        Category outCategory6 = new Category(R.drawable.category_gift, "礼物", 1);
        Category outCategory7 = new Category(R.drawable.category_traffic, "交通", 1);
        Category outCategory8 = new Category(R.drawable.category_electronic, "电子产品", 1);

        Category inCategory1 = new Category(R.drawable.category_salary, "薪资", 2);
        Category inCategory2 = new Category(R.drawable.category_parttime, "兼职", 2);
        Category inCategory3 = new Category(R.drawable.category_investment, "投资", 2);
        Category inCategory4 = new Category(R.drawable.category_alimony, "生活费", 2);

        categoryList.add(outCategory1);
        categoryList.add(outCategory2);
        categoryList.add(outCategory3);
        categoryList.add(outCategory4);
        categoryList.add(outCategory5);
        categoryList.add(outCategory6);
        categoryList.add(outCategory7);
        categoryList.add(outCategory8);
        categoryList.add(inCategory1);
        categoryList.add(inCategory2);
        categoryList.add(inCategory3);
        categoryList.add(inCategory4);

        //保存到数据库，以便正常crud
        categoryDao.saveCategoryList(categoryList);


        //图标集合
        List<Integer> categoryImageList = new ArrayList<>();

        int categoryImage1 = R.drawable.category_food;
        int categoryImage2 = R.drawable.category_dailyuse;
        int categoryImage3 = R.drawable.category_clothes;
        int categoryImage4 = R.drawable.category_medical;
        int categoryImage5 = R.drawable.category_snack;
        int categoryImage6 = R.drawable.category_gift;
        int categoryImage7 = R.drawable.category_traffic;
        int categoryImage8 = R.drawable.category_electronic;
        int categoryImage9 = R.drawable.category_default;
        int categoryImage10 = R.drawable.category_salary;
        int categoryImage11 = R.drawable.category_parttime;
        int categoryImage12 = R.drawable.category_investment;
        int categoryImage13 = R.drawable.category_alimony;
        int categoryImage14 = R.drawable.category_default2;

        categoryImageList.add(categoryImage1);
        categoryImageList.add(categoryImage2);
        categoryImageList.add(categoryImage3);
        categoryImageList.add(categoryImage4);
        categoryImageList.add(categoryImage5);
        categoryImageList.add(categoryImage6);
        categoryImageList.add(categoryImage7);
        categoryImageList.add(categoryImage8);
        categoryImageList.add(categoryImage9);
        categoryImageList.add(categoryImage10);
        categoryImageList.add(categoryImage11);
        categoryImageList.add(categoryImage12);
        categoryImageList.add(categoryImage13);
        categoryImageList.add(categoryImage14);

        //以json格式保存到SharedPreference中，在添加和修改分类页面时初始化图标列表时使用
        String jsonString = JSONArray.toJSONString(categoryImageList);
        SPUtil.put(MyApplication.getContext(), Constant.TEXT_CATEGORY_IMAGE_LIST, jsonString);
    }

    //记录数据初始化
    public static void recordInit(){
        RecordDao recordDao = new RecordDao();

        //记录集合
        List<Record> recordList = new ArrayList<>();

        Record record13 = new Record(150.0, "2019-05-05", "买衣服", 1,
                3, R.drawable.category_clothes, "衣服", 8, "花呗");
        Record record14 = new Record(200.0, "2019-05-05", "键盘", 1,
                8, R.drawable.category_electronic, "电子产品", 9, "京东白条");

        Record record1 = new Record(10.0, "2019-05-04", "晚餐", 1,
                1, R.drawable.category_food, "餐饮", 2, "微信");
        Record record2 = new Record(10.0, "2019-05-04", "午餐", 1,
                1, R.drawable.category_food, "餐饮", 2, "微信");
        Record record3 = new Record(10.0, "2019-05-04", "早餐", 1,
                1, R.drawable.category_food, "餐饮", 2, "微信");

        Record record4 = new Record(10.0, "2019-05-03", "晚餐", 1,
                1, R.drawable.category_food, "餐饮", 3, "支付宝");
        Record record5 = new Record(10.0, "2019-05-03", "午餐", 1,
                1, R.drawable.category_food, "餐饮", 3, "支付宝");
        Record record6 = new Record(10.0, "2019-05-03", "早餐", 1,
                1, R.drawable.category_food, "餐饮", 3, "支付宝");

        Record record7 = new Record(10.0, "2019-05-02", "晚餐", 1,
                1, R.drawable.category_food, "餐饮", 4, "储蓄卡");
        Record record8 = new Record(10.0, "2019-05-02", "午餐", 1,
                1, R.drawable.category_food, "餐饮", 4, "储蓄卡");
        Record record9 = new Record(10.0, "2019-05-02", "早餐", 1,
                1, R.drawable.category_food, "餐饮", 4, "储蓄卡");

        Record record10 = new Record(400.0, "2019-05-01", "工资", 2,
                9, R.drawable.category_salary, "薪资", 4, "储蓄卡");
        Record record11 = new Record(100.0, "2019-05-01", "兼职", 2,
                10, R.drawable.category_parttime, "兼职", 3, "支付宝");
        Record record12 = new Record(500.0, "2019-05-01", "工资", 2,
                9, R.drawable.category_salary, "薪资", 2, "微信");

        recordList.add(record1);
        recordList.add(record2);
        recordList.add(record3);
        recordList.add(record4);
        recordList.add(record5);
        recordList.add(record6);
        recordList.add(record7);
        recordList.add(record8);
        recordList.add(record9);
        recordList.add(record10);
        recordList.add(record11);
        recordList.add(record12);
        recordList.add(record13);
        recordList.add(record14);

        //保存到数据库，以便正常crud
        recordDao.saveRecordList(recordList);
    }
}
