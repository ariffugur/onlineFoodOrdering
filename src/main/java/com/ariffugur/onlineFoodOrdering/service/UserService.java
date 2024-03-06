package com.ariffugur.onlineFoodOrdering.service;

import com.ariffugur.onlineFoodOrdering.model.Cart;
import com.ariffugur.onlineFoodOrdering.model.Role;
import com.ariffugur.onlineFoodOrdering.model.User;
import com.ariffugur.onlineFoodOrdering.repository.UserRepository;
import com.ariffugur.onlineFoodOrdering.response.AuthResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final CartService cartService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(UserRepository userRepository, CartService cartService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.cartService = cartService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        return user.orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    }

    public User createUser(User user) {
        User newUser = User.builder()
                .fullName(user.getFullName())
                .username(user.getUsername())
                .password(bCryptPasswordEncoder.encode(user.getPassword()))
                .role(Role.ROLE_CUSTOMER)
                .build();
        userRepository.save(newUser);
        Cart cart = Cart.builder()
                .customer(newUser)
                .build();
        cartService.save(cart);
        return newUser;
    }
}
