package com.tiendq.cinebooking.model.request;

import com.tiendq.cinebooking.config.AppConstants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Login {

    @Email(message = "Username must be a email")
    private String email;

    @Pattern(regexp = AppConstants.PASSWORD_REGEX,
            message = "Password must contain letters or numbers, at least 6 characters")
    private String password;
}
