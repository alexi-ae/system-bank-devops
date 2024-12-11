package com.system.bank.devops.auth.insfrastructure.adapters.in;

import com.system.bank.devops.auth.insfrastructure.adapters.in.handlers.AuthHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterConfig {
    @Bean
    public RouterFunction<ServerResponse> routeAuth(AuthHandler handler) {
        return route(POST("/login"), handler::login)
                .andRoute(POST("/register"), handler::register);
    }
}
