package com.yeeshop.exception;


import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

@ControllerAdvice
public class ExceptionHandlingController {
    @ExceptionHandler(Exception.class)
    @RequestMapping("/error")
    public String exceptionPage(Exception ex){
        ex.printStackTrace();
        return "error/error";
    }
}
