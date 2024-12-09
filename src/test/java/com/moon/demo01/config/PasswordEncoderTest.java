package com.moon.demo01.config;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class PasswordEncoderTest {

    @Test
    void passwordEncoder_ShouldEncodePassword() {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String rawPassword = "password123";
        String encodedPassword = passwordEncoder.encode(rawPassword);
        log.info("Raw Password: {}", rawPassword);
        log.info("Encoded Password: {}", encodedPassword);

        assertThat(encodedPassword).isNotEqualTo(rawPassword);
        assertThat(passwordEncoder.matches(rawPassword, encodedPassword)).isTrue();

        boolean isMatched = passwordEncoder.matches(rawPassword, encodedPassword);
        log.info("Password matches: {}", isMatched);


    }
}
