package com.example.employeesystem.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                    .antMatchers(HttpMethod.POST, "/**").hasRole(Role.ADMIN.name())
                    .antMatchers(HttpMethod.DELETE, "/**").hasRole(Role.ADMIN.name())
                    .antMatchers(HttpMethod.PATCH, "/**").hasRole(Role.ADMIN.name())

                .and()
                    .httpBasic()

                .and()
                    .csrf().disable();
    }

    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        return new InMemoryUserDetailsManager(
                User.builder()
                        .username("admin")
                        .password(passwordEncoder().encode("123456"))
                        .roles(Role.ADMIN.name())
                        .build(),
                User.builder()
                        .username("user")
                        .password(passwordEncoder().encode("123456"))
                        .roles(Role.USER.name())
                        .build()
        );
    }

    @Bean
    protected PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(12);
    }
}
