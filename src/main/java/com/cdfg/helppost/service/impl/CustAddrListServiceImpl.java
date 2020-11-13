package com.cdfg.helppost.service.impl;

import cn.cdfg.exceptionHandle.ExceptionPrintMessage;
import cn.cdfg.exceptionHandle.HelpPostNotFoundException;
import com.cdfg.helppost.dao.CheckCanclePostDao;
import com.cdfg.helppost.dao.CustaddrlistDao;
import com.cdfg.helppost.dao.InsertPostSubDao;
import com.cdfg.helppost.pojo.dto.InsertCustAddrAndListDto;
import com.cdfg.helppost.pojo.dto.InsertCustAddrDto;
import com.cdfg.helppost.pojo.until.BillEntity;
import com.cdfg.helppost.pojo.until.PostLogEntity;
import com.cdfg.helppost.service.CustAddrListService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static com.cdfg.helppost.pojo.until.Constant.*;


@Service
public class CustAddrListServiceImpl implements CustAddrListService {
    @Autowired
    CustaddrlistDao clDao;

    @Autowired
    CheckCanclePostDao ccpDao;

    @Autowired
    InsertPostSubDao ipsDao;

    Logger logger = Logger.getLogger(CustAddrListServiceImpl.class);

    /**
     * 商品邮寄接口
     * @param ica
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout = 30,rollbackFor = Exception.class)
    public String insertCustAddrList(InsertCustAddrAndListDto ica,String worknumber) {
        //当取消邮寄申请时，判断是否符合条件取消
        Map param = new HashMap<String,Integer>();
        if ("2".equals(ica.getType())) {
            param.put("seq_no",ica.getSeq_no());
            String ret_flag;
            try {
                ccpDao.isCanclePost(param);
                ret_flag = (String) param.get("ret_flag");
            } catch (Exception e) {
                logger.error(new ExceptionPrintMessage().errorTrackSpace(e));
                logger.error("取消邮寄申请时，判断是否符合条件取消的存储过程返回异常");
                throw new HelpPostNotFoundException(errCode_3,errMsg_3);
            }
            if ("2001".equals(ret_flag)) {
                logger.info("离航班起飞时间不足48小时，不能取消");
                throw new HelpPostNotFoundException(errCode_12,errMsg_12);
            }
            if ("2002".equals(ret_flag)) {
                logger.info("该邮寄申请单含状态为已提货的提货单，不能取消");
                throw new HelpPostNotFoundException(errCode_19,errMsg_19);
            }
        }

        List<BillEntity> PIlist = ica.getOrderList();
        Map<String,String> Markmap = new HashMap<String,String>();
        Markmap.put("6868",null);
        Markmap.put("6921",null);
        Markmap.put("6922",null);
        Markmap.put("6127",null);

        //记录包裹数
        Map<String, Integer> pcgCntMap = new HashMap<String,Integer>();
        pcgCntMap.put("6868",0);
        pcgCntMap.put("6921",0);
        pcgCntMap.put("6922",0);
        pcgCntMap.put("6127",0);

        int pcgCntsy= 0;
        int pcgCntba= 0;
        int pcgCnthk= 0;
        int pcgCntml= 0;
        List<InsertCustAddrDto> icadList = new ArrayList<InsertCustAddrDto>();
        List<PostLogEntity> pleList = new ArrayList<PostLogEntity>();

        String seqnoStr = null;
        try {
            //拼接各门店的提货单号
            if (PIlist != null) {
                for (int i = 0; i < PIlist.size(); i++) {
                    BillEntity pi = PIlist.get(i);
                    if ("6868".equals(pi.getMarket()) || "6874".equals(pi.getMarket())) {
                        String billno = Markmap.get("6868");
                        if (billno == null) {
                            Markmap.put("6868",pi.getShxsdno());
                        }else {
                            billno = pi.getShxsdno()+"|"+billno;
                            Markmap.put("6868",billno);
                        }
                        pcgCntsy++;
                        pcgCntMap.put("6868",pcgCntsy);

                    } else if("6921".equals(pi.getMarket())) {
                        String billno = Markmap.get("6921");
                        if (billno == null) {
                            Markmap.put("6921",pi.getShxsdno());
                        }else {
                            billno = pi.getShxsdno()+"|"+billno;
                            Markmap.put("6921",billno);
                        }
                        pcgCntba++;
                        pcgCntMap.put("6921",pcgCntba);

                    }else if ("6922".equals(pi.getMarket())) {
                        String billno = Markmap.get("6922");
                        if (billno == null) {
                            Markmap.put("6922",pi.getShxsdno());
                        }else {
                            billno = pi.getShxsdno()+"|"+billno;
                            Markmap.put("6922",billno);
                        }
                        pcgCnthk++;
                        pcgCntMap.put("6922",pcgCnthk);

                    }else if ("6127".equals(pi.getMarket())){
                        String billno = Markmap.get("6127");
                        if (billno == null) {
                            Markmap.put("6127",pi.getShxsdno());
                        }else {
                            billno = pi.getShxsdno()+"|"+billno;
                            Markmap.put("6127",billno);
                        }
                        pcgCntml++;
                        pcgCntMap.put("6127",pcgCntml);
                    }else {
                        throw new HelpPostNotFoundException(errCode_2,errMsg_2);
                    }
                }
            }
            //查出顾客的购物卡号
            String gwkh = ica.getGwkh();//客人的购物卡号
//            String telphno = ul.getTelphno();//客人的电话号码
//            if (!telphno.equals(ica.getRec_phoneno())) {
//                throw new SelfMailNotFoundException(errCode10,errMsg10);
//            }

            //将有值的Markmap存入List
            for (Map.Entry<String,String> entry : Markmap.entrySet()) {
                if (entry.getValue() != null) {
                    int seqno = clDao.nextvalKey();
                    System.out.println(seqno);
                    InsertCustAddrDto icaDto = new InsertCustAddrDto();
                    icaDto.setRec_name(ica.getRec_name());
                    icaDto.setRec_phoneno(ica.getRec_phoneno());
                    icaDto.setRec_postcode(ica.getRec_postcode());
                    icaDto.setRec_provincename(ica.getRec_provincename());
                    icaDto.setRec_cityname(ica.getRec_cityname());
                    icaDto.setRec_areaname(ica.getRec_areaname());
                    icaDto.setRec_townname(ica.getRec_townname());
                    icaDto.setRec_detailaddress(ica.getRec_detailaddress());
                    icaDto.setType(ica.getType());
                    icaDto.setMarket(entry.getKey());
                    icaDto.setRec_xsdno(entry.getValue());
                    icaDto.setRec_pkgcnt(pcgCntMap.get(entry.getKey()));
                    icaDto.setGwkh(gwkh);
                    icaDto.setFlag("0");
                    icaDto.setSeqno_c(ica.getSeq_no());
                    icaDto.setSeqno(seqno);
                    icadList.add(icaDto);

                    PostLogEntity plEntity = new PostLogEntity();
                    plEntity.setRec_name(ica.getRec_name());
                    plEntity.setRec_phoneno(ica.getRec_phoneno());
                    plEntity.setRec_postcode(ica.getRec_postcode());
                    plEntity.setRec_provincename(ica.getRec_provincename());
                    plEntity.setRec_cityname(ica.getRec_cityname());
                    plEntity.setRec_areaname(ica.getRec_areaname());
                    plEntity.setRec_townname(ica.getRec_townname());
                    plEntity.setRec_detailaddress(ica.getRec_detailaddress());
                    plEntity.setType(ica.getType());
                    plEntity.setMarket(entry.getKey());
                    plEntity.setRec_xsdno(entry.getValue());
                    plEntity.setRec_pkgcnt(pcgCntMap.get(entry.getKey()));
                    plEntity.setGwkh(gwkh);
                    plEntity.setSeqno(seqno);
                    plEntity.setWorknumber(worknumber);
                    plEntity.setIsqr("N");
                    plEntity.setSqsj(new Date());
                    pleList.add(plEntity);

//                    ipsDao.insert(plEntity);

                    //将客人的邮寄信息写入日志
                    String address = ica.getRec_provincename()+ica.getRec_cityname()+ica.getRec_areaname()+ica.getRec_townname()+ica.getRec_detailaddress();
                    logger.info("顾客："+ica.getRec_name()+"电话："+ica.getRec_phoneno()+"地址："+address+"邮寄的提货单"+entry.getValue());

                    if (seqnoStr == null) {
                        seqnoStr = String.valueOf(seqno);
                    } else {
                        seqnoStr = seqnoStr+"|"+String.valueOf(seqno);
                    }
                }
            }
        } catch (Exception e) {
            logger.error(new ExceptionPrintMessage().errorTrackSpace(e));
            logger.error("邮寄信息写入异常");
            throw new HelpPostNotFoundException(errCode_3,errMsg_3);
        }

        int index = 0;
        try {
            index = ipsDao.insertPostSubLog(pleList);
        } catch (Exception e) {
            logger.error(new ExceptionPrintMessage().errorTrackSpace(e));
            logger.error("没有正确写入邮寄提交日志表");
            throw new HelpPostNotFoundException(errCode_6,errMsg_6);
        }

        if (index != icadList.size()) {
            logger.error("没有正确写入邮寄提交日志表");
            throw new HelpPostNotFoundException(errCode_6,errMsg_6);
        }

        int ret = 0;
        try {
            ret = clDao.insert(icadList);
        } catch (Exception e) {
            logger.error(new ExceptionPrintMessage().errorTrackSpace(e));
            logger.error("List中的数据没有正确写入顾客地址列表");
            throw new HelpPostNotFoundException(errCode_6,errMsg_6);
        }
        if (ret != icadList.size()) {
            logger.error("List中的数据没有正确写入顾客地址列表");
            throw new HelpPostNotFoundException(errCode_6,errMsg_6);
        }
        return seqnoStr;
    }
}
