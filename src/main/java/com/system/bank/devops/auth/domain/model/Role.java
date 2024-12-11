package com.system.bank.devops.auth.domain.model;

import lombok.*;
import org.springframework.data.annotation.Id;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    @Id
    private Long id;
    private String description;
}
