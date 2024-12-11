package com.system.bank.devops.auth.application.services;

import com.system.bank.devops.auth.application.dto.CreateUserRequest;
import com.system.bank.devops.auth.application.dto.LoginRequest;
import com.system.bank.devops.auth.application.mapper.AuthMapper;
import com.system.bank.devops.auth.domain.model.AuthToken;
import com.system.bank.devops.auth.domain.port.in.LoginUserUseCase;
import com.system.bank.devops.auth.domain.port.in.LogoutUserUseCase;
import com.system.bank.devops.auth.domain.port.in.RegisterUserUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class AuthenticationService {

    @Autowired
    private LoginUserUseCase loginUserUseCase;

    @Autowired
    private RegisterUserUseCase registerUserUseCase;

    @Autowired
    private LogoutUserUseCase logoutUserUseCase;

    @Autowired
    private AuthMapper authMapper;

    public Mono<AuthToken> login(LoginRequest request) {
        return authMapper.toLoginRequest(request)
                .flatMap(loginUserUseCase::execute);
    }

    public Mono<Void> register(CreateUserRequest request) {
        return authMapper.toCreateUserCommand(request)
                .flatMap(registerUserUseCase::execute);
    }

    public Mono<Void> logout(String token) {
        return logoutUserUseCase.execute(token);
    }
}
