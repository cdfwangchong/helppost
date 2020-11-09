package com.cdfg.helppost.controller;

import cn.cdfg.exceptionHandle.HelpPostNotFoundException;
import com.cdfg.helppost.pojo.dto.LeavedDto;
import com.cdfg.helppost.pojo.dto.XsdnoDto;
import com.cdfg.helppost.pojo.until.*;
import com.cdfg.helppost.service.QryBillIsPostService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.cdfg.helppost.pojo.until.Constant.*;

/*
 * project name :自助邮寄
 * for:未邮寄提货单接口和已经邮寄的提货单接口
 * author：wangc
 * time：2020-11-04
 * */
@CrossOrigin
@RestController
@RequestMapping("/cdfg")
public class QryBillIsPostController {

    @Autowired
    private QryBillIsPostService qbipService=null;
    Logger logger = Logger.getLogger(QryBillIsPostController.class);

    /**
     * 未邮寄提货单接口
     * @param login
     * @return
     */
    @PostMapping("/qrynotpostbill")
    @ResponseBody
    public Result<XsdnoDto> qryNotPostBill(HttpServletRequest request,@RequestBody Login login) {
        XsdnoDto xsdnoDto;

        if (login == null){
            logger.error("未邮寄提货单接口传入的参数值为null");
            throw new HelpPostNotFoundException(errCode_5,errMsg_5);
        }
        String token = request.getHeader("Authorization");
        String worknumber = new Token().CheckToken(token);

        xsdnoDto = qbipService.qryNotPostBill(login,worknumber);
        for (int i = 0; i < xsdnoDto.getOrderList().size(); i++) {
            BillEntity be = xsdnoDto.getOrderList().get(i);
            logger.info("取到未邮寄提货单接口返回值："+be.getMarket()+"#"+be.getShxsdno()+"#"+be.getShoughtpay());
        }
        return new Result<XsdnoDto>(sucCode,sucMsg,xsdnoDto);
    }

    /**
     * 邮寄提货单查询接口
     * @param login
     * @return
     */
    @PostMapping("/qrypostbill")
    @ResponseBody
    public Result<List<CustAddrlistEntity>> qryPostBill(HttpServletRequest request,@RequestBody Login login) {
        List<CustAddrlistEntity> beList;

        if (login == null){
            logger.error("邮寄提货单查询接口传入的参数值为null");
            throw new HelpPostNotFoundException(errCode_5,errMsg_5);
        }

        String token = request.getHeader("Authorization");
        String worknumber = new Token().CheckToken(token);

        beList = qbipService.qryPostBill(login,worknumber);
        for (int i = 0; i < beList.size(); i++) {
            CustAddrlistEntity be = beList.get(i);
            String address = be.getRec_provincename()+be.getRec_cityname()+be.getRec_areaname()+be.getRec_townname()+be.getRec_detailaddress();
            logger.info("取到邮寄提货单接口返回值："+be.getRec_name()+"#"+be.getRec_xsdno()+"#"+be.getSeq_no()+address);
        }
        return new Result<List<CustAddrlistEntity>>(sucCode,sucMsg,beList);
    }

    /**
     * 邮寄提货单确认接口
     * @param leavedDto
     * @return
     */
    @PostMapping("/updateleaved")
    @ResponseBody
    public Result<String> updateLeaved(HttpServletRequest request,@RequestBody LeavedDto leavedDto) {

        if (leavedDto == null){
            logger.error("邮寄提货单确认接口传入的参数值为null");
            throw new HelpPostNotFoundException(errCode_5,errMsg_5);
        }
        String token = request.getHeader("Authorization");
        String worknumber = new Token().CheckToken(token);
//        String worknumber = "3859";
        logger.info("取到确认邮寄单的操作员"+worknumber+"序号"+leavedDto.getSeq_no());

        String ret_flag = qbipService.updateLeaved(leavedDto,worknumber);
        if ("1002".equals(ret_flag)) {
            return new Result<String>(sucCode,sucMsg,"");
        }else {
            logger.info("该顾客邮寄商品确认失败"+leavedDto.getGwkh()+"商品序号："+leavedDto.getSeq_no());
            throw new HelpPostNotFoundException(errCode_15,errMsg_15);
        }
    }


}
