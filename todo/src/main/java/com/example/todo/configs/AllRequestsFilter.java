package com.example.todo.configs;

import com.example.todo.dao.AuthTokenDAO;
import com.example.todo.models.AuthToken;
import com.example.todo.models.User;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AllRequestsFilter extends GenericFilter {
    AuthTokenDAO authTokenDAO;
    public AllRequestsFilter(AuthTokenDAO authTokenDAO) {
        this.authTokenDAO = authTokenDAO;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        Authentication authentication = null;
        HttpServletRequest servletRequest = (HttpServletRequest) request;
        //HttpServletResponse servletResponse = (HttpServletResponse) response;
        String authorizationToken = servletRequest.getHeader("Authorization");
        if (authorizationToken != null && authorizationToken.startsWith("Bearer ")){
            String token = authorizationToken.replace("Bearer ", "");
            String tokenData = Jwts.parser()
                    .setSigningKey("Todo".getBytes())
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
            AuthToken authToken = authTokenDAO.findByToken(token);
            User user = authToken.getUser();

            if (user != null) {
                authentication = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), user.getAuthorities());
                System.out.println(authentication);
            }
            else System.out.println("tututututut");

        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }
}
