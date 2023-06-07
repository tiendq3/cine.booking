package com.tiendq.cinebooking.service;

import com.tiendq.cinebooking.model.request.Login;
import com.tiendq.cinebooking.model.request.Register;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public interface UserService {

    @Transactional
    void register(Register register);

    void login(Login login);

    void changePassword();
}
