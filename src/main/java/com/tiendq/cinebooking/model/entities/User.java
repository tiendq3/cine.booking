package com.tiendq.cinebooking.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tiendq.cinebooking.config.AppConstants;
import com.tiendq.cinebooking.model.enums.ERole;
import com.tiendq.cinebooking.model.enums.EStatusAccount;
import lombok.*;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.io.Serial;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Email(message = "Email should be valid")
    @Column(name = "email", unique = true, length = 50)
    private String email;

    @JsonIgnore
    @NotEmpty(message = "password is an encrypted string, cannot be empty")
    private String password;

    @Pattern(regexp = AppConstants.PHONE_REGEX,
            message = "Please provide a valid phone number")
    @Nullable
    private String phone;

    private String name;

    private String gender;

    private Instant birthday;

    private String avatar;

    private String address;

    private String activeCode;

    @Enumerated(EnumType.STRING)
    private EStatusAccount status;

    private Timestamp createdAt;

    private Timestamp updatedAt;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<ERole> roles;
}
