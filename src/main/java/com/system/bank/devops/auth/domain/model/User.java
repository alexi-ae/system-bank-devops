package com.system.bank.devops.auth.domain.model;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Long id;

    private String email;

    private String password;

    private Set<Role> roles;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private Long customerId;

    private String status;
}
