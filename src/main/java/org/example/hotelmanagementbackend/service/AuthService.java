package org.example.hotelmanagementbackend.service;

import lombok.RequiredArgsConstructor;
import org.example.hotelmanagementbackend.dao.RoleDao;
import org.example.hotelmanagementbackend.dao.UserDao;
import org.example.hotelmanagementbackend.entity.Role;
import org.example.hotelmanagementbackend.entity.User;
import org.example.hotelmanagementbackend.exception.UserNameAlreadyExistException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final RoleDao roleDao;
    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;


    public String register(String username, String password, String email, String phoneNumber) {
        if(userDao.existsByUsername(username)) {
            throw new UserNameAlreadyExistException(username);
        }
        var user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setEmail(email);
        user.setPhoneNumber(phoneNumber);

        var role = roleDao.findByRoleName("ROLE_CUSTOMER");
        if(role.isPresent()){
            user.addRole(role.get());
            var savedUser = userDao.save(user);
            return  savedUser.getUsername() + " registered successfully";
        }else {
            var roleUser = new Role();
            roleUser.setRoleName("ROLE_CUSTOMER");
            roleDao.save(roleUser);
            user.addRole(roleUser);
            var savedUser = userDao.save(user);
            return  savedUser.getUsername() + " registered successfully";
        }
    }



    public String login(String username, String password) {
        var auth = new UsernamePasswordAuthenticationToken(username, password);
        Authentication authentication = authenticationManager.authenticate(auth);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        StringBuilder sb = new StringBuilder();
        for (var role : authentication.getAuthorities()) {
            sb.append(role);
        }
        return sb.toString();
    }
}
