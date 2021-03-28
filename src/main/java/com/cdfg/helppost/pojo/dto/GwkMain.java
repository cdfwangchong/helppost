package com.cdfg.helppost.pojo.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * GWK_MAIN
 * @author 
 */
@Data
public class GwkMain implements Serializable {
    private String gmcardno;

    private String gmzjlb;

    private String gmzjno;

    private String gmstatus;

    private String gmgklb;

    private String gmname;

    private String gmnation;

    private String gmgender;

    private Date gmbirth;

    private String gmtelph;

    private String gmfzjg;

    private String gmdrass;

    private String gmismessage;

    private BigDecimal gmhisje;

    private Integer gmhiscs;

    private BigDecimal gmlimitje;

    private Short gmlimitcs;

    private Integer gmbsspjs;

    private String gmptbz;

    private String gmremark;

    private Date gmkkdate;

    private BigDecimal gmzk;

    private Date gmzkksrq;

    private Date gmzkjsrq;

    private String gmemail;

    private String gmljbh;

    private String gmljdd;

    private Date gmljrq;

    private String gmtr1;

    private String gmtr2;

    private String gmtr3;

    private Integer gmbnbsspjs;

    private Date gmgxrq;

    private String gmmdtype;

    private Date gmmdksrq;

    private Date gmmdjsrq;

    private String gmmdsource;

    private String gmtr4;

    private String gmtr5;

    private String gmtelmodifiable;

    private String gmismember;

    private String gmconfirmmember;

    private String gmisnewtel;

    private static final long serialVersionUID = 1L;
}