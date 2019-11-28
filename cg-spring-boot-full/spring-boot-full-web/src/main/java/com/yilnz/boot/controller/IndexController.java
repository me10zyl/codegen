package com.yilnz.boot.controller;

import com.github.pagehelper.PageInfo;
import com.yilnz.boot.entity.QQBotMsg;
import com.yilnz.boot.entity.json.QQBotMsgQuery;
import com.yilnz.boot.service.impl.QQBotMsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IndexController {

	@Autowired
	private QQBotMsgService qqBotMsgService;

	@GetMapping("/hello")
	@ResponseBody
	public String hello(){
		return "Hello, World!";
	}

	@GetMapping("")
	public String index(){
		return "index";
	}

	@GetMapping("/list")
	public String list(Model model, QQBotMsgQuery qqBotMsgQuery){
		final PageInfo<QQBotMsg> qqmsgsPageInfo = qqBotMsgService.list(qqBotMsgQuery);
		model.addAttribute("pageInfo", qqmsgsPageInfo);
		model.addAttribute("query", qqBotMsgQuery);
		return "list";
	}

}
