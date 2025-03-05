package com.tcc.api.controllers;

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
    @GetMapping("/category/{categoryId}")
    public List<Word> getWordsByCategory(@PathVariable Long categoryId) {
        return wordRepository.findByCategoryId(categoryId);
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

