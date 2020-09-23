package com.springboot.controller;

import com.springboot.bean.DataBean;
import com.springboot.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class DataController {

    @Autowired
    private DataService dataService;


    @GetMapping("/")
    public String list(Model model){
        //iService这个父类提供的方法叫做 .list
        //通过.list 调用mapper连接的数据库里的数据
        List<DataBean>  list = dataService.list();
        model.addAttribute("dataList",list);
        return "list";
    }

//    @GetMapping("/list/{id}")
//    //@PathVariable:将接收到的地址数据 映射到方法参数中
//    public String listById(Model model, @PathVariable String id){
//        List<DataBean>  list = dataService.listById(Integer.parseInt(id));
//        model.addAttribute("dataList",list);
//        return "list";
    }

