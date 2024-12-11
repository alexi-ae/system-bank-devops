package com.system.bank.devops.auth.domain.port.out;

import com.system.bank.devops.auth.domain.model.User;
import reactor.core.publisher.Mono;

public interface UserPersistencePort {
    Mono<User> create(User model);

    Mono<Void> updateCustomerId(String userId, long customerId);

    Mono<User> getByEmail(String email);

}
