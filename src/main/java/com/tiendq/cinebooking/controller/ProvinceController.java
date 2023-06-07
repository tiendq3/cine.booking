package com.tiendq.cinebooking.controller;

import com.tiendq.cinebooking.model.entities.Province;
import com.tiendq.cinebooking.service.ProvinceService;
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
public class ProvinceController {

    private final ProvinceService provinceService;

    @GetMapping("/provinces")
    public ResponseEntity<List<Province>> getAllProvince() {
        return ResponseEntity.ok(provinceService.getAllProvince());
    }

    @GetMapping("/provinces/{id}")
    public ResponseEntity<Province> getProvinceById(@PathVariable Long id) {
        return ResponseEntity.ok(provinceService.getProvinceById(id));
    }

    @PostMapping("/management/province")
    @ResponseStatus(HttpStatus.CREATED)
    public void insertProvince(@RequestBody Province province) {
        provinceService.insertProvince(province);
    }

    @PatchMapping("/management/province/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateProvince(@PathVariable Long id, Province newProvince) {
        provinceService.updateProvince(id, newProvince);
    }

    @DeleteMapping("/management/province/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProvince(@PathVariable Long id) {
        provinceService.deleteProvince(id);
    }
}
