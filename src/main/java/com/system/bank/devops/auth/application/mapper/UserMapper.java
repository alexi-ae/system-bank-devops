package com.system.bank.devops.auth.application.mapper;


import com.system.bank.devops.auth.domain.command.CreateUserCommand;
import com.system.bank.devops.auth.domain.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toModel(CreateUserCommand createUserRequest) {
        return User.builder()
                .email(createUserRequest.getEmail())
                .password(createUserRequest.getPassword())
                .build();
    }
}
