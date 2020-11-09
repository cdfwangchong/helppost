package com.cdfg.helppost.service;

import com.cdfg.helppost.pojo.dto.LeavedDto;
import com.cdfg.helppost.pojo.dto.XsdnoDto;
import com.cdfg.helppost.pojo.until.CustAddrlistEntity;
import com.cdfg.helppost.pojo.until.Login;

import java.util.List;

public interface QryBillIsPostService {

    XsdnoDto qryNotPostBill(Login login,String worknumber);

    List<CustAddrlistEntity> qryPostBill(Login login,String worknumber);

    String updateLeaved(LeavedDto leavedDto, String worknumber);

}
