package com.example.cinema.sqlite;

import android.content.Context;

import com.example.cinema.bean.LoginBean;
import com.example.cinema.bean.UserInfoBean;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;

import java.sql.SQLException;
import java.util.List;

public class DBManager {
    private Dao<LoginBean,String> dao;
 
    public DBManager(Context context) throws SQLException {
        Helper helper = new Helper(context);
        dao = helper.getDao(LoginBean.class);
    }
 
    // 创建数据
    public void insertStudent(LoginBean student) throws SQLException {
        //在数据库中创建一条记录，作用与SQLiteDatabase.insert一样
        dao.createOrUpdate(student);
    }
 
    public void batchInsert(List<LoginBean> students) throws SQLException {
        dao.create(students);
    }
 
    /**
     * 查询数据
     *
     * @return
     * @throws SQLException
     */
    public List<LoginBean> getStudent() throws SQLException {
        List<LoginBean> list = dao.queryForAll();
        return list;
    }

 
    /**
     * 删除数据
     *
     * @param student
     * @throws SQLException
     */
    public void deleteStudent(LoginBean student) throws SQLException {
        //只看id
        dao.delete(student);
    }


}
