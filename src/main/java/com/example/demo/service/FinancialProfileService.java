package com.example.demo.service;

import com.example.demo.entity.FinancialProfile;
import java.util.List;

public interface FinancialProfileService {

    FinancialProfile getByUserId(Long userId);

    FinancialProfile save(FinancialProfile profile);

    List<FinancialProfile> getAll();
}
