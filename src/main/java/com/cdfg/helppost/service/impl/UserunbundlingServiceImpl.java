package com.cdfg.helppost.service.impl;

import cn.cdfg.exceptionHandle.HelpPostNotFoundException;
import com.cdfg.helppost.dao.UserUnbundlingDao;
import com.cdfg.helppost.pojo.dto.UnbundlingDto;
import com.cdfg.helppost.service.UserunbundlingService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;
import static com.cdfg.helppost.pojo.until.Constant.*;

@Service
public class UserunbundlingServiceImpl implements UserunbundlingService {
    @Autowired
    UserUnbundlingDao uuDao=null;
    Logger logger = Logger.getLogger(UserunbundlingServiceImpl.class);

    @Override
    public Map<String, String> userUnbundling(UnbundlingDto unbundlingDto,String worknumber) {
        if (unbundlingDto == null) {
            logger.error("解绑信息接口传入的对象为空");
            throw new HelpPostNotFoundException(errCode_5,errMsg_5);
        }
        Map param = new HashMap<String,String>();
        String ret_flag;
        String ret_msg;
        String card_id = unbundlingDto.getCard_id();
        String cust_name = unbundlingDto.getCust_name();
        String tel_num = unbundlingDto.getTel_num();
        String pre_cardid = unbundlingDto.getPre_cardid();
        String pre_telnum = unbundlingDto.getPre_telnum();
        String remark = unbundlingDto.getRemark();
        String card_type = unbundlingDto.getCard_type();
        String pre_cardtype = unbundlingDto.getPre_cardtype();

        logger.info("解绑接口传入的信息："+card_id+"#"+cust_name+"#"+
                tel_num+"#"+"#"+pre_cardid+"#"+pre_telnum+"#"+remark);
        param.put("card_id",card_id);
        param.put("cust_name",cust_name);
        param.put("tel_num",tel_num);
        param.put("pre_cardid",pre_cardid);
        param.put("pre_telnum",pre_telnum);
        param.put("remark",remark);
        param.put("operator",worknumber);
        param.put("card_type",card_type);
        param.put("pre_cardtype",pre_cardtype);
        try {
            uuDao.userUnbundling(param);
        } catch (Exception e) {
            logger.error(card_id+"解绑信息写入异常");
            throw new HelpPostNotFoundException(errCode_22,errMsg_22);
        }
        ret_flag = (String) param.get("ret_flag");
        ret_msg = (String) param.get("ret_msg");
        if ("1001".equals(ret_flag)) {
            logger.error(ret_msg);
            throw new HelpPostNotFoundException(errCode,ret_msg);
        }
        logger.info(ret_flag+ret_msg);
        Map<String,String> retmap = new HashMap<>();
        retmap.put("ret_flag",ret_flag);
        retmap.put("ret_msg",ret_msg);
        return retmap;
    }
}
