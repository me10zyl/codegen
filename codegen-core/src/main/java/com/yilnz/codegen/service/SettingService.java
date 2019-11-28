package com.yilnz.codegen.service;

import com.yilnz.codegen.entity.Setting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.ResourceBundle;

@Service
public class SettingService {

    @Autowired
    private ApplicationContext applicationContext;

    public Setting getProperties(){
        final Properties properties = load();
        final String packageName = properties.getProperty("cg.pacakge-name");
        final String genLocation = properties.getProperty("cg.generate-location");
        final Setting setting = new Setting();
        setting.setPackageName(packageName);
        setting.setGeneratePath(genLocation);
        return setting;
    }

    public void writeProperties(Setting setting){
        final Resource resource = applicationContext.getResource("classpath:/codegen.properties");
        final Properties properties = new Properties();
        properties.setProperty("cg.generate-location", setting.getGeneratePath());
        properties.setProperty("cg.pacakge-name", setting.getPackageName());
        try {
            properties.store(new FileOutputStream(resource.getFile()), null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Properties load() {
        final Resource resource = applicationContext.getResource("classpath:/codegen.properties");
        final Properties properties = new Properties();
        try {
            properties.load(resource.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }
}
