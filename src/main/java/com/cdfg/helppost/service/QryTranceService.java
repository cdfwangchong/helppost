package com.cdfg.helppost.service;

import com.cdfg.helppost.pojo.dto.WaybilltraceDto;

import java.util.List;

public interface QryTranceService {
    List<WaybilltraceDto> QryWaybilltrace(String trance);
}
