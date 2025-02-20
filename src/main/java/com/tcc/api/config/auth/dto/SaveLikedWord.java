package com.tcc.api.config.auth.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@RequiredArgsConstructor
public class SaveLikedWord {
    private Long userId;
    private Long wordId;
}