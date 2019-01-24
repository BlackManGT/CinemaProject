package com.example.cinema.sqlite;

import android.content.Context;

import com.example.cinema.bean.UserInfoBean;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;

import java.sql.SQLException;
import java.util.List;

public class DBManager {
    private Dao<UserInfoBean,String> dao;
 
    public DBManager(Context context) throws SQLException {
        Helper helper = new Helper(context);
        dao = helper.getDao(UserInfoBean.class);
    }
 
    // 创建数据
    public void insertStudent(UserInfoBean student) throws SQLException {
        //在数据库中创建一条记录，作用与SQLiteDatabase.insert一样
        dao.createOrUpdate(student);
    }
 
    public void batchInsert(List<UserInfoBean> students) throws SQLException {
        dao.create(students);
    }
 
    /**
     * 查询数据
     *
     * @return
     * @throws SQLException
     */
    public List<UserInfoBean> getStudent() throws SQLException {
        List<UserInfoBean> list = dao.queryForAll();
        return list;
    }
 
    /**
     * 查询某个数据
     *
     * @return
     * @throws SQLException
     */
    public List<UserInfoBean> queryGuanyu() throws SQLException {
        //Eq是equals的缩写
        //方法1
        List<UserInfoBean> list = dao.queryForEq("nickName", "张飞");
 
        //方法2
//        QueryBuilder queryBuilder = dao.queryBuilder();
////        queryBuilder.offset(); //偏移量
////        queryBuilder.limit(8l); //最多几行  offset + limit 做分页
////        queryBuilder.orderBy("age",true);
//        queryBuilder.where().eq("nickName","关羽"); //多条件查询
//        List<UserInfoBean> query = queryBuilder.query();//此方法相当于build，提交设置
        return list;
    }
 
    /**
     * 删除数据
     *
     * @param student
     * @throws SQLException
     */
    public void deleteStudent(UserInfoBean student) throws SQLException {
        //只看id
        dao.delete(student);
    }
 
    /**
     * 删除指定数据
     *
     * @throws SQLException
     */
    public void deleteGuanyu() throws SQLException {
        DeleteBuilder deleteBuilder = dao.deleteBuilder();
        deleteBuilder.where().eq("nickName", "关羽");
        deleteBuilder.delete();
    }
 
    /**
     * 修改数据
     *
     * @param student
     * @throws SQLException
     */
    public void updateStudent(UserInfoBean student) throws SQLException {
        student.setNickName("关羽");
        dao.update(student);
    }
 
 
}
