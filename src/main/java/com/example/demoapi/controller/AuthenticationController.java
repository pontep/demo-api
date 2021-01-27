package com.example.demoapi.controller;

import com.example.demoapi.model.LoginWithUsernameAndPasswordRequest;
import com.example.demoapi.model.UserAccountDto;
import com.example.demoapi.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<?> loginWithUsernameAndPassword(@RequestBody LoginWithUsernameAndPasswordRequest request){
        log.info("loginWithUsernameAndPassword {}, {}", request.getUsername(), request.getPassword());
        UserAccountDto userAccountDto = this.authenticationService.loginWithUsernameAndPassword(request);
        log.info("Response userAccountDto = {}", userAccountDto);
        return ResponseEntity.ok().body(userAccountDto);
    }
}
