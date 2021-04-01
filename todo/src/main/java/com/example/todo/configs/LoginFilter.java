package com.example.todo.configs;

import com.example.todo.dao.UserDAO;
import com.example.todo.models.AuthToken;
import com.example.todo.models.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginFilter extends UsernamePasswordAuthenticationFilter {
    private UserDAO userDAO;

    public LoginFilter(String url, AuthenticationManager authenticationManager, UserDAO userDAO){
        setFilterProcessesUrl(url);
        setAuthenticationManager(authenticationManager);
        this.userDAO = userDAO;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        User user = null;
        Authentication authenticate = null;
        try {
            user = new ObjectMapper().readValue(request.getInputStream(), User.class);
            authenticate = getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return authenticate;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        String token = Jwts.builder()
                .setSubject(authResult.getName())
                .signWith(SignatureAlgorithm.HS512, "Todo".getBytes())
                .compact();
        User userByUsername = userDAO.findUserByUsername(authResult.getName());
        AuthToken authToken = new AuthToken();
        authToken.setToken(token);
        authToken.setUser(userByUsername);
        userByUsername.getAuthTokens().add(authToken);
        userDAO.save(userByUsername);
        response.addHeader("Authorization", "Bearer " + token);
        chain.doFilter(request, response);
    }
}
