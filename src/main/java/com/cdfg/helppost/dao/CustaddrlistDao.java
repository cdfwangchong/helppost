package com.cdfg.helppost.dao;

import com.cdfg.helppost.pojo.dto.Custaddrlist;
import com.cdfg.helppost.pojo.dto.InsertCustAddrDto;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface CustaddrlistDao {
    int updateByPrimaryKey(List<String> record);

    int insert(List<InsertCustAddrDto> record);

    Custaddrlist selectByPrimaryKey(BigDecimal seqno);

    int nextvalKey();
}