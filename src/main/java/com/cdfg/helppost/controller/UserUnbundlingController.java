package com.cdfg.helppost.controller;

import cn.cdfg.exceptionHandle.HelpPostNotFoundException;
import com.cdfg.helppost.pojo.dto.UnbundlingDto;
import com.cdfg.helppost.pojo.until.Result;
import com.cdfg.helppost.pojo.until.Token;
import com.cdfg.helppost.service.UserunbundlingService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

import static com.cdfg.helppost.pojo.until.Constant.*;


/*
 * project name :自助邮寄
 * for:用户解绑
 * author：wangc
 * time：2021-03-06
 * */
@Controller
@RequestMapping("/cdfg")
@CrossOrigin
public class UserUnbundlingController {
    @Autowired
    UserunbundlingService uuservice;
    Logger logger = Logger.getLogger(UserUnbundlingController.class);

    @PostMapping("/updateUserinfo")
    @ResponseBody
    public Result<String> userUnbundling(HttpServletRequest request, @RequestBody UnbundlingDto unbundlingDto) throws NullPointerException {
        if (unbundlingDto == null) {
            logger.error("传入的解绑信息对象为空");
            throw new HelpPostNotFoundException(errCode_5,errMsg_5);
        }
        String token = request.getHeader("Authorization");
        String worknumber = new Token().CheckToken(token);

//        String worknumber = "3859";
        Map<String,String> retmap = uuservice.userUnbundling(unbundlingDto,worknumber);
        String ret_flag = retmap.get("ret_flag");
        String ret_msg = retmap.get("ret_msg");
        if ("1001".equals(ret_flag)) {
            logger.error(ret_msg);
            throw new HelpPostNotFoundException(errCode,ret_msg);
        }else {
            return new Result<String>(sucCode,sucMsg,"");
        }
    }
}
