package com.yilnz.service;

import com.yilnz.entity.Demo;
import com.yilnz.entity.TreeNode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class DemoService implements ApplicationContextAware {

    private ApplicationContext applicationContext;
    private List<Demo> cache;
    private final static String[] ignoreFiles = new String[]{"target", "*.iml"};

    public Demo findByDemoName(String demoName) {
        final List<Demo> allDemos = getAllDemos();
        resetActive(allDemos);
        final Optional<Demo> any = allDemos.stream().filter(e -> e.getName().equals(demoName)).findAny();
        if (!any.isPresent()) {
            return null;
        }
        any.get().setActive(true);
        return any.get();
    }

    public void resetActive(List<Demo> allDemos){
        allDemos.forEach(e -> e.setActive(false));
    }

    public List<Demo> getAllDemos() {
        if (cache != null) {
            resetActive(cache);
            return cache;
        }
        List<Demo> demos = new ArrayList<>();
        File[] files = null;
        try {
            files = applicationContext.getResource("classpath:/cg/").getFile().listFiles();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert files != null;
        for (File file1 : files) {
            if (!file1.getName().startsWith("cg")) {
                continue;
            }
            final Demo demo = new Demo();
            demo.setName(file1.getName());
            demo.setFile(file1);
            demos.add(demo);
        }
        if (demos.size() > 0) {
            cache = demos;
        }
        return demos;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public List<TreeNode> getTree(String demoName, String id, String s) {
        final Demo demo = findByDemoName(demoName);
        File file = null;
        if (id == null || id.equals("#")) {
            file = demo.getFile();
        }else{
            file = new File(id);
        }
        final List<File> files = new ArrayList<>();
        final Path root = file.toPath();
        if(!StringUtils.isEmpty(s)) {
            try {
                Files.walkFileTree(root, new SimpleFileVisitor<Path>() {
                    @Override
                    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                        if(!dir.equals(root) && StringUtils.containsIgnoreCase(dir.toFile().getName(),s)){
                            files.add(dir.toFile());
                        }
                        return super.preVisitDirectory(dir, attrs);
                    }

                    @Override
                    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                        if(StringUtils.containsIgnoreCase(file.toFile().getName(),s)){
                            files.add(file.toFile());
                        }
                        return FileVisitResult.CONTINUE;
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            files.addAll(Arrays.asList(file.listFiles()));
        }
        final List<TreeNode> treeNodes = new ArrayList<>();
        for (File file1 : files) {
            boolean skip = false;
            for (String ignoreFile : ignoreFiles) {
                if(file1.getParentFile().equals(root.toFile())){
                    if(ignoreFile.contains("*")){
                        if(file1.getName().contains(ignoreFile.replace("*",""))){
                            skip = true;
                            break;
                        }
                    }else{
                        if(file1.getName().equals(ignoreFile)){
                            skip = true;
                            break;
                        }
                    }
                }
            }
            if (skip) {
                continue;
            }

            final TreeNode treeNode = new TreeNode();
            treeNode.setId(file1.getPath());
            treeNode.setText(file1.getName());
            if (file1.isDirectory() && file1.listFiles() != null) {
                treeNode.setChildren(true);
            }
            if (file1.isFile()) {
                treeNode.setIcon("jstree-file");
            }
            treeNodes.add(treeNode);
        }
        return treeNodes;
    }
}
