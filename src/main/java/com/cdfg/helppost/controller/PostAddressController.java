package com.cdfg.helppost.controller;

import cn.cdfg.exceptionHandle.HelpPostNotFoundException;
import com.cdfg.helppost.pojo.dto.PostaddressDto;
import com.cdfg.helppost.pojo.until.Login;
import com.cdfg.helppost.pojo.until.Result;
import com.cdfg.helppost.pojo.until.Token;
import com.cdfg.helppost.service.PostAddressService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import static com.cdfg.helppost.pojo.until.Constant.*;
import static com.cdfg.helppost.pojo.until.Constant.errMsg_5;

/*
 * project name :自助邮寄
 * for:收货地址管理和新增接口
 * author：wangc
 * time：2020-10-10
 * */
@CrossOrigin
@RestController
@RequestMapping("/cdfg")
public class PostAddressController {
    @Autowired
    PostAddressService paService = null;
    Logger logger = Logger.getLogger(PostAddressController.class);

    /**
     * 收货地址管理接口
     * @param login
     * @return
     */
    @PostMapping("qryPostAddress")
    @ResponseBody
    public Result<PostaddressDto> qryPostAddress(HttpServletRequest request,@RequestBody Login login) {
        if (login == null){
            logger.error("收货地址管理接口传入的参数值为null");
            throw new HelpPostNotFoundException(errCode_5,errMsg_5);
        }

        String token = request.getHeader("Authorization");
        new Token().CheckToken(token);

        PostaddressDto pd = paService.qryPostAddress(login);
        logger.info("取到收货地址管理接口的传入参数"+login.getGwkh());
        return new Result<PostaddressDto>(sucCode,sucMsg,pd);
    }

    /**
     * 收货地址新增接口
     * @param paDto
     * @return
     */
    @PostMapping("insertPostAddress")
    @ResponseBody
    public Result<String> insertPostAddress(HttpServletRequest request,@RequestBody PostaddressDto paDto) {
        if (paDto == null){
            logger.error("收货地址新增接口传入的参数值为null");
            throw new HelpPostNotFoundException(errCode_5,errMsg_5);
        }

        String token = request.getHeader("Authorization");
        new Token().CheckToken(token);

        String address = paDto.getRec_provincename()+paDto.getRec_cityname()+paDto.getRec_areaname()+paDto.getRec_townname()+paDto.getRec_detailaddress();
        logger.info("取到收货地址管理接口的传入参数"+paDto.getGwkh()+"详细地址："+address);
        int rs = paService.insertPostAddress(paDto);

        return new Result<String>(sucCode,sucMsg,"");
    }
}
