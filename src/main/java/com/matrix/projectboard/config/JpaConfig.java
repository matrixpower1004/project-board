package com.matrix.projectboard.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

/**
 * author         : Jason Lee
 * date           : 2023-05-30
 * description    :
 */
@EnableJpaAuditing
@Configuration
public class JpaConfig {

    @Bean
    public AuditorAware<String> auditorAware() {
        return () -> Optional.of("matrix"); // TODO: 스프링 시큐리티로 인증 기능을 붙이게 될 때 수정하자
    }
}
