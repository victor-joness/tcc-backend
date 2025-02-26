package com.tcc.api.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SaveInterpreterDTO {
    private Long id;
    private String name;
    private String email;
}
