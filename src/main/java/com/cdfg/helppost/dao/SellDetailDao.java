package com.cdfg.helppost.dao;

import com.cdfg.helppost.pojo.dto.PickBillDto;
import com.cdfg.helppost.pojo.dto.PickNumDto;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SellDetailDao {
    List<PickBillDto> QrySellDetail(PickNumDto picknumdto);
}
