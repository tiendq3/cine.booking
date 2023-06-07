package com.tiendq.cinebooking.controller;

import com.tiendq.cinebooking.model.entities.District;
import com.tiendq.cinebooking.service.DistrictService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/api/v1")
public class DistrictController {
    private final DistrictService districtService;

    @GetMapping("/districts")
    public ResponseEntity<List<District>> getAllDistrict() {
        return ResponseEntity.ok(districtService.getAllDistrict());
    }

    @GetMapping("/districts/{id}")
    public ResponseEntity<District> getDistrictById(@PathVariable Long id) {
        return ResponseEntity.ok(districtService.getDistrictById(id));
    }

    @PostMapping("/management/districts")
    @ResponseStatus(HttpStatus.CREATED)
    public void insertDistrict(@RequestBody District district) {
        districtService.insertDistrict(district);
    }

    @PatchMapping("/management/districts/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateDistrict(@PathVariable Long id, District newDistrict) {
        districtService.updateDistrict(id, newDistrict);
    }

    @DeleteMapping("/management/districts/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDistrict(@PathVariable Long id) {
        districtService.deleteDistrict(id);
    }
}
