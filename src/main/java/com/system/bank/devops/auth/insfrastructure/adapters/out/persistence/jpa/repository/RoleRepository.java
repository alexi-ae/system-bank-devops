package com.system.bank.devops.auth.insfrastructure.adapters.out.persistence.jpa.repository;

import com.system.bank.devops.auth.insfrastructure.adapters.out.persistence.jpa.entity.RoleEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface RoleRepository extends ReactiveCrudRepository<RoleEntity, Long> {

    Mono<RoleEntity> findByDescription(String s);

    @Query("SELECT r.id, r.description FROM role r " +
            "JOIN user_roles ur ON r.id = ur.role_id " +
            "WHERE ur.user_id = :userId")
    Flux<RoleEntity> findByUserId(Long userId);
}
