package com.tcc.api.controllers;

import com.tcc.api.models.Word;
import com.tcc.api.repositories.WordRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/words")
public class WordController {

    @Autowired
    private WordRepo wordRepository;

    @PostMapping
    public Word createWord(@RequestBody Word word) {
        return wordRepository.save(word);
    }

    @GetMapping
    public List<Word> getAllWords() {
        return wordRepository.findAll();
    }

    @GetMapping("/{id}")
    public Word getWordById(@PathVariable Long id) {
        return wordRepository.findById(id).orElseThrow(() -> new RuntimeException("Word not found"));
    }

    @PutMapping("/{id}")
    public Word updateWord(@PathVariable Long id, @RequestBody Word wordDetails) {
        Word word = wordRepository.findById(id).orElseThrow(() -> new RuntimeException("Word not found"));
        word.setWord(wordDetails.getWord());
        word.setDescription(wordDetails.getDescription());
        word.setVideo(wordDetails.getVideo());
        word.setStatus(wordDetails.getStatus());
        word.setModulo(wordDetails.getModulo());
        word.setCategory(wordDetails.getCategory());
        return wordRepository.save(word);
    }

    @DeleteMapping("/{id}")
    public void deleteWord(@PathVariable Long id) {
        Word word = wordRepository.findById(id).orElseThrow(() -> new RuntimeException("Word not found"));
        wordRepository.delete(word);
    }
}

