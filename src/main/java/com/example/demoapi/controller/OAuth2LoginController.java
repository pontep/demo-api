package com.example.demoapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
@AllArgsConstructor
public class OAuth2LoginController {

    private final ObjectMapper objectMapper;

    @GetMapping("/")
    public String index(
            @RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient authorizedClient,
            @AuthenticationPrincipal OAuth2User oauth2User) {

        String username = oauth2User.getName();
        String clientName = authorizedClient.getClientRegistration().getClientName();
        log.info("username = {}", username);
        log.info("clientName = {}", clientName);
        try{
            log.info("OAuth2LoginController index {}", objectMapper.writeValueAsString(oauth2User.getAttributes()));
        }catch (Exception e){
            log.error("cannot log {}", e.getMessage());
        }
        return "Hello !";
    }
}
