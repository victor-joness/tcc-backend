package com.tcc.api.models;
import com.tcc.api.enums.Role;
import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "users")
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String phone;
    private String code;
    private Boolean verified;
    private String password;
    private String photo;

    @Enumerated(EnumType.STRING)
    private Role role;
}

