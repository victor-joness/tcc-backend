package com.tcc.api.controllers;

import com.tcc.api.dto.SaveInterpreterDTO;
import com.tcc.api.models.Interpreter;
import com.tcc.api.repositories.InterpreterRepo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/interpreters")
@Tag(name = "Interpretes", description = "Serviços de interpretes")
public class InterpreterController {

    @Autowired
    private InterpreterRepo interpreterRepository;

    @Operation(description = "Serviço para criar interprete")
    @PostMapping
    public Interpreter createInterpreter(@RequestBody Interpreter data) {
        Interpreter interpreter = Interpreter.builder()
                .name(data.getName())
                .email(data.getEmail())
                .build();
        return interpreterRepository.save(interpreter);

    }

    @Operation(description = "Serviço para listar interpretes")
    @GetMapping
    public List<Interpreter> getAllInterpreters() {
        return interpreterRepository.findAll();
    }

    @Operation(description = "Serviço para buscar detalhes de interprete pelo id")
    @GetMapping("/{id}")
    public Interpreter getInterpreterById(@PathVariable Long id) {
        return interpreterRepository.findById(id).orElseThrow(() -> new RuntimeException("Interpreter not found"));
    }

    @Operation(description = "Serviço para editar interprete")
    @PutMapping("/{id}")
    public Interpreter updateInterpreter(@PathVariable Long id, @RequestBody SaveInterpreterDTO data) {
        Interpreter interpreter = interpreterRepository.findById(id).orElseThrow(() -> new RuntimeException("Interpreter not found"));
        interpreter.setEmail(data.getEmail());
        interpreter.setName(data.getName());
        return interpreterRepository.save(interpreter);
    }

    @Operation(description = "Serviço para apagar interprete")
    @DeleteMapping("/{id}")
    public void deleteInterpreter(@PathVariable Long id) {
        Interpreter interpreter = interpreterRepository.findById(id).orElseThrow(() -> new RuntimeException("Interpreter not found"));
        interpreterRepository.delete(interpreter);
    }
}

