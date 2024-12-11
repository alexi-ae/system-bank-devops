package com.system.bank.devops.auth.domain.model;


import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Token {
    private String id;
    private String userId;
    private String token;
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;
    private boolean revoked;
}
