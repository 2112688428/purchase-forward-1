package cn.eight.purchaseforward.util;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class DbPool {
    private static ComboPooledDataSource ds;

    static {
        //创建连接池对象并设置属性
        ds = new ComboPooledDataSource();
        //读取属性文件中的值
        Properties properties = new Properties();
        try {
            properties.load(DbPool.class.getClassLoader().getResourceAsStream("db.properties"));
            ds.setDriverClass(properties.getProperty("driverName"));
            ds.setUser(properties.getProperty("username"));
            ds.setPassword(properties.getProperty("password"));
            ds.setJdbcUrl(properties.getProperty("url"));
            ds.setInitialPoolSize(Integer.valueOf(properties.getProperty("InitialPoolSize")));
            ds.setMaxPoolSize(Integer.valueOf(properties.getProperty("MaxPoolSize")));
            ds.setMinPoolSize(Integer.valueOf(properties.getProperty("MinPoolSize")));
            ds.setMaxIdleTime(Integer.valueOf(properties.getProperty("MaxIdleTime")));
            ds.setMaxStatements(Integer.valueOf(properties.getProperty("MaxStatements")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection(){
        try {
            return ds.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
