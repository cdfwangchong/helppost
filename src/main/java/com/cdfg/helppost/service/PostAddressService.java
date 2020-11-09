package com.cdfg.helppost.service;

import com.cdfg.helppost.pojo.dto.PostaddressDto;
import com.cdfg.helppost.pojo.until.Login;

public interface PostAddressService {
    public PostaddressDto qryPostAddress(Login login);

    public int insertPostAddress(PostaddressDto ipaDto);
}
