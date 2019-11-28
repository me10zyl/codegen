package com.yilnz.boot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class IndexController {

	@GetMapping("/hello")
	@ResponseBody
	public String hello(){
		return "Hello, World!";
	}

	@GetMapping("")
	public String index(){
		return "index";
	}
}
