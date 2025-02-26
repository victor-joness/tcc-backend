package com.tcc.api.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SaveNewsDTO {
    private Long id;
    private String title;
    private String resume;
    private String link;
}
