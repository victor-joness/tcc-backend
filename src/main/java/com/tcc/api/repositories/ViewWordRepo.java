package com.tcc.api.repositories;

import com.tcc.api.models.ViewWord;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ViewWordRepo extends JpaRepository<ViewWord, Long> {
    List<ViewWord> findByUserIdOrderByDateAsc(Long userId);

    Optional<ViewWord> findByUserIdAndWordId(Long userId, Long wordId);

    @Query("SELECT vw.word.id FROM ViewWord vw WHERE vw.user.id = :userId ORDER BY vw.date DESC")
    List<Long> findViewedWordIdsByUserId(@Param("userId") Long userId, Pageable pageable);
}
