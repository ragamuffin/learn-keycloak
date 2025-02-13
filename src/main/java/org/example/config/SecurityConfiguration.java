package org.example.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(SecurityConfiguration.class);

    /**
     * User Details Service
     * @return A user details manager
     */
    @Bean
    public UserDetailsService userDetailsService() {
        // Password cannot be stored as plaintext in the latest versions of Spring Security
        BCryptPasswordEncoder pwdEncoder = new BCryptPasswordEncoder();
        final String password = pwdEncoder.encode("password");

        logger.debug ("Encoded password: {}", password);

        UserDetails user = User.builder()
                .username("admin")
                .password(password)
                .roles("USER")
                .build();

        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager(user);
        logger.debug ("Created InMemoryUserDetailsManager");

        return manager;
    }

    /**
     * Password Encoder is a must for a user provided password to match with the actual user password
     * @return Password encoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
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
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }
}
