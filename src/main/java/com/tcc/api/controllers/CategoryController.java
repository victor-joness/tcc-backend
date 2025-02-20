package com.tcc.api.controllers;

import com.tcc.api.models.Category;
import com.tcc.api.models.User;
import com.tcc.api.repositories.CategoryRepo;
import com.tcc.api.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryRepo categoryRepo;

    @GetMapping("/{type}")
    public List<Category> getUserById(@PathVariable String type) {
        return categoryRepo.findByType(type);
    }
}

