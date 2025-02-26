package com.tcc.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class LoginDTO {

    @NotNull(message = "O email é obrigatório.")
    @Email(message = "Email deve ser válido.")
    private String email;

    @NotNull(message = "A senha é obrigatório.")
    @Size(min = 7, message = "A senha deve ter pelo menos 7 caracteres.")
    private String password;

    public String getEmail() {
        return this.email;
    }
}
