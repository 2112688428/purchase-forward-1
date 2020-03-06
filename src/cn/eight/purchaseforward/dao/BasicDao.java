package cn.eight.purchaseforward.dao;

import cn.eight.purchaseforward.util.DbPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//项目中对数据库的操作分为两大部分，一个是查询，另一块增删改，实现一些公共的功能
//此类的目的是为了减少一些反复出现的代码，减少代码的冗余
public class BasicDao {
    //公共的查询功能
    public ResultSet execQuery(Connection con, PreparedStatement pst,Object...params){
        //针对占位符传参
        ResultSet rs = null;
        try {
            if(params!=null){
                for (int i = 0; i < params.length; i++) {
                    pst.setObject(i+1,params[i]);
                }
            }
            rs = pst.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    //公共的修改功能
    public void execUpdate(Connection con, PreparedStatement pst,Object...params) throws SQLException {
        //针对占位符传参
         if(params != null){
             for (int i = 0; i < params.length; i++) {
                 pst.setObject(i+1,params[i]);
             }
         }
         pst.executeUpdate();
    }

    //公共的释放资源的方法
    public void releaseResource(ResultSet rs,PreparedStatement pst,Connection con){
        try {
            if(rs != null){
                rs.close();
            }
            if(pst != null){
                pst.close();
            }
            if(con != null){
                con.close();
                con = null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Connection con = DbPool.getConnection();
        String sql = "insert user(username,password,rule,email,qq) value(?,?,?,?,?)";
        BasicDao dao = new BasicDao();
        try {
            PreparedStatement pst = con.prepareStatement(sql);
            dao.execUpdate(con,pst,"admin","123456",0,null,null);
            dao.releaseResource(null,pst,con);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
