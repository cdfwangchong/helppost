package com.cdfg.helppost.dao;

import com.cdfg.helppost.pojo.dto.Userlist;
import org.springframework.stereotype.Repository;

@Repository
public interface UserlistDao {

    Userlist selectByPrimaryKey(String gwkh);

}