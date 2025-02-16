package org.example.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(SecurityConfiguration.class);

    @Value("${spring.security.oauth2.resourceserver.jwt.jwk-set-uri}")
    String jwkSetUri;

    @Bean
    JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withJwkSetUri(this.jwkSetUri).build();
    }

    /**
     * Security filter chain will define required authentication based on the URL's
     */
    @Bean
    public SecurityFilterChain greetingsFilterChain(HttpSecurity http) throws Exception {

        logger.debug("Setting up security filter chain");

        // filter URL's that require authentication
        http.securityMatcher("/hello")
                .authorizeHttpRequests( authorize -> authorize.anyRequest().authenticated() )
                .oauth2ResourceServer(oauth -> oauth.jwt(Customizer.withDefaults()));

        return http.build();
    }
}