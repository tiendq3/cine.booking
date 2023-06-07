package com.tiendq.cinebooking.model.dtos;

import com.tiendq.cinebooking.config.AppConstants;
import com.tiendq.cinebooking.model.enums.ERole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.time.Instant;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private Long id;

    private String username;

    @Email(message = "Email should be valid")
    @NotEmpty
    private String email;

    @Pattern(regexp = AppConstants.PHONE_REGEX,
            message = "Please provide a valid phone number")
    @Nullable
    private String phone;

    private String name;

    private String gender;

    private Instant birthday;

    private String avatar;

    private String address;

    private Set<ERole> roles;
}
