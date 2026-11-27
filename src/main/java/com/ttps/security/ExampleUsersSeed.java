package com.ttps.security;

import com.ttps.security.entity.User;
import com.ttps.security.repository.UserRepository;
import com.ttps.security.security.Permission;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

/**
 * Carga usuarios de ejemplo al iniciar, sin modificar los usuarios existentes.
 */
@Component
@RequiredArgsConstructor
public class ExampleUsersSeed implements ApplicationRunner {

    public static final String READER_USER = "lector@ejemplo.com";
    public static final String EDITOR_USER = "editor@ejemplo.com";
    public static final String ADMIN_USER = "admin@ejemplo.com";
    // Contraseña pública solo para los usuarios de ejemplo; no usar este seed en producción.
    private static final String USERS_PASSWORD = "1234";
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(@NonNull ApplicationArguments args) {
        createIfAbsent(READER_USER, Set.of(Permission.FOOD_READ));
        createIfAbsent(EDITOR_USER, Set.of(Permission.FOOD_READ, Permission.FOOD_WRITE));
        createIfAbsent(ADMIN_USER, Set.of(Permission.FOOD_READ, Permission.FOOD_WRITE, Permission.ROLE_ADMIN));
    }

    private void createIfAbsent(String email, Set<Permission> permissions) {
        if (userRepository.findByEmail(email).isPresent()) {
            return;
        }

        User user = new User();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(USERS_PASSWORD));
        user.setPermissions(permissions);
        userRepository.save(user);
    }
}
