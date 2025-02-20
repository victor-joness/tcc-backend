package com.tcc.api.config.auth.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Setter
@Getter
public class SignUpDTO {
    private String name;
    private String email;
    private String password;
    private String passwordConfirmation;
}
