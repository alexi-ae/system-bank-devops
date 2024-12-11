package com.system.bank.devops.auth.insfrastructure.adapters.in.handlers;

import com.system.bank.devops.auth.application.dto.CreateUserRequest;
import com.system.bank.devops.auth.application.dto.LoginRequest;
import com.system.bank.devops.auth.application.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyExtractors;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.BodyInserters.fromValue;


@Component
public class AuthHandler {
    @Autowired
    private AuthenticationService authenticationService;

    public Mono<ServerResponse> login(ServerRequest request) {
        return request.body(BodyExtractors.toMono(LoginRequest.class))
                .flatMap(authenticationService::login)
                .flatMap(p -> ServerResponse
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(fromValue(p)));

    }

    public Mono<ServerResponse> register(ServerRequest request) {
        return request.body(BodyExtractors.toMono(CreateUserRequest.class))
                .flatMap(authenticationService::register)
                .flatMap(p -> ServerResponse.status(HttpStatus.CREATED).build());
    }

    public Mono<Void> logout(String token) {
        return authenticationService.logout(token).then();
    }

}
