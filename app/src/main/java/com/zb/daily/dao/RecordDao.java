package com.zb.daily.dao;

import com.zb.daily.model.Category;
import com.zb.daily.model.Record;
import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * @auther: zb
 * @Date: 2019/3/29 17:01
 * @Description: 数据库记录表的crud操作
 */
public class RecordDao {

    //保存记录
    public boolean saveRecord(Record temp) {
        return temp.save();
    }

    //保存记录集合
    public void saveRecordList(List<Record> recordList) {
        DataSupport.saveAll(recordList);
    }

    //查询记录集合按时间降序排列
    public List<Record> findRecordList() {
        List<Record> recordList = DataSupport.order("date desc, id desc").find(Record.class);
        return recordList;
    }

    //查询日支出/收入
    public double getDaySummary(Integer type, String date) {
        List<Record> recordList = DataSupport.select("money").where("type = ? and date = ?", type.toString(), date).find(Record.class);
        double sum = 0;
        for (Record record : recordList){
            sum += record.getMoney();
        }
        return sum;
    }

    //删除记录
    public boolean deleteRecord(Integer id) {
        return DataSupport.delete(Record.class, id) == 1;
    }
}
