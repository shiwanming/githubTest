package com.test.login;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.sql.*;

@Controller
public class LoginController {

    @RequestMapping("/login")
    public ModelAndView index(String name,String password){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        String concatRuselt = name+password+"".trim();
        System.out.println(concatRuselt+"***");

        //用来返回数据和页面
        ModelAndView modelAndView = new ModelAndView();

        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test","root","123456");
            //这里我讲姓名和密码拼接，好查询一些，，，，，
            String sql = "select * from test.user where concat(name,password)=\'"+concatRuselt+"\'";
            preparedStatement = connection.prepareStatement(sql);

            //查询结果集
            resultSet = preparedStatement.executeQuery();
            //如果没有下一个结果就说明没有查询到数据
            if(!resultSet.next()){
                modelAndView.addObject("message","用户名或者密码错误");
                //设置返回的页面
                modelAndView.setViewName("login");
                return modelAndView;
            }


            /*while (resultSet.next()){
                System.out.println(resultSet.getString("name"));
            }*/
            connection.close();
            preparedStatement.close();
            resultSet.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        modelAndView.addObject("message","恭喜。。。用户名是："+name+"---密码是："+password);
        modelAndView.setViewName("index");
        return modelAndView;
    }
}
