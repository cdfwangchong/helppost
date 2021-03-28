package com.cdfg.helppost.service.impl;

import cn.cdfg.exceptionHandle.ExceptionPrintMessage;
import cn.cdfg.exceptionHandle.HelpPostNotFoundException;
import com.cdfg.helppost.dao.PostaddressDao;
import com.cdfg.helppost.dao.UserlistDao;
import com.cdfg.helppost.pojo.dto.GwkMain;
import com.cdfg.helppost.pojo.dto.PostaddressDto;

import com.cdfg.helppost.pojo.until.Login;
import com.cdfg.helppost.service.PostAddressService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import static com.cdfg.helppost.pojo.until.Constant.*;

@Service
public class PostAddressServiceImpl implements PostAddressService {

    @Autowired
    PostaddressDao paDao;

    @Autowired
    UserlistDao ulDao;

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
            throw new HelpPostNotFoundException(errCode_18,errMsg_18);
        }
        return paDto;
    }

    /**
     * 新增邮寄地址
     * @param ipdDto
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout = 30,rollbackFor = Exception.class)
    public int insertPostAddress(PostaddressDto ipdDto) {
        int result;
        //查出顾客的购物卡号
        GwkMain ul;
        try {
            ul = ulDao.selectByPrimaryKey(ipdDto.getGwkh());
        } catch (Exception e) {
            logger.error(ipdDto.getGwkh()+"未注册");
            throw new HelpPostNotFoundException(errCode7,ipdDto.getGwkh()+errMsg7);
        }
        String gwkh = ul.getGmcardno();
        ipdDto.setGwkh(gwkh);
        String recname = ul.getGmname();//EOP用户表中的顾客名称
        String crname = ipdDto.getRec_name();//接口传入的顾客名称
        logger.info(gwkh+"EOP用户表中的顾客名称"+recname);
        logger.info(gwkh+"接口传入的收件人名称"+crname);
        logger.info("新增顾客地址前查出客人购物卡号"+gwkh);
        if ("海南省".equals(ipdDto.getRec_provincename())) {
            logger.info("收件地址必须是岛外");
            throw new HelpPostNotFoundException(errCode_8,errMsg_8);
        }
        if (!recname.equals(crname)) {
            logger.info("收件必须是顾客本人");
            throw new HelpPostNotFoundException(errCode_7,errMsg_7);
        }
        try {
            result = paDao.insert(ipdDto);
            if (result > 0) {
                logger.info("顾客"+ipdDto.getGwkh()+"地址新增成功");
            }
        } catch (Exception e) {
            logger.error(new ExceptionPrintMessage().errorTrackSpace(e));
            logger.error("邮寄地址管理表写入异常");
            throw new HelpPostNotFoundException(errCode_6,errMsg_6);
        }
        return result;
    }
//    @Override
//    public int updatePostAddress(PostaddressDto ipaDto) {
//        //查出顾客的购物卡号
//        Userlist ul;
//        try {
//            ul = ulDao.selectByPrimaryKey(ipaDto.getGwkh());
//        } catch (Exception e) {
//            logger.error(ipaDto.getGwkh()+"未注册");
//            throw new HelpPostNotFoundException(errCode7,ipaDto.getGwkh()+errMsg7);
//        }
//        String gwkh = ul.getIdseq();
//        ipaDto.setGwkh(gwkh);
//        logger.info("更新顾客地址前查出客人购物卡号"+ul.getIdseq());
//        int result;
//        String recname = ul.getName();//EOP用户表中的顾客名称
//        String crname = ipaDto.getRec_name();//接口传入的顾客名称
//        logger.info(gwkh+"EOP用户表中的顾客名称"+recname);
//        logger.info(gwkh+"接口传入的收件人名称"+crname);
//        if ("海南省".equals(ipaDto.getRec_provincename())) {
//            logger.info(gwkh+"收件地址必须是岛外");
//            throw new HelpPostNotFoundException(errCode_8,errMsg_8);
//        }
//        if (!recname.equals(crname)) {
//            logger.info(gwkh+"收件必须是顾客本人");
//            throw new HelpPostNotFoundException(errCode_7,errMsg_7);
//        }
//        try {
//            result = paDao.updateByPrimaryKey(ipaDto);
//            if (result > 0) {
//                logger.info(gwkh+"顾客"+ul.getName()+"地址更新成功");
//            }
//        } catch (Exception e) {
//            logger.error(gwkh+"邮寄地址管理表更新异常");
//            throw new HelpPostNotFoundException(errCode,errMsg);
//        }
//        return result;
//    }
}
