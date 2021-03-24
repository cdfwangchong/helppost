package com.cdfg.helppost.controller;

import cn.cdfg.exceptionHandle.HelpPostNotFoundException;
import com.cdfg.helppost.pojo.dto.TracenoDto;
import com.cdfg.helppost.pojo.dto.WaybilltraceDto;
import com.cdfg.helppost.pojo.until.Result;
import com.cdfg.helppost.service.QryTranceService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.cdfg.helppost.pojo.until.Constant.*;


@CrossOrigin
@RestController
@RequestMapping("/cdfg")
public class QryTraceController {
    @Autowired
    QryTranceService qtService = null;
    Logger logger = Logger.getLogger(QryTraceController.class);

    @PostMapping("/qryWaybilltrace")
    @ResponseBody
    public Result<List<WaybilltraceDto>> QryWaybilltrace(@RequestBody TracenoDto wbtDto) {
        String traceno = wbtDto.getTraceno();
        if (traceno == null) {
            logger.error("传入的快递单为空");
            throw new HelpPostNotFoundException(errCode19,errMsg19);
        }
        logger.info("运单轨迹查询接口传入值："+traceno);
        List<WaybilltraceDto> beList;
        beList = qtService.QryWaybilltrace(traceno);
        return new Result<List<WaybilltraceDto>>(sucCode,sucMsg,beList);
    }
}
