package com.yilnz.codegen.controller;

import com.yilnz.codegen.entity.Demo;
import com.yilnz.codegen.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/")
public class IndexController {


    @RequestMapping("")
    public String index(Model model){
        return "sbadmin2/index";
    }
}
