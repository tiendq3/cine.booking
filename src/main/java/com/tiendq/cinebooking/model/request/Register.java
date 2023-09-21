package com.tiendq.cinebooking.model.request;

import com.tiendq.cinebooking.config.AppConstants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Register extends Login {
    @NotEmpty
    private String otp;
}
