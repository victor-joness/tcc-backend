package com.tcc.api.repositories;

import com.tcc.api.models.Interpreter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InterpreterRepo extends JpaRepository<Interpreter, Long> {
}

