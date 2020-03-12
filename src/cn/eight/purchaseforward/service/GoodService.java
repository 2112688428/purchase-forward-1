package cn.eight.purchaseforward.service;

import cn.eight.purchaseforward.pojo.CarBean;
import cn.eight.purchaseforward.pojo.Good;

import java.util.List;

public interface GoodService {
    List<String> findAllGoodType();
    List<Good> findGoodsByType(String goodType);
    List<Good> findCars(CarBean carBean);

}
