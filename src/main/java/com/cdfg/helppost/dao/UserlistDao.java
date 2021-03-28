package com.cdfg.helppost.dao;

import com.cdfg.helppost.pojo.dto.GwkMain;
import org.springframework.stereotype.Repository;

@Repository
public interface UserlistDao {

    GwkMain selectByPrimaryKey(String gwkh);

}