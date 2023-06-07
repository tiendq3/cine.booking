package com.tiendq.cinebooking.controller;

import com.tiendq.cinebooking.model.dtos.UserDTO;
import com.tiendq.cinebooking.service.other.UserResourceService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@AllArgsConstructor
@Slf4j
public class UserResource {

    private final UserResourceService userResourceService;

    @GetMapping("/current")
    public ResponseEntity<UserDTO> currentUser() {
        log.warn("[CONTROLLER] - GET CURRENT USER: " + userResourceService.currentUser());
        return ResponseEntity.ok(userResourceService.currentUser());
    }

    @GetMapping()
    public ResponseEntity<Page<UserDTO>> getAll(@RequestParam(defaultValue = "0") int page,
                                                @RequestParam(defaultValue = "30") int size,
                                                @RequestParam(defaultValue = "name") String[] properties,
                                                @RequestParam(defaultValue = "ASC") Sort.Direction sort) {
        log.warn("[CONTROLLER] - GET ALL USER REQUEST");
        return ResponseEntity.ok(userResourceService.getAllUser(page, size, properties, sort));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        log.warn("[CONTROLLER] - GET USER BY ID: " + id);
        return ResponseEntity.ok(userResourceService.getUserById(id));
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        log.warn("[CONTROLLER] - UPDATE USER: " + userDTO);
        userResourceService.updateProduct(id, userDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long id) {
        log.warn("[CONTROLLER] - DELETE USER BY ID: " + id);
        userResourceService.deleteUser(id);
    }
}
