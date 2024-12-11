package com.system.bank.devops.auth.insfrastructure.adapters.out.persistence.jpa.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table("role")
public class RoleEntity {
    @Id
    private Long id;

    private String description;
}
