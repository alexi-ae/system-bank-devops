package com.system.bank.devops.auth.domain.port.in;

import com.system.bank.devops.auth.domain.command.CreateUserCommand;
import reactor.core.publisher.Mono;

public interface RegisterUserUseCase {
    Mono<Void> execute(CreateUserCommand command);
}
