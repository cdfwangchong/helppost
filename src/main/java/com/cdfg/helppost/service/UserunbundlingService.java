package com.cdfg.helppost.service;

import com.cdfg.helppost.pojo.dto.UnbundlingDto;

import java.util.Map;

public interface UserunbundlingService {
    Map<String, String> userUnbundling(UnbundlingDto unbundlingDto,String worknumber);
}
