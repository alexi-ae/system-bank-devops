package com.system.bank.devops.auth.domain.port.in;

import reactor.core.publisher.Mono;

public interface LogoutUserUseCase {
    Mono<Void> execute(String token);
}
