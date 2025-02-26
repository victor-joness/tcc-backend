package com.tcc.api.repositories;

import com.tcc.api.models.Interpreter;
import com.tcc.api.models.Law;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LawRepo extends JpaRepository<Law, Long> {
}

