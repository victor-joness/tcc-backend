package com.tcc.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

public interface WordRepo extends JpaRepository<com.tcc.api.models.Word, Long> {
}

