package cn.eight.purchaseforward.dao;

import cn.eight.purchaseforward.pojo.User;
import cn.eight.purchaseforward.util.DbPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {
    private BasicDao dao = new BasicDao();

    public boolean queryUser(User user){
        boolean result = false;
        PreparedStatement pst = null;
        ResultSet rs =null;
        Connection con = DbPool.getConnection();
        String sql = "select count(*) from user where username=? and password=? and rule=0";
        try {
            pst = con.prepareStatement(sql);
            rs = dao.execQuery(con, pst, user.getUsername(), user.getPassword());
            if(rs!=null&&rs.next()&&rs.getInt(1)==1){
                result = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            dao.releaseResource(rs,pst,con);
        }
        return result;
    }
}
