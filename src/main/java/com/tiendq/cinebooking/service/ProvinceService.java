package com.tiendq.cinebooking.service;

import com.tiendq.cinebooking.model.entities.Province;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProvinceService {
    List<Province> getAllProvince();

    Province getProvinceById(Long id);

    void insertProvince(Province province);

    void updateProvince(Long id, Province newProvince);

    void deleteProvince(Long id);
}
