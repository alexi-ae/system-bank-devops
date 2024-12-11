package com.system.bank.devops.auth.insfrastructure.config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Component;

@Component
public class DatabaseInitializer {
    @Autowired
    private DatabaseClient databaseClient;

    @PostConstruct
    public void initializeDatabase() {
        createRole();
        createUser();
        createUserRole();
    }

    void createUser() {
        String createTableQuery = """
                    CREATE TABLE IF NOT EXISTS users (
                        id BIGSERIAL PRIMARY KEY, -- Auto-incremental
                        email VARCHAR(100) NOT NULL,
                        password VARCHAR(100) NOT NULL,
                        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                        updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                        customer_id BIGINT
                    );
                """;

        databaseClient.sql(createTableQuery)
                .then()
                .subscribe();
    }

    void createRole() {
        String createTableQuery = """
                    CREATE TABLE IF NOT EXISTS role (
                        id BIGSERIAL PRIMARY KEY, -- Auto-incremental
                        description VARCHAR(100) NOT NULL
                    );
                """;

        databaseClient.sql(createTableQuery)
                .then()
                .subscribe();
    }

    void createUserRole() {
        String createTableQuery = """
                      CREATE TABLE IF NOT EXISTS  user_roles (
                         user_id BIGINT NOT NULL,
                         role_id BIGINT NOT NULL,
                         PRIMARY KEY (user_id, role_id),
                         FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
                         FOREIGN KEY (role_id) REFERENCES role (id) ON DELETE CASCADE
                      );
                """;

        databaseClient.sql(createTableQuery)
                .then()
                .subscribe();
    }
}
