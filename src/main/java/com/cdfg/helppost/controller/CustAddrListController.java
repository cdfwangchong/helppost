package com.cdfg.helppost.controller;

import cn.cdfg.exceptionHandle.HelpPostNotFoundException;
import com.cdfg.helppost.pojo.dto.InsertCustAddrAndListDto;
import com.cdfg.helppost.pojo.until.BillEntity;
import com.cdfg.helppost.pojo.until.Result;
import com.cdfg.helppost.pojo.until.Token;
import com.cdfg.helppost.service.CustAddrListService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import static com.cdfg.helppost.pojo.until.Constant.*;

/*
 * project name :自助邮寄
 * for:商品邮寄接口
 * author：wangc
 * time：2020-10-10
 *
 * */
@CrossOrigin
@RestController
@RequestMapping("/cdfg")
public class CustAddrListController {
    @Autowired
    CustAddrListService calService = null;
    Logger logger = Logger.getLogger(CustAddrListController.class);

    @PostMapping("insertCustAddr")
    @ResponseBody
    public Result<String> insertCustAddrList(HttpServletRequest request, @RequestBody InsertCustAddrAndListDto ica){
        if (ica == null) {
            logger.error("获取到的对象值为空");
            throw new HelpPostNotFoundException(errCode_5,errMsg_5);
        }

        String token = request.getHeader("Authorization");
        String worknumber = new Token().CheckToken(token);

        //详细地址
        String address = ica.getRec_provincename()+ica.getRec_cityname()+ica.getRec_areaname()+ica.getRec_townname()+ica.getRec_detailaddress();
        logger.info("取到要邮寄的详细地址："+address);
        for (int i = 0; i < ica.getOrderList().size(); i++) {
            BillEntity pi = ica.getOrderList().get(i);
            logger.info("取到要邮寄的提货单："+pi.getShxsdno()+"门店："+pi.getMarket());
        }
        String seqno = calService.insertCustAddrList(ica,worknumber);
        if (!seqno.isEmpty()) {
            return new Result<String>(sucCode,sucMsg,seqno);
        }else {
            throw new HelpPostNotFoundException(errCode_3,errMsg_3);
        }
    }
}
