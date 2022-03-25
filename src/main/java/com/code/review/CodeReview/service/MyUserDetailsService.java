package com.code.review.CodeReview.service;

import com.code.review.CodeReview.model.Users;
import com.code.review.CodeReview.model.UserPrincipal;
import com.code.review.CodeReview.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = userRepository.findByUserName(username);

        if(user==null){
            throw new UsernameNotFoundException("Users not found!");
        }
        return new UserPrincipal(user);
    }
}
