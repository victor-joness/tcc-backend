package com.tcc.api.controllers;

import com.tcc.api.models.Category;
import com.tcc.api.models.User;
import com.tcc.api.repositories.CategoryRepo;
import com.tcc.api.repositories.UserRepo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/categories")
@Tag(name = "Categorias", description = "Serviços de categorias de palavras")
@SecurityRequirement(name = "bearer-key")
public class CategoryController {

    @Autowired
    private CategoryRepo categoryRepo;

    @Operation(description = "Serviço para buscar detalhes de categorias pelo tipo")
    @GetMapping("/{type}")
    public List<Category> getUserById(@PathVariable String type) {
        return categoryRepo.findByType(type);
    }
}

