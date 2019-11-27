package com.yilnz.controller;

import com.yilnz.entity.Demo;
import com.yilnz.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/")
public class IndexController {

    @Autowired
    private DemoService demoService;

    @RequestMapping("")
    public String index(Model model){
        final List<Demo> allDemos = demoService.getAllDemos();
        model.addAttribute("demoList", allDemos);
        return "sbadmin2/index";
    }
}
