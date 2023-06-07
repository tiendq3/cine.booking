package com.tiendq.cinebooking.service.impl;

import com.tiendq.cinebooking.exception.NotFoundException;
import com.tiendq.cinebooking.model.entities.District;
import com.tiendq.cinebooking.repository.DistrictRepository;
import com.tiendq.cinebooking.service.DistrictService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class DistrictServiceImpl implements DistrictService {

    private final DistrictRepository districtRepository;

    @Override
    public List<District> getAllDistrict() {
        return districtRepository.findAll();
    }

    @Override
    public District getDistrictById(Long id) {
        return districtRepository.findById(id).orElse(null);
    }

    @Override
    public void insertDistrict(District district) {
        districtRepository.save(district);
    }

    @Override
    public void updateDistrict(Long id, District newDistrict) {
        District district = districtRepository.findById(id).orElse(null);
        if (district == null) throw new NotFoundException("not found province by id: " + id);
        district.setType(newDistrict.getType());
        district.setName(newDistrict.getName());
    }

    @Override
    public void deleteDistrict(Long id) {
        District district = districtRepository.findById(id).orElse(null);
        if (district == null) throw new NotFoundException("not found province by id: " + id);
        districtRepository.delete(district);
    }
}
