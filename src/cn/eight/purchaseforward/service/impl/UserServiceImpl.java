package cn.eight.purchaseforward.service.impl;

import cn.eight.purchaseforward.dao.UserDao;
import cn.eight.purchaseforward.pojo.User;
import cn.eight.purchaseforward.service.UserService;
import cn.eight.purchaseforward.util.Tools;

public class UserServiceImpl implements UserService {
    private UserDao userDao = new UserDao();

    @Override
    public boolean registerUser(User user) {
        //对用户密码进行加密处理
        user.setPassword(Tools.md5(user.getPassword()));
        return userDao.insertUser(user);
    }

    @Override
    public boolean checkUser(String username) {
        return userDao.queryUserByUserName(username);
    }

    @Override
    public boolean login(User user) {
        //要把密码进行转换，转换为加密后的值，再去比对
        user.setPassword(Tools.md5(user.getPassword()));
        return userDao.queryLoginUser(user);
    }
}
