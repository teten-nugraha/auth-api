package id.ten.authapi.service;

import id.ten.authapi.mappers.UserMappers;
import id.ten.authapi.model.User;
import id.ten.authapi.records.LoginRecord;
import id.ten.authapi.records.RegisterUserRecord;
import id.ten.authapi.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserMappers userMappers;

    public AuthenticationService(
            UserRepository userRepository,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder,
            UserMappers userMappers
    ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMappers = userMappers;
    }

    public User signup(RegisterUserRecord payload) {
        User user = userMappers.toUser(payload);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User authenticate(LoginRecord payload) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        payload.email(),
                        payload.password()
                )
        );

        return userRepository.findByEmail(payload.email())
                .orElseThrow();
    }
}
