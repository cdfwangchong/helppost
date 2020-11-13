package com.cdfg.helppost.service.impl;

import cn.cdfg.exceptionHandle.ExceptionPrintMessage;
import cn.cdfg.exceptionHandle.HelpPostNotFoundException;
import com.cdfg.helppost.dao.QryBillIsPostDao;
import com.cdfg.helppost.pojo.dto.LeavedDto;
import com.cdfg.helppost.pojo.dto.XsdnoDto;
import com.cdfg.helppost.pojo.until.BillEntity;
import com.cdfg.helppost.pojo.until.CustAddrlistEntity;
import com.cdfg.helppost.pojo.until.Login;
import com.cdfg.helppost.service.QryBillIsPostService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.cdfg.helppost.pojo.until.Constant.*;

@Service
public class QryBillIsPostServiceImpl implements QryBillIsPostService {
    @Autowired
    private QryBillIsPostDao qbipDao=null;
    Logger logger = Logger.getLogger(QryBillIsPostServiceImpl.class);

    /**
     * 未邮寄提货单接口
     * @param login
     * @return
     */
    @Override
    public XsdnoDto qryNotPostBill(Login login,String worknumber) {

        List<BillEntity> beList;
        String ret_card;
        String ret_name;
        Map param = new HashMap<String,String>();
        XsdnoDto xsdnoDto = new XsdnoDto();

        try {
            param.put("gwkh",login.getGwkh());
            param.put("i_userId",worknumber);

            qbipDao.qryNotPostBill(param);
            //取出结果集
            beList = (List<BillEntity>) param.get("wyjRc");
            ret_name = (String)param.get("ret_name");
            ret_card = (String)param.get("ret_card");

            xsdnoDto.setOrderList(beList);
            xsdnoDto.setRet_card(ret_card);
            xsdnoDto.setRet_name(ret_name);
        } catch (Exception e) {
            logger.error(new ExceptionPrintMessage().errorTrackSpace(e));
            logger.error("查找未邮寄的提货单存储过程返回值异常");
            throw new HelpPostNotFoundException(errCode,errMsg);
        }
        //取出ret_flag
        String ret_flag = (String) param.get("ret_flag");
        if ("0".equals(ret_flag)) {
            logger.error("该离岛日期使用邮寄提货票数已经满");
            throw new HelpPostNotFoundException(errCode_17,errMsg_17);
        }
        return xsdnoDto;
    }

    /**
     * 邮寄提货单查询接口
     * @param login
     * @return
     */
    @Override
    public List<CustAddrlistEntity> qryPostBill(Login login,String worknumber) {
        Map param = new HashMap<String,String>();
        param.put("i_gwkh",login.getGwkh());
        param.put("i_userId",worknumber);
        List<CustAddrlistEntity> beyList;

        try {
            qbipDao.qryPostBill(param);

            //取出结果集
            beyList = (List<CustAddrlistEntity>) param.get("yjRc");
        } catch (Exception e) {
            logger.error(new ExceptionPrintMessage().errorTrackSpace(e));
            logger.error("查找已邮寄的提货单存储过程返回值异常");
            throw new HelpPostNotFoundException(errCode,errMsg);
        }
        return beyList;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout = 30,rollbackFor = Exception.class)
    public String updateLeaved(LeavedDto leavedDto, String worknumber) {
        Map param = new HashMap<String,String>();
        param.put("i_gwkh",leavedDto.getGwkh());
        param.put("seq_no",leavedDto.getSeq_no());
        param.put("operator",worknumber);
        String ret_flag;

        try {
            qbipDao.updateLeaved(param);

            //取出结果集
            ret_flag = (String) param.get("ret_flag");
        } catch (Exception e) {
            logger.error(new ExceptionPrintMessage().errorTrackSpace(e));
            logger.error("查找已邮寄的提货单存储过程返回值异常");
            throw new HelpPostNotFoundException(errCode,errMsg);
        }
        return ret_flag;
    }
}
