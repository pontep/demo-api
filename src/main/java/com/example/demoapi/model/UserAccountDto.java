package com.example.demoapi.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserAccountDto {

    private Long id;
    private String username;
    private String email;
    private String displayName;
}
