package com.cdfg.helppost.dao;

import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface QrytraceDao {
    Map QryWaybilltrace(Map param);
}
