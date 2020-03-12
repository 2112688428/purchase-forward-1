package cn.eight.purchaseforward.pojo;

import java.util.HashMap;
import java.util.Map;

public class CarBean {
    //定义一个属性来存放购物车的关键数据
    Map<Integer,Integer> car ;

    public Map<Integer, Integer> getCar() {
        return car;
    }

    public void setCar(Map<Integer, Integer> car) {
        this.car = car;
    }

    //添加商品，方法的调用对应于首页面中点击商品的操作
    public void addGood(Integer id){
        if(car == null){
           car = new HashMap<>();
        }
        car.put(id, 1);//默认买一件商品
    }
    //清空
    public void clearCar(){
        if(car != null){
            car.clear();
        }
    }
    //删除商品
    public void removeGood(Integer id){
        if(car != null){
            car.remove(id);
        }
    }
    //修改商品数量
    public void modGood(Integer[] ids,Integer[] amounts){
        for (int i = 0; i < ids.length; i++) {
            car.put(ids[i], amounts[i]);
        }
    }
}
