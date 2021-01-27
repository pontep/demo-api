package com.example.demoapi.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserAccount {

    private Long id;
    private String username;
    private String password;
    private String displayName;
    private String email;
    private Integer loginFailedCount;
}
