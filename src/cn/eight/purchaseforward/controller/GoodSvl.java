package cn.eight.purchaseforward.controller;

import cn.eight.purchaseforward.pojo.CarBean;
import cn.eight.purchaseforward.pojo.Good;
import cn.eight.purchaseforward.service.GoodService;
import cn.eight.purchaseforward.service.impl.GoodServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

@WebServlet("/qiantai/goodsvl")
public class GoodSvl extends HttpServlet {
    private GoodService service = new GoodServiceImpl();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String reqType = request.getParameter("reqType");
        if(reqType.equals("main")){
            openMain(request,response);
        }else if(reqType.equals("downImg")){
            downImg(request,response);
        } else if (reqType.equals("addCar")) {
            addCar(request,response);
        }
    }

    private void addCar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer id = Integer.valueOf(request.getParameter("goodid"));
        HttpSession session = request.getSession();
        //从session中取购物车
        CarBean carBean = (CarBean) session.getAttribute("car");
        if(carBean == null){
            carBean = new CarBean();
        }
        carBean.addGood(id);
        //把购物车添加到session中
        session.setAttribute("car",carBean);
        genericCarData(request,response,carBean);
    }

    //把CarBean对象中的表示购物车的map对象转换为能够在页面上展现的List<Good>集合对象
    private void genericCarData(HttpServletRequest request, HttpServletResponse response,CarBean carBean) throws ServletException, IOException {
        List<Good> goodList = service.findCars(carBean);
        request.setAttribute("car",goodList);
        request.getRequestDispatcher("flow.jsp").forward(request,response);
    }

    //向客户端发送图片
    private void downImg(HttpServletRequest request, HttpServletResponse response) {
        String filename = request.getParameter("filename");
        String path = request.getServletContext().getRealPath("/WEB-INF/upload/"+filename);
        FileInputStream fis = null;
        ServletOutputStream os = null;
        try {
            int len = 0;
            byte[] buff = new byte[1024];
            fis = new FileInputStream(path);
            os = response.getOutputStream();
            while((len = fis.read(buff))!=-1){
                os.write(buff,0,len);
            }
            os.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                fis.close();
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void openMain(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String goodType = request.getParameter("goodType");
        //取出所有的端口类别
        List<String> goodTypes = service.findAllGoodType();
        List<Good> goodList = null;
        if(goodTypes.size()>0){
            if(goodType == null||goodType.isEmpty()) {
                //取第一个类别
                goodType = goodTypes.get(0);
            }
            //取出该类别的所有商品
            goodList = service.findGoodsByType(goodType);
        }
        request.setAttribute("goodTypes", goodTypes);
        request.setAttribute("goodList",goodList);
        request.getRequestDispatcher("main.jsp").forward(request,response);
    }
}
