package com.system.bank.devops.auth.insfrastructure.adapters.out.persistence.jpa.mapper;

import com.system.bank.devops.auth.domain.model.Role;
import com.system.bank.devops.auth.domain.model.User;
import com.system.bank.devops.auth.insfrastructure.adapters.out.persistence.jpa.entity.RoleEntity;
import com.system.bank.devops.auth.insfrastructure.adapters.out.persistence.jpa.entity.UserEntity;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UserEntityMapper {
    public Mono<UserEntity> toEntity(User user) {
        return Mono.just(UserEntity.builder()
                .email(user.getEmail())
                .password(user.getPassword())
                .build());
    }

    public Mono<User> toModel(UserEntity entity) {
        return Mono.just(User.builder()
                .id(entity.getId())
                .email(entity.getEmail())
                .password(entity.getPassword())
                .roles(new HashSet<>())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .customerId(entity.getCustomerId())
                .build());
    }

    private Set<Role> toRoles(Set<RoleEntity> roles) {
        if (roles == null) {
            return new HashSet<>();
        }

        return roles.stream()
                .map(roleEntity -> Role.builder()
                        .id(roleEntity.getId())
                        .description(roleEntity.getDescription()).build())
                .collect(Collectors.toSet());
    }
}
