package com.tcc.api.repositories;

import com.tcc.api.models.RequestWord;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestWordRepo extends JpaRepository<RequestWord, Long> {
    List<RequestWord> findByStatusIn(List<String> status);
}
