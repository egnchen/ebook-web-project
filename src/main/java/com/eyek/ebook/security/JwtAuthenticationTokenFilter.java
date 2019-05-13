package com.eyek.ebook.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.eyek.ebook.facade.LoggerFacade;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.core.instrument.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*
JWT filter.
Parse the JWT if it's found in request headers and put it into SecurityContext.
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Value("${jwt.header}")
    private String tokenHeader;

    @Value("${jwt.tokenPrefix}")
    private String tokenPrefix;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String authToken = request.getHeader(this.tokenHeader);
        if (StringUtils.isNotEmpty(authToken) && authToken.startsWith(this.tokenPrefix)) {
            authToken = authToken.substring(this.tokenPrefix.length());
            try {
                DecodedJWT decodedJWT = JWT.decode(authToken);
                String username = objectMapper.readValue(decodedJWT.getSubject(), AuthUserDto.class).getUsername();
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                JwtAuthenticationToken authentication = new JwtAuthenticationToken(userDetails);
                authentication.setToken(authToken);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                LoggerFacade.getLogger(this).debug(String.format("Added auth to context, username : %s", username));
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
        chain.doFilter(request, response);
    }
}
