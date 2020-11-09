package com.cdfg.helppost.dao;

import com.cdfg.helppost.pojo.until.UserEntity;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface LoginDao {
    Map selectByPrimaryKey(Map<String,String> param);

    UserEntity qryUser(String gwkh);
}
