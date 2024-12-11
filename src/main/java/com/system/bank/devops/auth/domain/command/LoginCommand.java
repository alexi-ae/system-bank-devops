package com.system.bank.devops.auth.domain.command;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class LoginCommand {
    private String email;
    private String password;
}
