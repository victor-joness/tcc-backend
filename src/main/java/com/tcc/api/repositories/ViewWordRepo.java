package com.tcc.api.repositories;

import com.tcc.api.models.Interpreter;
import com.tcc.api.models.ViewWord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ViewWordRepo extends JpaRepository<ViewWord, Long> {
    List<ViewWord> findByUserIdOrderByDateAsc(Long userId);

    Optional<ViewWord> findByUserIdAndWordId(Long userId, Long wordId);
}

