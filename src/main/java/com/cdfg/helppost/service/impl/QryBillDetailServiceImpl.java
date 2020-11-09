package com.cdfg.helppost.service.impl;

import com.cdfg.helppost.dao.SellDetailDao;
import com.cdfg.helppost.pojo.dto.PickBillDto;
import com.cdfg.helppost.pojo.dto.PickNumDto;
import com.cdfg.helppost.service.QryBillDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QryBillDetailServiceImpl implements QryBillDetailService {
    @Autowired
    SellDetailDao selldetaildao;

    @Override
    public List<PickBillDto> getselldetail(PickNumDto picknumdto) {
        return selldetaildao.QrySellDetail(picknumdto);
    }
}
