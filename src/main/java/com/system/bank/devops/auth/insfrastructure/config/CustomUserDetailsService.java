package com.system.bank.devops.auth.insfrastructure.config;

import com.system.bank.devops.auth.insfrastructure.adapters.out.persistence.jpa.repository.RoleRepository;
import com.system.bank.devops.auth.insfrastructure.adapters.out.persistence.jpa.repository.UserRepository;
import com.system.bank.devops.core.exception.ApiRestException;
import com.system.bank.devops.core.exception.ErrorReason;
import com.system.bank.devops.core.exception.ErrorSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class CustomUserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    public Mono<UserDetails> loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findFirstByEmail(username)
                .switchIfEmpty(Mono.error(ApiRestException.builder().source(ErrorSource.BUSINESS_SERVICE).reason(ErrorReason.UNAUTHORIZED).build()))
                .flatMap(userEntity ->
                        roleRepository.findByUserId(userEntity.getId()) // Obtiene los roles
                                .map(roleEntity -> new SimpleGrantedAuthority(roleEntity.getDescription())) // Convierte a SimpleGrantedAuthority
                                .collectList() // Convierte el Flux a List
                                .map(roles ->
                                        User.withUsername(userEntity.getEmail()) // Crea UserDetails
                                                .password(userEntity.getPassword())
                                                .authorities(roles)
                                                .build()
                                )
                );
    }
}
