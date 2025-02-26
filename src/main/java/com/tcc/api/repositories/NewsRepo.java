package com.tcc.api.repositories;

import com.tcc.api.models.News;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsRepo extends JpaRepository<News, Long> {
}

