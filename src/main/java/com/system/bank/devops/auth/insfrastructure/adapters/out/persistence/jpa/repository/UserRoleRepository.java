package com.system.bank.devops.auth.insfrastructure.adapters.out.persistence.jpa.repository;

import com.system.bank.devops.auth.insfrastructure.adapters.out.persistence.jpa.entity.UserRoleEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends ReactiveCrudRepository<UserRoleEntity, Void> {
}
