package com.eyek.ebook.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    DataSource dataSource;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()

                .authorizeRequests()
                    .anyRequest().permitAll()
                    .and()

                .formLogin()
                    .loginPage("http://localhost:8081/login")
                    .loginProcessingUrl("/perform_login")
                    .defaultSuccessUrl("/", true)
                    .failureUrl("http://localhost:8081/login?error=true");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder builder) throws Exception {
        builder.jdbcAuthentication().dataSource(dataSource)
                .passwordEncoder(new BCryptPasswordEncoder());
    }

    // manually expose the authentication manager as a bean
    // so that it can be autowired
    // reference:
    // https://stackoverflow.com/questions/21633555/how-to-inject-authenticationmanager-using-java-configuration-in-a-custom-filter
    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
