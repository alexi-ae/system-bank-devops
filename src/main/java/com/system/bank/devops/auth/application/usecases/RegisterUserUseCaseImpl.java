package com.system.bank.devops.auth.application.usecases;

import com.system.bank.devops.auth.application.mapper.UserMapper;
import com.system.bank.devops.auth.domain.command.CreateUserCommand;
import com.system.bank.devops.auth.domain.port.in.RegisterUserUseCase;
import com.system.bank.devops.auth.domain.port.out.UserPersistencePort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class RegisterUserUseCaseImpl implements RegisterUserUseCase {

    @Autowired
    private UserPersistencePort userPersistencePort;

    @Autowired
    private UserMapper userMapper;

    @Override
    public Mono<Void> execute(CreateUserCommand command) {
        return userPersistencePort.create(userMapper.toModel(command)).then();
    }
}
