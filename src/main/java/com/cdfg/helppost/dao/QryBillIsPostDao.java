package com.cdfg.helppost.dao;

import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface QryBillIsPostDao {
    Map qryNotPostBill(Map<String, String> param);

    Map qryPostBill(Map<String, String> param);

    Map updateLeaved(Map<String, String> param);
}
