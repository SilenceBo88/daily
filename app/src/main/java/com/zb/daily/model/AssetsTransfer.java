package com.zb.daily.model;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

/**
 * @auther: zb
 * @Date: 2019/4/22 22:23
 * @Description: 资产转账实体类
 */
public class AssetsTransfer extends DataSupport{

    @Column(unique = true)
    private Integer id;

    private Integer fromId;//转出账户id

    private Integer toId;//转入账户id

    private String date;//转账日期

    private String remark;//备注

    private Double money;//金额

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFromId() {
        return fromId;
    }

    public void setFromId(Integer fromId) {
        this.fromId = fromId;
    }

    public Integer getToId() {
        return toId;
    }

    public void setToId(Integer toId) {
        this.toId = toId;
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

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public Assets getFromAssets() {
        return DataSupport.find(Assets.class, fromId);
    }

    public Assets getToAssets() {
        return DataSupport.find(Assets.class, toId);
    }

    public AssetsTransfer() {
    }

    public AssetsTransfer(Integer fromId, Integer toId, String date, String remark, Double balance) {
        this.fromId = fromId;
        this.toId = toId;
        this.date = date;
        this.remark = remark;
        this.money = balance;
    }

    @Override
    public String toString() {
        return "AssetsTransfer{" +
                "id=" + id +
                ", fromId=" + fromId +
                ", toId=" + toId +
                ", date='" + date + '\'' +
                ", remark='" + remark + '\'' +
                ", money=" + money +
                '}';
    }
}
