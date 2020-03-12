package cn.eight.purchaseforward.dao;

import cn.eight.purchaseforward.pojo.CarBean;
import cn.eight.purchaseforward.pojo.Good;
import cn.eight.purchaseforward.util.DbPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GoodDao {
    private BasicDao dao = new BasicDao();

    public List<String> queryGoodType(){
        String sql = "select DISTINCT goodtype from good order by goodtype";
        PreparedStatement pst = null;
        ResultSet rs = null;
        Connection con = DbPool.getConnection();
        try {
            pst = con.prepareStatement(sql);
            rs = dao.execQuery(con, pst, null);
            List<String> goodTypeList = new ArrayList<>();
            while(rs!=null&&rs.next()){
                goodTypeList.add(rs.getString(1));
            }
            return goodTypeList;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            dao.releaseResource(rs,pst,con);
        }
        return null;
    }

    public List<Good> queryGoodByType(String goodType){
        String sql = "select * from good where goodtype=? order by id limit ?,?";
        PreparedStatement pst = null;
        ResultSet rs = null;
        Connection con = DbPool.getConnection();
        try {
            pst = con.prepareStatement(sql);
            rs = dao.execQuery(con, pst,goodType,0,20);
            List<Good> goodList = new ArrayList<>();
            while(rs!=null&&rs.next()){
                Good goodBean = new Good();
                goodBean.setId(rs.getInt(1));
                goodBean.setGoodname(rs.getString(2));
                goodBean.setGoodtype(rs.getString(3));
                goodBean.setPrice(rs.getDouble(4));
                goodBean.setPic(rs.getString(5));
                goodList.add(goodBean);
            }
            return goodList;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            dao.releaseResource(rs,pst,con);
        }
        return null;
    }

    public List<Good> queryCarBean(CarBean carBean){
        List<Good> goodList = new ArrayList<>();
        Map<Integer, Integer> car = carBean.getCar();
        //从map中取出所有的商品id
        StringBuilder ids = new StringBuilder();
        for (Map.Entry<Integer,Integer> entry:car.entrySet()) {
            Integer id = entry.getKey();
            ids.append(id.toString()).append(",");
        }
        String idsStr = ids.toString();
        if(!idsStr.isEmpty()){//去掉最后的逗号
            int pos = idsStr.lastIndexOf(",");
            idsStr = idsStr.substring(0,pos);
        }else{
            return goodList;
        }

        String sql = "select * from good where id in ("+idsStr+")";
        PreparedStatement pst = null;
        ResultSet rs = null;
        Connection con = DbPool.getConnection();
        try {
            pst = con.prepareStatement(sql);
            rs = dao.execQuery(con, pst,null);
            goodList = new ArrayList<>();
            while(rs!=null&&rs.next()){
                Good goodBean = new Good();
                goodBean.setId(rs.getInt(1));
                goodBean.setGoodname(rs.getString(2));
                goodBean.setPrice(rs.getDouble(4));
                //存放购物车中的数量
                goodBean.setAmount(car.get(rs.getInt(1)));
                goodList.add(goodBean);
            }
            return goodList;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            dao.releaseResource(rs,pst,con);
        }
        return null;
    }


}
