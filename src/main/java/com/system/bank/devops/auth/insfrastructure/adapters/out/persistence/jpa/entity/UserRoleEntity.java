package com.system.bank.devops.auth.insfrastructure.adapters.out.persistence.jpa.entity;

import lombok.*;
import org.springframework.data.relational.core.mapping.Table;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table("user_roles")
public class UserRoleEntity {
    private Long userId;

    private Long roleId;
}
