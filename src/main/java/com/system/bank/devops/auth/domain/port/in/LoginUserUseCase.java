package com.system.bank.devops.auth.domain.port.in;

import com.system.bank.devops.auth.domain.command.LoginCommand;
import com.system.bank.devops.auth.domain.model.AuthToken;
import reactor.core.publisher.Mono;

public interface LoginUserUseCase {
    Mono<AuthToken> execute(LoginCommand command);
}
