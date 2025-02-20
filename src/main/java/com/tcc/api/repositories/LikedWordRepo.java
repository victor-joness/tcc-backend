package com.tcc.api.repositories;

import com.tcc.api.models.LikedWord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LikedWordRepo extends JpaRepository<LikedWord, Long> {

    List<LikedWord> findByUserId(Long userId);

    @Query("SELECT lw.word, COUNT(lw) AS likeCount " +
            "FROM LikedWord lw " +
            "GROUP BY lw.word " +
            "ORDER BY likeCount DESC")
    List<Object[]> findMostLikedWords();

}

