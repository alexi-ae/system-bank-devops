package com.system.bank.devops.auth.application.usecases;

import com.system.bank.devops.auth.domain.command.LoginCommand;
import com.system.bank.devops.auth.domain.model.AuthToken;
import com.system.bank.devops.auth.domain.port.in.LoginUserUseCase;
import com.system.bank.devops.auth.domain.port.out.TokenPort;
import com.system.bank.devops.auth.domain.port.out.UserPersistencePort;
import com.system.bank.devops.core.exception.ApiRestException;
import com.system.bank.devops.core.exception.ErrorReason;
import com.system.bank.devops.core.exception.ErrorSource;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class LoginUserUseCaseImpl implements LoginUserUseCase {

    @Autowired
    private UserPersistencePort userPersistencePort;

    @Autowired
    private TokenPort tokenPort;

    @Autowired
    private ReactiveAuthenticationManager authenticationManager;


    @SneakyThrows
    @Override
    public Mono<AuthToken> execute(LoginCommand command) {

        Authentication authRequest = new UsernamePasswordAuthenticationToken(command.getEmail(), command.getPassword());
        return authenticationManager.authenticate(authRequest)
                .flatMap(auth ->
                        userPersistencePort.getByEmail(command.getEmail())
                                .flatMap(user -> Mono.just(tokenPort.generate(user)))
                                .flatMap(tokenString -> Mono.just(AuthToken.builder()
                                        .token(tokenString)
                                        .build())))
                .onErrorResume(ApiRestException.class, ex -> {
                    ApiRestException apiRestException = ApiRestException.builder()
                            .reason(ErrorReason.UNAUTHORIZED)
                            .source(ErrorSource.BUSINESS_SERVICE)
                            .build();
                    return Mono.error(apiRestException);
                });
//                .onErrorResume( AuthenticationException.class, e -> Mono.error(new RuntimeException("Credenciales inválidas")));
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("USER_DISABLED", e);
//            throw ApiRestException.builder().reason(ErrorReason.AUTHENTICATION_FAILED)
//                    .source(ErrorSource.BUSINESS_SERVICE).build();
        }
    }
}
