package com.tiendq.cinebooking.service.other;

import com.tiendq.cinebooking.exception.NotFoundException;
import com.tiendq.cinebooking.model.dtos.UserDTO;
import com.tiendq.cinebooking.model.entities.User;
import com.tiendq.cinebooking.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class UserResourceService {

    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

    public Page<UserDTO> getAllUser(int page, int size, String[] properties, Sort.Direction sort) {
        log.warn("[SERVICE] - GET ALL USER");
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.ASC, properties);
        if (sort.isDescending()) {
            pageable = PageRequest.of(page, size, Sort.Direction.DESC, properties);
        }
        Page<User> userDTOS = userRepository.findAll(pageable);
        return userDTOS.map(user -> modelMapper.map(user, UserDTO.class));
    }

    public UserDTO getUserById(Long id) {
        log.warn("[SERVICE] - GET USER BY ID");
        User user = userRepository.findById(id).orElse(null);
        if (user == null) throw new NotFoundException("Not found user by " + id);
        return modelMapper.map(user, UserDTO.class);
    }

    public void updateProduct(Long id, UserDTO userDTO) {

    }

    public void deleteUser(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) throw new NotFoundException("not found product by " + id);
        userRepository.delete(user);
    }

    public UserDTO currentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) return null;
        User user = userRepository.findUserByEmail(authentication.getName());
        if (user == null) return null;
        return modelMapper.map(user, UserDTO.class);
    }
}
