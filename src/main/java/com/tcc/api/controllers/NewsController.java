package com.tcc.api.controllers;

import com.tcc.api.dto.SaveNewsDTO;
import com.tcc.api.models.News;
import com.tcc.api.repositories.NewsRepo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/news")
@Tag(name = "Noticias", description = "Serviços de noticias")
public class NewsController {

    @Autowired
    private NewsRepo newsRepository;

    @Operation(description = "Serviço para criar noticia")
    @PostMapping
    public News createNews(@RequestBody News data) {
        News news = News.builder()
                .title(data.getTitle())
                .link(data.getLink())
                .resume(data.getResume())
                .build();
        return newsRepository.save(news);

    }

    @Operation(description = "Serviço para listar noticias")
    @GetMapping
    public List<News> getAllNewss() {
        return newsRepository.findAll();
    }

    @Operation(description = "Serviço para buscar detalhes de noticia")
    @GetMapping("/{id}")
    public News getNewsById(@PathVariable Long id) {
        return newsRepository.findById(id).orElseThrow(() -> new RuntimeException("News not found"));
    }

    @Operation(description = "Serviço para editar noticia")
    @PutMapping("/{id}")
    public News updateNews(@PathVariable Long id, @RequestBody SaveNewsDTO data) {
        News news = newsRepository.findById(id).orElseThrow(() -> new RuntimeException("News not found"));
        news.setLink(data.getLink());
        news.setResume(data.getResume());
        news.setTitle(data.getTitle());
        return newsRepository.save(news);
    }

    @Operation(description = "Serviço para apagar noticia")
    @DeleteMapping("/{id}")
    public void deleteNews(@PathVariable Long id) {
        News news = newsRepository.findById(id).orElseThrow(() -> new RuntimeException("News not found"));
        newsRepository.delete(news);
    }
}

