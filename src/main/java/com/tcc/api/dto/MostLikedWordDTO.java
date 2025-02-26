package com.tcc.api.dto;

public class MostLikedWordDTO {

    private String word;
    private Long likeCount;

    // Construtores, getters e setters

    public MostLikedWordDTO(String word, Long likeCount) {
        this.word = word;
        this.likeCount = likeCount;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public Long getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Long likeCount) {
        this.likeCount = likeCount;
    }
}

