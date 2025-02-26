package com.tcc.api.controllers;

import com.tcc.api.dto.SaveLawDTO;
import com.tcc.api.models.Law;
import com.tcc.api.repositories.LawRepo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/laws")
@Tag(name = "Leis", description = "Serviços de Leis")
@SecurityRequirement(name = "bearer-key")
public class LawController {

    @Autowired
    private LawRepo lawRepository;

    @Operation(description = "Serviço para criar lei")
    @PostMapping
    public Law createLaw(@RequestBody Law data) {
        Law law = Law.builder()
                .title(data.getTitle())
                .link(data.getLink())
                .resume(data.getResume())
                .build();
        return lawRepository.save(law);

    }

    @Operation(description = "Serviço para listar leis")
    @GetMapping
    public List<Law> getAllLaws() {
        return lawRepository.findAll();
    }

    @Operation(description = "Serviço para buscar detalhes de lei pelo id")
    @GetMapping("/{id}")
    public Law getLawById(@PathVariable Long id) {
        return lawRepository.findById(id).orElseThrow(() -> new RuntimeException("Law not found"));
    }

    @Operation(description = "Serviço para editar lei")
    @PutMapping("/{id}")
    public Law updateLaw(@PathVariable Long id, @RequestBody SaveLawDTO data) {
        Law law = lawRepository.findById(id).orElseThrow(() -> new RuntimeException("Law not found"));
        law.setLink(data.getLink());
        law.setResume(data.getResume());
        law.setTitle(data.getTitle());
        return lawRepository.save(law);
    }

    @Operation(description = "Serviço para apagar lei")
    @DeleteMapping("/{id}")
    public void deleteLaw(@PathVariable Long id) {
        Law law = lawRepository.findById(id).orElseThrow(() -> new RuntimeException("Law not found"));
        lawRepository.delete(law);
    }
}

