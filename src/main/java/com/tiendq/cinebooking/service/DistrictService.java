package com.tiendq.cinebooking.service;

import com.tiendq.cinebooking.model.entities.District;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DistrictService {
    List<District> getAllDistrict();

    District getDistrictById(Long id);

    void insertDistrict(District district);

    void updateDistrict(Long id, District newDistrict);

    void deleteDistrict(Long id);
}
