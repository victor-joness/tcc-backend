package com.tcc.api.dto;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SaveWordDTO {
    private Long id;
    private String word;
    private String description;
    private String video;
    private String status;
    private String modulo;
    private Long categoryId;
}
