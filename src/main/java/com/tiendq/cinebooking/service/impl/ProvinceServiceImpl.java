package com.tiendq.cinebooking.service.impl;

import com.tiendq.cinebooking.exception.NotFoundException;
import com.tiendq.cinebooking.model.entities.Province;
import com.tiendq.cinebooking.repository.ProvinceRepository;
import com.tiendq.cinebooking.service.ProvinceService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class ProvinceServiceImpl implements ProvinceService {

    private final ProvinceRepository provinceRepository;

    @Override
    public List<Province> getAllProvince() {
        return provinceRepository.findAll();
    }

    @Override
    public Province getProvinceById(Long id) {
        return provinceRepository.findById(id).orElse(null);
    }

    @Override
    public void insertProvince(Province province) {
        provinceRepository.save(province);
    }

    @Override
    public void updateProvince(Long id, Province newProvince) {
        Province province = provinceRepository.findById(id).orElse(null);
        if (province == null) throw new NotFoundException("not found province by id: " + id);
        province.setType(newProvince.getType());
        province.setName(newProvince.getName());
        province.setDistricts(newProvince.getDistricts());
    }

    @Override
    public void deleteProvince(Long id) {
        Province province = provinceRepository.findById(id).orElse(null);
        if (province == null) throw new NotFoundException("not found province by id: " + id);
        provinceRepository.delete(province);
    }
}
