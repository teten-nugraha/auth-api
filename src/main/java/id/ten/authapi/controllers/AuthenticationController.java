package id.ten.authapi.controllers;

import id.ten.authapi.model.User;
import id.ten.authapi.records.LoginRecord;
import id.ten.authapi.records.LoginResponseRecord;
import id.ten.authapi.records.RegisterUserRecord;
import id.ten.authapi.service.AuthenticationService;
import id.ten.authapi.service.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/auth")
@RestController
public class AuthenticationController {

    private final JwtService jwtService;
    private final AuthenticationService authenticationService;

    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signup")
    public ResponseEntity<User> register(@RequestBody RegisterUserRecord payload) {
        User registeredUser = authenticationService.signup(payload);

        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseRecord> authenticate(@RequestBody LoginRecord payload) {
        User authenticatedUser = authenticationService.authenticate(payload);
        String jwtToken = jwtService.generateToken(authenticatedUser);

        LoginResponseRecord loginResponse = new LoginResponseRecord(jwtToken, jwtService.getExpirationTime());

        return ResponseEntity.ok(loginResponse);
    }
}
