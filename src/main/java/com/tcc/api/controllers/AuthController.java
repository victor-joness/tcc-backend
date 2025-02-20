package com.tcc.api.controllers;


import com.tcc.api.config.auth.AuthService;
import com.tcc.api.config.auth.dto.*;
import com.tcc.api.controllers.responses.ResponseCreated;
import com.tcc.api.controllers.responses.ResponseOk;
import jakarta.validation.Valid;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<ResponseCreated> signUp(@RequestBody SignUpDTO data) throws Exception {
        CreatedUserDTO createdUser = authService.signUp(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseCreated("Created", createdUser));
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseOk> login(@Valid @RequestBody LoginDTO data) throws BadRequestException {
        LoggedDTO logged = authService.login(data);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseOk("Success", logged));
    }

    @PostMapping("/validation-email-signup")
    public ResponseEntity<ResponseCreated> validationEmailSignUp(@RequestBody ValidationEmailSignUpDTO data) throws Exception {
        ValidatedEmailSignUpDTO validated = authService.validationEmailSignUp(data);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseCreated("Ok", validated));
    }
}

