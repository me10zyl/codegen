package com.yilnz.codegen.conf;

import com.yilnz.codegen.entity.Demo;
import com.yilnz.codegen.exception.BizException;
import com.yilnz.codegen.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;

@ControllerAdvice(basePackages = "com.yilnz.codegen.controller")
public class DemoListInterceptor {

    @Autowired
    private DemoService demoService;

    @ModelAttribute
    public void addAttributes(Model model) {
        final List<Demo> allDemos = demoService.getAllDemos();
        model.addAttribute("demoList", allDemos);
    }

    @ExceptionHandler(value = BizException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public void exceptionHandler(BizException bizException, HttpServletResponse response) throws IOException {
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setCharacterEncoding("utf-8");
        final PrintWriter writer = response.getWriter();
        writer.write(new String(bizException.getMessage().getBytes(StandardCharsets.UTF_8), Charset.forName("UTF-8")));
        writer.flush();
    }
}
