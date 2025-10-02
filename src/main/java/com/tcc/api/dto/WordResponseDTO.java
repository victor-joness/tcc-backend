package com.tcc.api.dto;

import java.util.List;

import com.tcc.api.models.Category;
import com.tcc.api.models.Variation;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WordResponseDTO {
    private Long id;
    private String word;
    private String description;
    private String video;
    private String status;
    private String modulo;
    private Long request_word_id;
    private Category category;
    private List<Variation> variations;

    private String interpreterName;
}