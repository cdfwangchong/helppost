package com.cdfg.helppost.controller;

import cn.cdfg.exceptionHandle.HelpPostNotFoundException;
import com.cdfg.helppost.pojo.until.Result;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@ControllerAdvice
public class HelpPostExceptionController {

    @ExceptionHandler(value = HelpPostNotFoundException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @RequestMapping(produces="json/html; charset=UTF-8")
    @ResponseBody
    public Result<String> exception (HelpPostNotFoundException exception){
        System.out.println(exception.getMsg());
        return new Result<String>(exception.getRetCode(),exception.getMsg(),"");
    }
}
