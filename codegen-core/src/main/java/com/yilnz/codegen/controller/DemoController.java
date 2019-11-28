package com.yilnz.codegen.controller;

import com.yilnz.codegen.entity.Demo;
import com.yilnz.codegen.entity.TreeNode;
import com.yilnz.codegen.service.CodeGenService;
import com.yilnz.codegen.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

@Controller
public class DemoController {

    @Autowired
    private DemoService demoService;
    @Autowired
    private CodeGenService codeGenService;

    @GetMapping("/demo/{demo}")
    public String getByDemoName(@PathVariable("demo") String demo, Model model){
        final Demo demo1 = demoService.findByDemoName(demo);
        model.addAttribute("demo", demo1);
        return "sbadmin2/demopage";
    }

    @GetMapping("/tree/lazy")
    @ResponseBody
    public List<TreeNode> getTree(@RequestParam("demo") String demo, @RequestParam String id, @RequestParam(required = false) String s){
        return demoService.getTree(demo, id, s);
    }

    @GetMapping("/watch/file")
    @ResponseBody
    public String watchFile(@RequestParam String path){
        final File file = new File(path);
        if (file.isFile() && file.exists()) {
            String fileStr = "";
            try {
                fileStr = new String(Files.readAllBytes(file.toPath()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            return fileStr;
        }
        return "";
    }

    @GetMapping("/generate/gen")
    @ResponseBody
    public void generate(String demo) throws IOException {
        codeGenService.codeGenerate(demo);
    }
}
