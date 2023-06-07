package com.tiendq.cinebooking.model.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
public class MailMessage {

    private String email;

    private String title;

    private String content;
}
