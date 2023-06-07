package com.tiendq.cinebooking.controller;

import com.tiendq.cinebooking.model.dtos.VoucherDTO;
import com.tiendq.cinebooking.service.VoucherService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/api/v1")
public class VoucherController {

    private final VoucherService voucherService;

    @GetMapping("/vouchers/{id}")
    public ResponseEntity<VoucherDTO> getVoucherById(@PathVariable Long id) {
        log.warn("[CONTROLLER] - GET VOUCHER BY ID: " + id);
        return ResponseEntity.ok(voucherService.getVoucherById(id));
    }

    @GetMapping("/vouchers")
    public ResponseEntity<Page<VoucherDTO>> getAllVoucher() {
        log.warn("[CONTROLLER] - GET ALL VOUCHER");
        return ResponseEntity.ok(voucherService.getAllVoucher());
    }

    @PostMapping("/management/vouchers")
    @ResponseStatus(HttpStatus.CREATED)
    public void insertVoucher(@RequestBody VoucherDTO voucherDTO) {
        log.warn("[CONTROLLER] - INSERT NEW VOUCHER: " + voucherDTO);
        voucherService.insertVoucher(voucherDTO);
    }

    @PatchMapping("/management/vouchers/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateVoucher(@PathVariable Long id, @RequestBody VoucherDTO voucherDTO) {
        log.warn("[CONTROLLER] - UPDATE VOUCHER: " + voucherDTO);
        voucherService.updateVoucher(id, voucherDTO);
    }

    @DeleteMapping("/management/vouchers/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteVoucher(@PathVariable Long id) {
        log.warn("[CONTROLLER] - DELETE VOUCHER BY ID:" + id);
        voucherService.deleteVoucher(id);
    }
}
