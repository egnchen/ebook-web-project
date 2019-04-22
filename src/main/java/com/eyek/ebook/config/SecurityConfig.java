package com.eyek.ebook.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    DataSource dataSource;

    @Autowired
    UserDetailsService userDetailsService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /*
     this DaoAuthenticationProvider is configured to be the custom authProvider to use
     it gets userDetail from the custom userDetailsService(auto injected as userDetailsServiceImpl)
     which fetches user information from userRepository
     and use custom passwordEncoder defined above
     reference:
     https://dzone.com/articles/spring-security-5-form-login-with-database-provide
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()

                .authorizeRequests()
                    .anyRequest().permitAll()
                    .and()

                .formLogin()
                    .usernameParameter("username")
                    .passwordParameter("password")
                    .permitAll()
                    .loginPage("http://localhost:8081/login")
                    .loginProcessingUrl("/api/auth/login")
                    .successForwardUrl("/api/auth/post-login")
                    .failureUrl("http://localhost:8081/login?error=true")
                    .and()

                .logout()
                    .logoutUrl("/api/auth/logout")
                    .logoutSuccessUrl("/logout")
                    .permitAll();
    }

    /*
     manually expose the authentication manager as a bean
     so that it can be autowired
     reference:
     https://stackoverflow.com/questions/21633555/how-to-inject-authenticationmanager-using-java-configuration-in-a-custom-filter
     */
    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
