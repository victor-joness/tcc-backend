package com.tcc.api.controllers;

import com.tcc.api.dto.AvaliateRequestCreateWordDTO;
import com.tcc.api.dto.SaveWordDTO;
import com.tcc.api.models.Category;
import com.tcc.api.models.Word;
import com.tcc.api.repositories.CategoryRepo;
import com.tcc.api.repositories.WordRepo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/words")
@Tag(name = "Palavras", description = "Serviços de palavras")
@SecurityRequirement(name = "bearer-key")
public class WordController {

    @Autowired
    private WordRepo wordRepository;

    @Autowired
    private CategoryRepo categoryRepo;

    @Operation(description = "Serviço para solicitar inclusão de palavra")
    @PostMapping("/request")
    public Word requestCreateWord(@RequestBody SaveWordDTO data) throws BadRequestException {
        Optional<Category> category = categoryRepo.findById(data.getCategoryId());
        if (category.isPresent()){
            Word word = Word.builder()
                    .word(data.getWord())
                    .video(data.getVideo())
                    .status("em analise")
                    .modulo(data.getModulo())
                    .description(data.getDescription())
                    .category(category.get())
                    .build();
            return wordRepository.save(word);
        }
        throw new BadRequestException("Categoria não existe");
    }

    @Operation(description = "Serviço para avaliar inclusão de palavra")
    @PostMapping("/{wordId}")
    public Word avaliateRequestCreateWord(@PathVariable Long wordId, @RequestBody AvaliateRequestCreateWordDTO data) throws BadRequestException {
        Optional<Word> wordOptional = wordRepository.findById(wordId);
        if (wordOptional.isPresent()){
            Word word = wordOptional.get();
            if (data.isApproved()) word.setStatus("aprovado");
            else word.setStatus("reprovado");
            return wordRepository.save(word);
        }
        throw new BadRequestException("Palavra não existe");
    }

    @Operation(description = "Serviço para criar palavra")
    @PostMapping
    public Word createWord(@RequestBody SaveWordDTO data) throws BadRequestException {
        Optional<Category> category = categoryRepo.findById(data.getCategoryId());
        if (category.isPresent()){
            Word word = Word.builder()
                    .word(data.getWord())
                    .video(data.getVideo())
                    .status(data.getStatus())
                    .modulo(data.getModulo())
                    .description(data.getDescription())
                    .category(category.get())
                    .build();
            return wordRepository.save(word);
        }
        throw new BadRequestException("Categoria não existe");
    }

    @Operation(description = "Serviço para buscar detalhes de palavras pela categoria")
    @GetMapping("/{categoryId}")
    public List<Word> getWordsByCategory(@PathVariable Long categoryId) {
        return wordRepository.findByCategoryIdAndStatus(categoryId, "aprovado");
    }

    @Operation(description = "Serviço para buscar detalhes de palavras pelo status")
    @GetMapping("/{status}")
    public List<Word> getWordsByStatus(@PathVariable String status) {
        return wordRepository.findByStatus(status);
    }

    @Operation(description = "Serviço para buscar detalhes de palavra")
    @GetMapping("/{id}")
    public Word getWordById(@PathVariable Long id) {
        return wordRepository.findById(id).orElseThrow(() -> new RuntimeException("Word not found"));
    }

    @Operation(description = "Serviço para editar palavra")
    @PutMapping("/{id}")
    public Word updateWord(@PathVariable Long id, @RequestBody SaveWordDTO data) throws BadRequestException {
        Optional<Category> category = categoryRepo.findById(data.getCategoryId());
        Optional<Word> wordOpt = wordRepository.findById(id);
        if (wordOpt.isEmpty()) throw new BadRequestException("Palavra não existe");
        if (category.isPresent()){
            Word word = Word.builder()
                    .word(data.getWord())
                    .video(data.getVideo())
                    .status(data.getStatus())
                    .modulo(data.getModulo())
                    .description(data.getDescription())
                    .category(category.get())
                    .build();
            return wordRepository.save(word);
        }
        throw new BadRequestException("Categoria não existe");
    }

    @Operation(description = "Serviço para apagar palavra")
    @DeleteMapping("/{id}")
    public void deleteWord(@PathVariable Long id) {
        Word word = wordRepository.findById(id).orElseThrow(() -> new RuntimeException("Word not found"));
        wordRepository.delete(word);
    }
}

