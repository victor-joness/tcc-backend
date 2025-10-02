package com.tcc.api.repositories;

import com.tcc.api.models.User;
import com.tcc.api.models.LikedWord;
import com.tcc.api.models.Word;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LikedWordRepo extends JpaRepository<LikedWord, Long> {

    List<LikedWord> findByUserId(Long userId);

    @Query("SELECT lw.word, COUNT(lw) AS likeCount " +
           "FROM LikedWord lw " +
           "GROUP BY lw.word " +
           "ORDER BY likeCount DESC")
    List<Object[]> findMostLikedWords();

    Optional<LikedWord> findByUserAndWord(User user, Word word);
}

