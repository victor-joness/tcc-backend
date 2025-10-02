package com.tcc.api.controllers;

import com.tcc.api.dto.AvaliateRequestCreateWordDTO;
import com.tcc.api.dto.RequestWordDTO;
import com.tcc.api.dto.SaveWordDTO;
import com.tcc.api.dto.UpdateWordStatusDTO;
import com.tcc.api.dto.WordResponseDTO;
import com.tcc.api.models.Category;
import com.tcc.api.models.LikedWord;
import com.tcc.api.models.RequestWord;
import com.tcc.api.models.Word;
import com.tcc.api.repositories.CategoryRepo;
import com.tcc.api.repositories.InterpreterRepo;
import com.tcc.api.repositories.LikedWordRepo;
import com.tcc.api.repositories.RequestWordRepo;
import com.tcc.api.repositories.WordRepo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/words")
@Tag(name = "Palavras", description = "Serviços de palavras")
@SecurityRequirement(name = "bearer-key")
public class WordController {

    @Autowired
    private WordRepo wordRepository;

    @Autowired
    private InterpreterRepo interpreterRepo;

    @Autowired
    private LikedWordRepo likedWordRepository;

    @Autowired
    private RequestWordRepo requestWordRepository;

    @Autowired
    private CategoryRepo categoryRepo;

    @Operation(description = "Serviço para buscar todas as palavras aprovadas (com favoritos, se userId for informado)")
    @GetMapping
    public List<Map<String, Object>> getAllApprovedWords(
            @RequestParam(required = false) Long userId) {

        List<Word> words = wordRepository.findByStatus("APPROVED");

        final Set<Long> favoritedWordIds;
        if (userId != null) {
            List<LikedWord> likedWords = likedWordRepository.findByUserId(userId).stream()
                    .filter(lw -> "SAVED".equalsIgnoreCase(lw.getStatus()))
                    .collect(Collectors.toList());

            favoritedWordIds = likedWords.stream()
                    .map(lw -> lw.getWord().getId())
                    .collect(Collectors.toSet());
        } else {
            favoritedWordIds = new HashSet<>();
        }

        return words.stream().map(word -> {
            Map<String, Object> wordMap = new HashMap<>();
            wordMap.put("id", word.getId());
            wordMap.put("word", word.getWord());
            wordMap.put("description", word.getDescription());
            wordMap.put("video", word.getVideo());
            wordMap.put("status", word.getStatus());
            wordMap.put("modulo", word.getModulo());
            wordMap.put("category", word.getCategory());

            if (favoritedWordIds.contains(word.getId())) {
                wordMap.put("favorited", true);
            }

            if (word.getVariations() != null && !word.getVariations().isEmpty()) {
                wordMap.put("variacao", true);
            }

            return wordMap;
        }).collect(Collectors.toList());
    }

    @Operation(description = "Serviço para listar todas as palavras solicitadas")
    @GetMapping("/requests")
    public List<RequestWord> getAllRequestedWords() {
        List<String> allowedStatuses = List.of("PENDING", "REJECTED", "TRANSLATED");
        return requestWordRepository.findByStatusIn(allowedStatuses);
    }

    @Operation(description = "Atualiza o status e o interpreter_id de uma palavra na RequestWord")
    @PutMapping("/{id}/status")
    public ResponseEntity<RequestWord> updateWordStatus(
            @PathVariable Long id,
            @RequestBody UpdateWordStatusDTO statusDTO) throws BadRequestException {

        Optional<RequestWord> optionalWord = requestWordRepository.findById(id);

        if (optionalWord.isEmpty()) {
            throw new BadRequestException("Palavra não encontrada com o ID: " + id);
        }

        RequestWord word = optionalWord.get();
        String newStatus = statusDTO.getStatus();

        if (!newStatus.equals("APPROVED") && !newStatus.equals("REJECTED") && !newStatus.equals("TRANSLATED")) {
            throw new BadRequestException("Status inválido. Use 'APPROVED', 'REJECTED' ou 'TRANSLATED'.");
        }

        word.setStatus(newStatus);

        if (statusDTO.getInterpreterId() != null) {
            word.setInterpreterId(statusDTO.getInterpreterId());
        }

        RequestWord updatedWord = requestWordRepository.save(word);
        return ResponseEntity.ok(updatedWord);
    }

    @Operation(description = "Serviço para solicitar inclusão de palavra")
    @PostMapping("/request")
    public RequestWord requestCreateWord(@RequestBody RequestWordDTO data) throws BadRequestException {
        RequestWord requestWord = RequestWord.builder()
                .word(data.getWord())
                .videoUrl(data.getVideo_url())
                .status("PENDING")
                .category(data.getCategory())
                .requestUserId(data.getRequest_user_id())
                .build();

        return requestWordRepository.save(requestWord);
    }

    @Operation(description = "Serviço para avaliar inclusão de palavra")
    @PostMapping("/{wordId}")
    public Word avaliateRequestCreateWord(@PathVariable Long wordId, @RequestBody AvaliateRequestCreateWordDTO data)
            throws BadRequestException {
        Optional<Word> wordOptional = wordRepository.findById(wordId);
        if (wordOptional.isPresent()) {
            Word word = wordOptional.get();
            if (data.isApproved())
                word.setStatus("aprovado");
            else
                word.setStatus("reprovado");
            return wordRepository.save(word);
        }
        throw new BadRequestException("Palavra não existe");
    }

    @Operation(description = "Serviço para criar palavra")
    @PostMapping
    public Word createWord(@RequestBody SaveWordDTO data) throws BadRequestException {
        Optional<Category> category = categoryRepo.findById(data.getCategoryId());
        System.err.println(data);
        if (category.isPresent()) {
            Word word = Word.builder()
                    .word(data.getWord())
                    .video(data.getVideo())
                    .status(data.getStatus())
                    .modulo(data.getModulo())
                    .description(data.getDescription())
                    .request_word_id(data.getRequest_word_id())
                    .category(category.get())
                    .build();
            return wordRepository.save(word);
        }
        throw new BadRequestException("Categoria não existe");
    }

    @Operation(description = "Serviço para buscar detalhes de palavras pela categoria")
    @GetMapping("/category/{categoryId}")
    public List<Map<String, Object>> getWordsByCategory(
            @PathVariable Long categoryId,
            @RequestParam(required = false) Long userId) {

        List<Word> words = wordRepository.findByCategoryIdAndStatus(categoryId, "APPROVED");

        final Set<Long> favoritedWordIds;
        if (userId != null) {
            List<LikedWord> likedWords = likedWordRepository.findByUserId(userId).stream()
                    .filter(lw -> "SAVED".equalsIgnoreCase(lw.getStatus()))
                    .collect(Collectors.toList());

            favoritedWordIds = likedWords.stream()
                    .map(lw -> lw.getWord().getId())
                    .collect(Collectors.toSet());
        } else {
            favoritedWordIds = new HashSet<>();
        }

        return words.stream().map(word -> {
            Map<String, Object> wordMap = new HashMap<>();
            wordMap.put("id", word.getId());
            wordMap.put("word", word.getWord());
            wordMap.put("description", word.getDescription());
            wordMap.put("video", word.getVideo());
            wordMap.put("status", word.getStatus());
            wordMap.put("modulo", word.getModulo());
            wordMap.put("category", word.getCategory());

            if (favoritedWordIds.contains(word.getId())) {
                wordMap.put("favorited", true);
            }

            if (word.getVariations() != null && !word.getVariations().isEmpty()) {
                wordMap.put("variacao", true);
            }

            return wordMap;
        }).collect(Collectors.toList());
    }

    @Operation(description = "Serviço para buscar detalhes de palavras pelo status")
    @GetMapping("/status/{status}")
    public List<WordResponseDTO> getWordsByStatus(@PathVariable String status) {
        List<Word> words = wordRepository.findByStatus(status);

        return words.stream().map(word -> {
            final String[] interpreterName = { null };

            if (word.getRequest_word_id() != null) {
                requestWordRepository.findById(word.getRequest_word_id()).ifPresent(requestWord -> {
                    interpreterRepo.findById(requestWord.getInterpreterId()).ifPresent(interpreter -> {
                        interpreterName[0] = interpreter.getName();
                    });
                });
            }

            return WordResponseDTO.builder()
                    .id(word.getId())
                    .word(word.getWord())
                    .description(word.getDescription())
                    .video(word.getVideo())
                    .status(word.getStatus())
                    .modulo(word.getModulo())
                    .request_word_id(word.getRequest_word_id())
                    .category(word.getCategory())
                    .variations(word.getVariations())
                    .interpreterName(interpreterName[0])
                    .build();
        }).toList();
    }

    @Operation(description = "Serviço para buscar detalhes de palavra")
    @GetMapping("/{id}")
    public Word getWordById(@PathVariable Long id) {
        return wordRepository.findById(id).orElseThrow(() -> new RuntimeException("Word not found"));
    }

    @Operation(description = "Serviço para editar palavra")
    @PutMapping("/{id}")
    public Word updateWord(@PathVariable Long id, @RequestBody SaveWordDTO data) throws BadRequestException {
        Word word = wordRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Palavra não existe"));

        if (data.getCategoryId() != null) {
            Category category = categoryRepo.findById(data.getCategoryId())
                    .orElseThrow(() -> new BadRequestException("Categoria não existe"));
            word.setCategory(category);
        }

        if (data.getWord() != null)
            word.setWord(data.getWord());

        if (data.getVideo() != null)
            word.setVideo(data.getVideo());

        if (data.getStatus() != null)
            word.setStatus(data.getStatus());

        if (data.getModulo() != null)
            word.setModulo(data.getModulo());

        if (data.getDescription() != null)
            word.setDescription(data.getDescription());

        return wordRepository.save(word);
    }

    @Operation(description = "Serviço para apagar palavra")
    @DeleteMapping("/{id}")
    public void deleteWord(@PathVariable Long id) {
        Word word = wordRepository.findById(id).orElseThrow(() -> new RuntimeException("Word not found"));
        wordRepository.delete(word);
    }
}
