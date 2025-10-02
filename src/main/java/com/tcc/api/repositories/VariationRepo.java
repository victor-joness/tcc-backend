package com.tcc.api.repositories;

import com.tcc.api.models.Variation;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface VariationRepo extends JpaRepository<Variation, Long> {
    List<Variation> findByWordId(Long wordId);
}

