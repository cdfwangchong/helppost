package com.cdfg.helppost.dao;

import com.cdfg.helppost.pojo.dto.PostaddressDto;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface PostaddressDao {
    int deleteByPrimaryKey(String gwkh);

    int insert(PostaddressDto record);

    PostaddressDto selectByPrimaryKey(String gwkh);

//    int updateByPrimaryKey(PostaddressDto record);

    Map ismodifyaddress(Map<String, String> param);

    int nextvalKey();
}