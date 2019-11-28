package com.yilnz.codegen.entity;

import lombok.Data;

import java.io.File;

@Data
public class Demo {
    private String name;
    private File file;
    private boolean active;
}
