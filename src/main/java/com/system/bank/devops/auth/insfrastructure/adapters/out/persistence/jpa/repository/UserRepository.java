package com.system.bank.devops.auth.insfrastructure.adapters.out.persistence.jpa.repository;

import com.system.bank.devops.auth.insfrastructure.adapters.out.persistence.jpa.entity.UserEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface UserRepository extends ReactiveCrudRepository<UserEntity, Long> {
    Mono<UserEntity> findFirstByEmail(String s);
}
