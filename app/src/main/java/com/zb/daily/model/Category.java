package com.zb.daily.model;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

/**
 * @auther: zb
 * @Date: 2019/3/25 19:37
 * @Description: 分类实体类
 */
public class Category extends DataSupport {

    @Column(unique = true)
    private Integer id;

    private Integer imageId;//图片id

    private String name;//名称

    private Integer type;//类型：1是支出，2是收入

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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Category() {
    }

    public Category(Integer imageId, String name, Integer type) {
        this.imageId = imageId;
        this.name = name;
        this.type = type;
    }
}
