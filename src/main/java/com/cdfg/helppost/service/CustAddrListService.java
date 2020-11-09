package com.cdfg.helppost.service;


import com.cdfg.helppost.pojo.dto.InsertCustAddrAndListDto;

public interface CustAddrListService {

    String insertCustAddrList(InsertCustAddrAndListDto ica,String worknumber);
}
