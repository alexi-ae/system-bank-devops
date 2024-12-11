package com.system.bank.devops.auth.domain.port.out;

import com.system.bank.devops.auth.domain.model.Token;
import com.system.bank.devops.auth.domain.model.User;
import org.springframework.stereotype.Service;

public interface TokenPort {
    String generate(User model);

    Token getInfo(String token);
}
