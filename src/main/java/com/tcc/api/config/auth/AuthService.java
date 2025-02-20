package com.tcc.api.config.auth;


import com.tcc.api.config.auth.dto.*;
import com.tcc.api.config.mail.MailService;
import com.tcc.api.enums.Role;
import com.tcc.api.models.User;
import com.tcc.api.repositories.UserRepo;
import com.tcc.api.utils.GenerateCodeValidation;
import jakarta.transaction.Transactional;
import org.apache.coyote.BadRequestException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenService jwtTokenService;

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Autowired
    private UserRepo userRepository;

    @Autowired
    private MailService sendMailSignUpUseCase;

    @Value("${strings.subjectSignUpEmail}")
    private String subjectSignUpEmail;

    @Value("${strings.messageSignUpEmail}")
    private String messageSignUpEmail;


    @Transactional
    public LoggedDTO login (LoginDTO data) throws BadRequestException {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(data.getEmail(), data.getPassword());
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);


        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        if (!userDetails.getUser().getVerified()) throw new BadRequestException("Usuário não verificado");

        return new LoggedDTO(jwtTokenService.generateToken(userDetails), userDetails.getUser().getId(), userDetails.getUser().getRole().name());
    }

    @Transactional
    public CreatedUserDTO signUpInterprete (SignUpDTO data) throws Exception {
        if (!(data.getPassword().equals(data.getPasswordConfirmation()))) throw new BadRequestException("As senhas devem ser iguais.");
        if (!(data.getPassword().length() > 6)) throw new BadRequestException("A senha deve ter pelo menos 7 caracteres");
        String code = GenerateCodeValidation.generateCode();

        User user = modelMapper.map(data, User.class);
        user.setRole(Role.INTERPRETE);
        user.setCode(code);
        user.setPassword(passwordEncoder.encode(data.getPassword()));
        User userCreated = userRepository.save(user);
        sendMailSignUpUseCase.sendSimpleMessage(userCreated.getEmail(), subjectSignUpEmail, messageSignUpEmail);
        return modelMapper.map(userCreated, CreatedUserDTO.class);

    }

    public CreatedUserDTO signUp (SignUpDTO data) throws Exception {
        if (!(data.getPassword().equals(data.getPasswordConfirmation()))) throw new BadRequestException("As senhas devem ser iguais.");
        if (!(data.getPassword().length() > 6)) throw new BadRequestException("A senha deve ter pelo menos 7 caracteres");
        String code = GenerateCodeValidation.generateCode();

        User user = modelMapper.map(data, User.class);
        user.setRole(Role.SURDO);
        user.setCode(code);
        user.setPassword(passwordEncoder.encode(data.getPassword()));
        User userCreated = userRepository.save(user);
        sendMailSignUpUseCase.sendSimpleMessage(userCreated.getEmail(), subjectSignUpEmail, messageSignUpEmail);
        return modelMapper.map(userCreated, CreatedUserDTO.class);

    }

    @Transactional
    public ValidatedEmailSignUpDTO validationEmailSignUp (ValidationEmailSignUpDTO data) throws Exception {
        Optional<User> userFind = userRepository.findByEmailAndCode(data.getEmail(), data.getCode());
        if (userFind.isEmpty()) throw new Exception("Not found resource");
        User user = userFind.get();
        if (!user.getRole().equals(Role.INTERPRETE)){
            user.setVerified(true);
        }
        user.setCode(null);
        userRepository.save(user);
        return new ValidatedEmailSignUpDTO(true, user.getRole().name());

    }
}
