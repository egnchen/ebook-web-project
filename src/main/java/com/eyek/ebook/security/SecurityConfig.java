package com.eyek.ebook.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


// helper class to get multi-value parameter from application.yml
@Component
@ConfigurationProperties(prefix="security")
class SecurityConfigFile {

    private List<String> defaultAllowedOrigins = new ArrayList<String>();

    public List<String> getDefaultAllowedOrigins() {
        return this.defaultAllowedOrigins;
    }
}

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    DataSource dataSource;

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    AccessDeniedHandler accessDeniedHandler;

    @Autowired
    JwtAuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    JwtAuthenticationTokenFilter authenticationTokenFilter;

    @Autowired
    AuthenticationSuccessHandler authenticationSuccessHandler;

    @Autowired
    AuthenticationFailureHandler authenticationFailureHandler;

    @Autowired
    SecurityConfigFile securityConfigFile;

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        System.out.println(securityConfigFile.getDefaultAllowedOrigins());
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(securityConfigFile.getDefaultAllowedOrigins());
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "content-type", "x-auth-token"));
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(Long.valueOf(3600));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }


    /*
     this DaoAuthenticationProvider is configured to be the custom authProvider to use
     it gets userDetail from the custom userDetailsService(auto injected as userDetailsServiceImpl)
     and use custom passwordEncoder defined above
     reference:
     https://dzone.com/articles/spring-security-5-form-login-with-database-provide
     */
    // configure default password encoder
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Autowired
    JwtAuthenticationProvider jwtAuthenticationProvider;

    // configure both authentication providers.
    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider());
        auth.authenticationProvider(jwtAuthenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // enable cross-origin resource sharing support
                // for separated front-backend architecture
                .cors().and()
                // disable csrf since we're using JWT
                .csrf().disable()

                // allow request from any user here
                // per-interface privilege is defined in controller methods' annotation, not here.
                .authorizeRequests()
                    // security
                    .anyRequest().permitAll()
                    .and()

                // customized exception handling
                .exceptionHandling().accessDeniedHandler(accessDeniedHandler).and()
                .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint).and()
                // JWT authentication support
                .addFilterBefore(authenticationTokenFilter, UsernamePasswordAuthenticationFilter.class)
                // remove session support, don't need them
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()

                // default username-password form login
                .formLogin()
                    .loginProcessingUrl("/api/auth/login")
                    .usernameParameter("username")
                    .passwordParameter("password")
                    .successHandler(authenticationSuccessHandler)
                    .failureHandler(authenticationFailureHandler)
                    .and()
                // default logout handler
                .logout()
                    .logoutUrl("/api/auth/logout")
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
