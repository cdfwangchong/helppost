package com.cdfg.helppost.service.impl;

import cn.cdfg.exceptionHandle.ExceptionPrintMessage;
import cn.cdfg.exceptionHandle.HelpPostNotFoundException;
import com.cdfg.helppost.dao.PostaddressDao;
import com.cdfg.helppost.pojo.dto.PostaddressDto;
import com.cdfg.helppost.pojo.until.Login;
import com.cdfg.helppost.service.PostAddressService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static com.cdfg.helppost.pojo.until.Constant.*;

@Service
public class PostAddressServiceImpl implements PostAddressService {

    @Autowired
    PostaddressDao paDao;

    Logger logger = Logger.getLogger(PostAddressServiceImpl.class);

    /**
     * 查询邮寄地址
     * @param login
     * @return
     */
    @Override
    public PostaddressDto qryPostAddress(Login login) {
        String gwkh = login.getGwkh();//客人的购物卡号

        PostaddressDto paDto;
        try {
            paDto = paDao.selectByPrimaryKey(gwkh);

            if (paDto != null) {
                logger.info("取到顾客"+gwkh+"的地址信息"+paDto.getRec_provincename()+
                        paDto.getRec_cityname()+paDto.getRec_areaname()+paDto.getRec_townname()
                        +paDto.getRec_detailaddress());
            }else {
                logger.error("获取到的对象值为空");
                throw new HelpPostNotFoundException(errCode_5,errMsg_5);
            }
        } catch (Exception e) {
            logger.error(new ExceptionPrintMessage().errorTrackSpace(e));
            logger.error("邮寄地址管理表查询异常");
            throw new HelpPostNotFoundException(errCode14,errMsg14);
        }
        return paDto;
    }

    /**
     * 新增邮寄地址
     * @param ipdDto
     * @return
     */
    @Override
    public int insertPostAddress(PostaddressDto ipdDto) {
        int result;
        Map param = new HashMap<String,Integer>();
        try {
            //先判断是否可以修改
            param.put("i_gwkh",ipdDto.getGwkh());
            paDao.ismodifyaddress(param);

        } catch (Exception e) {
            logger.error(new ExceptionPrintMessage().errorTrackSpace(e));
            logger.error("判断是否可以写入地址表时存储过程返回异常");
            throw new HelpPostNotFoundException(errCode,errMsg);
        }
            String ret_flag = (String) param.get("ret_flag");
            if ("1".equals(ret_flag)) {
                logger.info("顾客"+ipdDto.getGwkh()+"存在未完结的邮寄申请，不能修改地址");
                throw new HelpPostNotFoundException(errCode14,errMsg14);
            }

        try {
            result = paDao.insert(ipdDto);
            if (result > 0) {
                logger.info("顾客"+ipdDto.getGwkh()+"地址新增成功");
            }
        } catch (Exception e) {
            logger.error(new ExceptionPrintMessage().errorTrackSpace(e));
            logger.error("邮寄地址管理表写入异常");
            throw new HelpPostNotFoundException(errCode,errMsg);
        }
        return result;
    }
}
