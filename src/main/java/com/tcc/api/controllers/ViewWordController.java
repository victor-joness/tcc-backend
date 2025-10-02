package com.tcc.api.controllers;

import com.tcc.api.dto.ViewWordDTO;
import com.tcc.api.models.User;
import com.tcc.api.models.ViewWord;
import com.tcc.api.models.Word;
import com.tcc.api.repositories.UserRepo;
import com.tcc.api.repositories.ViewWordRepo;
import com.tcc.api.repositories.WordRepo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/view-words")
@Tag(name = "Palavras visualizadas", description = "Serviços de palavras visualizadas")
@SecurityRequirement(name = "bearer-key")
public class ViewWordController {

    @Autowired
    private ViewWordRepo viewWordRepository;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private WordRepo wordRepository;

    @Operation(description = "Retorna apenas as palavras visualizadas recentemente por um usuário")
    @GetMapping("/{userId}")
    public List<Word> getRecentViewedWords(@PathVariable Long userId) {
        Pageable pageable = PageRequest.of(0, 10);
        List<Long> viewedWordIds = viewWordRepository.findViewedWordIdsByUserId(userId, pageable);

        if (viewedWordIds.isEmpty()) {
            return Collections.emptyList();
        }

        // Buscar as palavras pelo id
        List<Word> words = wordRepository.findByIdIn(viewedWordIds);

        // Opcional: ordenar para ficar na ordem das visualizações mais recentes
        // Como findByIdIn pode não preservar a ordem, ordenamos aqui:
        Map<Long, Word> wordMap = words.stream().collect(Collectors.toMap(Word::getId, w -> w));
        List<Word> sortedWords = viewedWordIds.stream()
                .map(wordMap::get)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        return sortedWords;
    }

    @Operation(description = "Serviço para salvar palavra vista por um usuário")
    @PostMapping
    public Word saveView(@RequestBody ViewWordDTO data) throws BadRequestException {
        Optional<User> user = userRepo.findById(data.getUserId());
        Optional<Word> word = wordRepository.findById(data.getWordId());

        if (user.isPresent() && word.isPresent()) {
            Optional<ViewWord> viewWordOptional = viewWordRepository.findByUserIdAndWordId(data.getUserId(),
                    data.getWordId());

            if (viewWordOptional.isPresent()) {
                ViewWord viewWord = viewWordOptional.get();
                viewWord.setDate(data.getDate());
                viewWordRepository.save(viewWord);
            } else {
                ViewWord viewWord = new ViewWord();
                viewWord.setUser(user.get());
                viewWord.setWord(word.get());
                viewWord.setDate(data.getDate());
                viewWordRepository.save(viewWord);
            }

            return word.get(); // Retorna apenas a palavra
        }

        throw new BadRequestException("Palavra ou usuário não existe");
    }
}
