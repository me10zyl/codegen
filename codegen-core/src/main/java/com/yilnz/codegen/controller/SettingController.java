package com.yilnz.codegen.controller;

import com.sun.org.apache.xpath.internal.operations.Mod;
import com.yilnz.codegen.entity.Setting;
import com.yilnz.codegen.service.SettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/setting")
public class SettingController {

    @Autowired
    private SettingService settingService;

    @GetMapping("")
    public String setting(Model model){
        final Setting properties = settingService.getProperties();
        model.addAttribute("packageName", properties.getPackageName());
        model.addAttribute("genPath", properties.getGeneratePath());
        return "sbadmin2/setting";
    }

    @PostMapping("/save")
    @ResponseBody
    public void saveSetting(Setting setting){
        settingService.writeProperties(setting);
    }
}
