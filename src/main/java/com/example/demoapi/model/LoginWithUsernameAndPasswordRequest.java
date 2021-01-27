package com.example.demoapi.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginWithUsernameAndPasswordRequest {

    private String username;
    private String password;
}
