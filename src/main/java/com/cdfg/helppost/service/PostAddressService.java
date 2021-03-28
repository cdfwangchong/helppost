package com.cdfg.helppost.service;

import com.cdfg.helppost.pojo.dto.PostaddressDto;
import com.cdfg.helppost.pojo.until.Login;

public interface PostAddressService {
    PostaddressDto qryPostAddress(Login login);

    int insertPostAddress(PostaddressDto ipaDto);

//    int updatePostAddress(PostaddressDto ipaDto);
}
