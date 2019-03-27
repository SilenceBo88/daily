package com.zb.daily.model;

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

    private Double money;

    private Date date;

    private String remark;

    private Integer category_id;

    private Integer assets_id;

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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getCategory_id() {
        return category_id;
    }

    public void setCategory_id(Integer category_id) {
        this.category_id = category_id;
    }

    public Integer getAssets_id() {
        return assets_id;
    }

    public void setAssets_id(Integer assets_id) {
        this.assets_id = assets_id;
    }

    public Record(Double money, Date date, String remark, Integer category_id, Integer assets_id) {
        this.money = money;
        this.date = date;
        this.remark = remark;
        this.category_id = category_id;
        this.assets_id = assets_id;
    }
}
