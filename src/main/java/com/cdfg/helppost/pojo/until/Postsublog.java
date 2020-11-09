package com.cdfg.helppost.pojo.until;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * POSTSUBLOG
 * @author 
 */
@Data
public class Postsublog implements Serializable {
    /**
     * 序号
     */
    private BigDecimal psseqno;

    /**
     * 购物卡号
     */
    private String psgwkh;

    /**
     * 收件人名称
     */
    private String psname;

    /**
     * 收件人电话号码
     */
    private String psphoneno;

    /**
     * 邮编
     */
    private String pspostcode;

    /**
     * 省
     */
    private String psprovincename;

    /**
     * 市
     */
    private String pscityname;

    /**
     * 区
     */
    private String psareaname;

    /**
     * 乡镇
     */
    private String pstownname;

    /**
     * 剩下详细地址
     */
    private String psdetailaddress;

    /**
     * 类型
     */
    private String pstype;

    /**
     * 门店
     */
    private String psmarket;

    /**
     * 提货单号
     */
    private String psxsdno;

    /**
     * 包裹数
     */
    private BigDecimal pspgkcnt;

    /**
     * 操作员
     */
    private String psopratid;

    /**
     * 是否确认
     */
    private String psisqr;

    /**
     * 确认时间
     */
    private Date psqrtime;

    /**
     * 申请时间
     */
    private Date pssqsj;

    private static final long serialVersionUID = 1L;
}