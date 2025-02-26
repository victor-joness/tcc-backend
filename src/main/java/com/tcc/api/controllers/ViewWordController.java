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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
    private WordRepo wordRepo;


    @Operation(description = "Serviço para listar palavras vistas de um usuário", security = { @SecurityRequirement(name = "Authorization") })
    @GetMapping("/{userId}")
    public List<ViewWord> getByUserViewWords(@PathVariable Long userId) {
        return viewWordRepository.findByUserIdOrderByDateAsc(userId);
    }


    @Operation(description = "Serviço para salvar palavra vista por um usuário")
    @PostMapping
    public ViewWord saveView(@RequestBody ViewWordDTO data) throws BadRequestException {
        Optional<User> user = userRepo.findById(data.getUserId());
        Optional<Word> word = wordRepo.findById(data.getUserId());
        if (user.isPresent() && word.isPresent()) {
            Optional<ViewWord> viewWordOptional = viewWordRepository.findByUserIdAndWordId(data.getUserId(), data.getWordId());
            if (viewWordOptional.isPresent()) {
                ViewWord viewWord = viewWordOptional.get();
                viewWord.setDate(data.getDate());
                return viewWordRepository.save(viewWord);
            }
            ViewWord viewWordDetails = new ViewWord();
            viewWordDetails.setUser(user.get());
            viewWordDetails.setWord(word.get());
            viewWordDetails.setDate(data.getDate());
            return viewWordRepository.save(viewWordDetails);
        }
        throw new BadRequestException("Palavra ou usuário não existe");


    }

}

