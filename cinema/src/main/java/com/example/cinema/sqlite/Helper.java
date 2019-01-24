package com.example.cinema.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.cinema.bean.UserInfoBean;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

public class Helper extends OrmLiteSqliteOpenHelper {
    public Helper(Context context ) {
        super(context, "test", null, 1);
    }
 
    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            // 建表
            TableUtils.createTable(connectionSource,UserInfoBean.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
 
    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
 
    }
}
