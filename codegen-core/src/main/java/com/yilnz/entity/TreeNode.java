package com.yilnz.entity;

import lombok.Data;

import java.util.List;

@Data
public class TreeNode {
	private String text;
	private String id;
	private Object children;
	private String icon;
}
