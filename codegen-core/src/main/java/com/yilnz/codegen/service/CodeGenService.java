package com.yilnz.codegen.service;

import com.yilnz.codegen.entity.Demo;
import com.yilnz.codegen.entity.Setting;
import com.yilnz.codegen.exception.BizException;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class CodeGenService {

    @Autowired
    private DemoService demoService;

    public void codeGenerate(String demo, String projectName, String generatePath) throws IOException {

        final Demo demo1 = demoService.findByDemoName(demo);
        final File destDir = Paths.get(generatePath, projectName).toFile();
        if(!destDir.exists()) {
            FileUtils.copyDirectory(demo1.getFile(), destDir);
        }else{
            throw new BizException("该文件夹已经存在");
        }
    }
}
