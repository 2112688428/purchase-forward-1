package cn.eight.purchaseforward.service.impl;

import cn.eight.purchaseforward.dao.GoodDao;
import cn.eight.purchaseforward.pojo.CarBean;
import cn.eight.purchaseforward.pojo.Good;
import cn.eight.purchaseforward.service.GoodService;

import java.util.List;

public class GoodServiceImpl implements GoodService {
    private GoodDao goodDao = new GoodDao();

    @Override
    public List<String> findAllGoodType() {
        return goodDao.queryGoodType();
    }

    @Override
    public List<Good> findGoodsByType(String goodType) {
        return goodDao.queryGoodByType(goodType);
    }

    @Override
    public List<Good> findCars(CarBean carBean) {
        return goodDao.queryCarBean(carBean);
    }
}
