package com.tcc.api.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SaveVariationDTO {
    private Long id;
    private String name;
    private String description;
    private String video;
    private Long wordId;
}
