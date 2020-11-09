package com.cdfg.helppost.service;

import com.cdfg.helppost.pojo.dto.PickBillDto;
import com.cdfg.helppost.pojo.dto.PickNumDto;

import java.util.List;

public interface QryBillDetailService {

    List<PickBillDto> getselldetail(PickNumDto picknumdto);
}
