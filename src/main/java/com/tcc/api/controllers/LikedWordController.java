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
        Optional<User> user = userRepo.findById(data.getUserId());
        Optional<Word> word = wordRepo.findById(data.getUserId());

        if (user.isPresent() && word.isPresent()) {
            LikedWord likedWord = new LikedWord();
            likedWord.setWord(word.get());
            likedWord.setUser(user.get());
            return likedWordRepository.save(likedWord);
        }
        throw new BadRequestException("Palavra ou usuário não existe");

    }



    @Operation(description = "Serviço para buscar detalhes de curtidas do usuário")
    @GetMapping("/user/{userId}")
    public List<LikedWord> getLikedWordsByUser(@PathVariable Long userId) {
        return likedWordRepository.findByUserId(userId);
    }

    @Operation(description = "Serviço para descurtir palavra")
    @DeleteMapping("/{id}")
    public void deleteLikedWord(@PathVariable Long id) {
        LikedWord likedWord = likedWordRepository.findById(id).orElseThrow(() -> new RuntimeException("Liked word not found"));
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

