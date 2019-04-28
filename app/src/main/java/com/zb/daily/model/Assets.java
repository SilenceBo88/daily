package com.zb.daily.model;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

/**
 * @auther: zb
 * @Date: 2019/2/23 22:43
 * @Description: 资产实体类
 */
public class Assets extends DataSupport {

    @Column(unique = true)
    private Integer id;

    private Integer imageId;//图片id

    private String name;//名称

    private Double balance;//余额

    private Integer type;//类型：1是资产，2是负债

    private String remark;//备注

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getImageId() {
        return imageId;
    }

    public void setImageId(Integer imageId) {
        this.imageId = imageId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Assets() {
    }

    public Assets(Integer imageId, String name, Double balance, Integer type, String remark) {
        this.imageId = imageId;
        this.name = name;
        this.balance = balance;
        this.type = type;
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "Assets{" +
                "id=" + id +
                ", imageId=" + imageId +
                ", name='" + name + '\'' +
                ", balance=" + balance +
                ", type=" + type +
                ", remark='" + remark + '\'' +
                '}';
    }
}
