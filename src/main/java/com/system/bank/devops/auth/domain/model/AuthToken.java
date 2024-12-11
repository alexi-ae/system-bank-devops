package com.system.bank.devops.auth.domain.model;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthToken {
    private String token;
}
