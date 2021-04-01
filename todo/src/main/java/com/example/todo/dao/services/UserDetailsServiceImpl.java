package com.example.todo.dao.services;

import com.example.todo.dao.UserDAO;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Qualifier("udsi")
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private UserDAO userDAO;
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return userDAO.findUserByUsername(s);
    }
}
