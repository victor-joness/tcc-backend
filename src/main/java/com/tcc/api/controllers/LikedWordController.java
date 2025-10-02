package com.tcc.api.controllers;

import com.tcc.api.dto.MostLikedWordDTO;
import com.tcc.api.dto.SaveLikedWord;
import com.tcc.api.models.LikedWord;
import com.tcc.api.models.User;
import com.tcc.api.models.Word;
import com.tcc.api.repositories.LikedWordRepo;
import com.tcc.api.repositories.UserRepo;
import com.tcc.api.repositories.WordRepo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/liked-words")
@Tag(name = "Palavras curtidas", description = "Serviços de palavras curtidas")
@SecurityRequirement(name = "bearer-key")
public class LikedWordController {

    @Autowired
    private LikedWordRepo likedWordRepository;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private WordRepo wordRepo;

    @Operation(description = "Serviço para salvar curtida")
    @PostMapping
    public LikedWord createLikedWord(@RequestBody SaveLikedWord data) throws BadRequestException {
        Optional<User> userOpt = userRepo.findById(data.getUserId());
        Optional<Word> wordOpt = wordRepo.findById(data.getWordId());

        if (userOpt.isEmpty() || wordOpt.isEmpty()) {
            throw new BadRequestException("Palavra ou usuário não existe");
        }

        User user = userOpt.get();
        Word word = wordOpt.get();

        // Verifica se já existe curtida para aquele usuário e palavra
        Optional<LikedWord> likedWordOpt = likedWordRepository.findByUserAndWord(user, word);

        if (likedWordOpt.isPresent()) {
            LikedWord likedWord = likedWordOpt.get();

            // Alterna status SAVED <-> REMOVED
            if ("SAVED".equalsIgnoreCase(likedWord.getStatus())) {
                likedWord.setStatus("REMOVED");
            } else {
                likedWord.setStatus("SAVED");
            }

            return likedWordRepository.save(likedWord);
        } else {
            LikedWord likedWord = new LikedWord();
            likedWord.setUser(user);
            likedWord.setWord(word);
            likedWord.setStatus("SAVED");

            return likedWordRepository.save(likedWord);
        }
    }

    @Operation(description = "Serviço para buscar palavras curtidas do usuário")
    @GetMapping("/user/{userId}")
    public List<Word> getLikedWordsByUser(@PathVariable Long userId) {
        List<LikedWord> likedWords = likedWordRepository.findByUserId(userId);

        // Filtra apenas os likes com status "SAVED"
        return likedWords.stream()
                .filter(lw -> "SAVED".equalsIgnoreCase(lw.getStatus()))
                .map(LikedWord::getWord)
                .collect(Collectors.toList());
    }

    @Operation(description = "Serviço para descurtir palavra")
    @DeleteMapping("/{id}")
    public void deleteLikedWord(@PathVariable Long id) {
        LikedWord likedWord = likedWordRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Liked word not found"));
        likedWordRepository.delete(likedWord);
    }

    @Operation(description = "Serviço para buscar detalhes de as palavras e suas respectivas quantidades de curtidas")
    @GetMapping("/most-liked")
    public List<MostLikedWordDTO> getMostLikedWords() {
        List<Object[]> results = likedWordRepository.findMostLikedWords();
        List<MostLikedWordDTO> mostLikedWords = new ArrayList<>();

        for (Object[] result : results) {
            String word = (String) result[0];
            Long likeCount = (Long) result[1];
            mostLikedWords.add(new MostLikedWordDTO(word, likeCount));
        }

        return mostLikedWords;
    }
}
