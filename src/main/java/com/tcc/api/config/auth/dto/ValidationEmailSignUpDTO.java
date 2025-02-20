package com.tcc.api.config.auth.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class ValidationEmailSignUpDTO {
    private String email;
    private String code;
}
