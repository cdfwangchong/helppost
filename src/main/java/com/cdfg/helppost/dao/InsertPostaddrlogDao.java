package com.cdfg.helppost.dao;

import com.cdfg.helppost.pojo.dto.PostaddressDto;
import org.springframework.stereotype.Repository;


@Repository
public interface InsertPostaddrlogDao {

    int insertPostAddrLog(PostaddressDto record);
}
