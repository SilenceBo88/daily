package com.zb.daily.model;

import com.zb.daily.dao.AssetsDao;
import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

import java.util.Date;

/**
 * @auther: zb
 * @Date: 2019/3/27 19:59
 * @Description: 记录实体类
 */
public class Record extends DataSupport {
    @Column(unique = true)
    private Integer id;

    private Double money;//钱数

    private String date;//日期

    private String remark;//备注

    private Integer type;//类型：1是支出记录，2是收入记录

    private Integer category_id;//分类id

    private Integer  category_imageId; //分类图片

    private String  category_name; //分类名

    private Integer assets_id; //资产id

    private String  assets_name;//资产名

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getCategoryId() {
        return category_id;
    }

    public void setCategoryId(Integer category_id) {
        this.category_id = category_id;
    }

    public Integer getAssetsId() {
        return assets_id;
    }

    public void setAssetsId(Integer assets_id) {
        this.assets_id = assets_id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getCategoryImageId() {
        return category_imageId;
    }

    public void setCategoryImageId(Integer category_imageId) {
        this.category_imageId = category_imageId;
    }

    public String getCategoryName() {
        return category_name;
    }

    public void setCategoryName(String category_name) {
        this.category_name = category_name;
    }

    public String getAssetsName() {
        return assets_name;
    }

    public void setAssetsName(String assets_name) {
        this.assets_name = assets_name;
    }

    public Assets getAssets(){
        return DataSupport.find(Assets.class, assets_id);
    }

    public Category getCategory(){
        return DataSupport.find(Category.class, category_id);
    }

    public Record() {
    }

    public Record(Double money, String date, String remark, Integer type,
                  Integer category_id, Integer category_imageId, String category_name,
                  Integer assets_id, String assets_name) {
        this.money = money;
        this.date = date;
        this.remark = remark;
        this.type = type;
        this.category_id = category_id;
        this.category_imageId = category_imageId;
        this.category_name = category_name;
        this.assets_id = assets_id;
        this.assets_name = assets_name;
    }
}
