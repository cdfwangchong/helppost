package com.cdfg.helppost.dao;

import com.cdfg.helppost.pojo.dto.InsertCustAddrDto;
import com.cdfg.helppost.pojo.until.PostLogEntity;
import com.cdfg.helppost.pojo.until.Postsublog;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InsertPostSubDao {

    int insertPostSubLog(List<PostLogEntity> record);

    int insert(List<PostLogEntity> record);
}
