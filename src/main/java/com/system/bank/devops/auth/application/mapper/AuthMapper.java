package com.system.bank.devops.auth.application.mapper;

import com.system.bank.devops.auth.application.dto.CreateUserRequest;
import com.system.bank.devops.auth.application.dto.LoginRequest;
import com.system.bank.devops.auth.application.dto.LoginResponse;
import com.system.bank.devops.auth.domain.command.CreateUserCommand;
import com.system.bank.devops.auth.domain.command.LoginCommand;
import com.system.bank.devops.auth.domain.model.AuthToken;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class AuthMapper {


    public LoginResponse toLoginResponse(AuthToken authToken) {
        return LoginResponse.builder()
                .token(authToken.getToken())
                .build();
    }

    public Mono<LoginCommand> toLoginRequest(LoginRequest command) {
        return Mono.just(LoginCommand.builder()
                .email(command.getEmail())
                .password(command.getPassword())
                .build());
    }

    public Mono<CreateUserCommand> toCreateUserCommand(CreateUserRequest request) {
        return Mono.just(CreateUserCommand.builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .build());
    }
}
