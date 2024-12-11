package com.system.bank.devops.auth.insfrastructure.adapters.out.persistence.jpa;

import com.system.bank.devops.auth.domain.model.User;
import com.system.bank.devops.auth.domain.port.out.UserPersistencePort;
import com.system.bank.devops.auth.insfrastructure.adapters.out.persistence.jpa.entity.UserRoleEntity;
import com.system.bank.devops.auth.insfrastructure.adapters.out.persistence.jpa.mapper.UserEntityMapper;
import com.system.bank.devops.auth.insfrastructure.adapters.out.persistence.jpa.repository.RoleRepository;
import com.system.bank.devops.auth.insfrastructure.adapters.out.persistence.jpa.repository.UserRepository;
import com.system.bank.devops.auth.insfrastructure.adapters.out.persistence.jpa.repository.UserRoleRepository;
import com.system.bank.devops.core.exception.ApiRestException;
import com.system.bank.devops.core.exception.ErrorReason;
import com.system.bank.devops.core.exception.ErrorSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@Transactional
public class UserJpaAdapter implements UserPersistencePort {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private UserEntityMapper userEntityMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Mono<User> create(User model) {
        return userRepository.findFirstByEmail(model.getEmail())
                .flatMap(existingUser -> Mono.<User>error(
                        ApiRestException.builder()
                                .reason(ErrorReason.USER_ALREADY_EXISTS)
                                .source(ErrorSource.BUSINESS_SERVICE)
                                .build()
                ))
                .switchIfEmpty(registerNewUser(model));
    }

    private Mono<User> registerNewUser(User model) {
        model.setPassword(passwordEncoder.encode(model.getPassword()));
        return userEntityMapper.toEntity(model)
                .flatMap(userRepository::save)
                .flatMap(userEntity ->
                        roleRepository.findByDescription("USER")
                                .switchIfEmpty(Mono.error(new IllegalArgumentException("No has role")))
                                .map(roleEntity -> UserRoleEntity.builder()
                                        .roleId(roleEntity.getId())
                                        .userId(userEntity.getId())
                                        .build())
                                .flatMap(userRoleRepository::save)
                                .map(userRoleEntity -> userEntity)
                )
                .flatMap(userEntityMapper::toModel);
    }

    @Override
    public Mono<Void> updateCustomerId(String userId, long customerId) {

        return userRepository.findById(Long.getLong(userId))
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Usuario no encontrado el usuario: " + userId)))
                .flatMap(userEntity -> {
                    userEntity.setCustomerId(customerId);
                    return Mono.just(userEntity);
                })
                .flatMap(userRepository::save)
                .flatMap(userEntityMapper::toModel).then();
    }

    @Override
    public Mono<User> getByEmail(String email) {
        return userRepository.findFirstByEmail(email)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Usuario no encontrado con correo: " + email)))
                .flatMap(userEntityMapper::toModel)
                .doOnError(e -> System.out.println("Error al buscar usuario por email: " + e.getMessage()));
    }
}
