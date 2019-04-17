package com.zb.daily.dao;

import android.content.ContentValues;
import com.zb.daily.model.Category;
import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * @auther: zb
 * @Date: 2019/3/25 20:28
 * @Description: 数据库分类表的crud操作
 */
public class CategoryDao {

    //保存分类
    public boolean saveCategory(Category temp) {
        return temp.save();
    }

    //保存分类集合
    public void saveCategoryList(List<Category> categoryList) {
        DataSupport.saveAll(categoryList);
    }

    //按照分类类型查询分类集合
    public List<Category> findCategoryListByType(Integer type){
        List<Category> categoryList = DataSupport.where("type = ?", type.toString()).find(Category.class);
        return categoryList;
    }

    //替换旧的分类列表
    public void replaceOldList(List<Category> categoryList) {
        if (categoryList.size() == 0){
            return;
        }
        List<Category> tempCategory = findCategoryListByType(categoryList.get(0).getType());

        for (int i=0; i<categoryList.size(); i++){
            Category temp = new Category();
            temp.setImageId(categoryList.get(i).getImageId());
            temp.setName(categoryList.get(i).getName());
            temp.setType(categoryList.get(i).getType());

            temp.update(tempCategory.get(i).getId());
        }
    }

    //修改分类
    public boolean updateCategory(Category temp) {
        ContentValues values = new ContentValues();
        values.put("imageId", temp.getImageId());
        values.put("name", temp.getName());
        values.put("type", temp.getType());

        return DataSupport.update(Category.class, values, temp.getId()) == 1;
    }

    //删除资产
    public boolean deleteCategory(Integer id) {
        return DataSupport.delete(Category.class, id) == 1;
    }

}
