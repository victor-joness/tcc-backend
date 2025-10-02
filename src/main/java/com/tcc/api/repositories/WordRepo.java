package com.tcc.api.repositories;

import com.tcc.api.models.Word;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WordRepo extends JpaRepository<com.tcc.api.models.Word, Long> {
    List<Word> findByCategoryIdAndStatus(Long categoryId, String status);
    List<Word> findByStatus(String status);
    List<Word> findByIdIn(List<Long> viewedWordIds);
}

