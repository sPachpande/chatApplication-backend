package com.chatApplication.backend.config.security;

import com.chatApplication.backend.user.service.ApplicationUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

    private final ApplicationUserService applicationUserService;

    @Autowired
    public SecurityConfig(ApplicationUserService applicationUserService) {
        this.applicationUserService = applicationUserService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors()
                .and().authorizeRequests()
                .antMatchers(HttpMethod.POST,"/users").permitAll()
//                .antMatchers(HttpMethod.GET,"/messages").permitAll()
                .anyRequest().authenticated()
                .and().httpBasic()
                .and().csrf().disable();
    }

    @Bean
    DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder());
        daoAuthenticationProvider.setUserDetailsService(applicationUserService);
        return daoAuthenticationProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider());
    }

}
