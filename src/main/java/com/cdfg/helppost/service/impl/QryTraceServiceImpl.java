package com.cdfg.helppost.service.impl;

import cn.cdfg.exceptionHandle.HelpPostNotFoundException;
import com.cdfg.helppost.dao.QrytraceDao;
import com.cdfg.helppost.pojo.dto.WaybilltraceDto;
import com.cdfg.helppost.service.QryTranceService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.cdfg.helppost.pojo.until.Constant.*;

@Service
public class QryTraceServiceImpl implements QryTranceService {

    @Autowired
    private QrytraceDao qrytranceDao = null;
    Logger logger = Logger.getLogger(QryTraceServiceImpl.class);
    @Override
    public List<WaybilltraceDto> QryWaybilltrace(String trance) {
        if (trance==null) {
            logger.error("传入的快递单为空");
            throw new HelpPostNotFoundException(errCode19,errMsg19);
        }
        Map param = new HashMap<String,String>();
        List<WaybilltraceDto> beList;
        String ret_flag=null;
        String ret_msg=null;
        try {
            param.put("traceno",trance);
            qrytranceDao.QryWaybilltrace(param);
            //取出结果集
            beList = (List<WaybilltraceDto>) param.get("traceRc");
            ret_flag = (String)param.get("ret_flag");
            ret_msg = (String)param.get("ret_msg");
            if (!"1002".equals(ret_flag)) {
                logger.error("查询运单轨迹失败");
                throw new HelpPostNotFoundException(errCode,ret_msg);
            }
            if (beList.isEmpty()) {
                logger.error("返回的运单轨迹结果集为空");
                throw new HelpPostNotFoundException(errCode21,errMsg21);
            }
        } catch (Exception e) {
            logger.error("查询运单轨迹存储过程返回值异常");
            throw new HelpPostNotFoundException(errCode22,errMsg22);
        }
        logger.info("ret_flag"+ret_flag+" ret_msg："+ret_msg);
        return beList;
    }
}
