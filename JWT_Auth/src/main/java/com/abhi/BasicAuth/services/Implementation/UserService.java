package com.abhi.BasicAuth.services.Implementation;

import com.abhi.BasicAuth.entity.*;
import com.abhi.BasicAuth.exceptions.AuthException;
import com.abhi.BasicAuth.repositories.RoleRepository;
import com.abhi.BasicAuth.repositories.UserRepository;
import com.abhi.BasicAuth.security.JwtTokenProvider;
import com.abhi.BasicAuth.services.UserServiceInterface;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@Service
public class UserService implements UserServiceInterface {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;
    private JwtTokenProvider jwtTokenProvider;


    @Override
    public ResponseEntity<String> register(RegisterDTO registerDTO) {
        if(userRepository.existsByUsername(registerDTO.getUsername()) ||
                userRepository.existsByEmail(registerDTO.getEmail())){
            throw new AuthException("User with given username or email already exists.");
        }

        User user = new User();
        user.setName(registerDTO.getName());
        user.setUsername(registerDTO.getUsername());
        user.setEmail(registerDTO.getEmail());
        String encodedPass = passwordEncoder.encode(registerDTO.getPassword());
        user.setPassword(encodedPass);

        Set<Role> roleSet = new HashSet<>();
        for(Role role : registerDTO.getRoles()){
            System.out.println("Given Roles : " + role.getName());
            Role userRole = roleRepository.findByName("ROLE_"+role.getName());
            System.out.println(userRole);
            if(userRole!=null){
                System.out.println("Given Roles : " + role.getName());
                roleSet.add(userRole);
            }
        }
        user.setRoles(roleSet);
        for(Role roles : user.getRoles()){
            System.out.println("Added roles : "+roles.getName());
        }
        userRepository.save(user);

        return new ResponseEntity<>("User registered successfully!", HttpStatus.CREATED);
    }


    @Override
    public ResponseEntity<JwtAuthResponse> login(LoginDTO loginDTO) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDTO.getUsernameOrEmail(),
                loginDTO.getPassword()
        ));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenProvider.generateToken(authentication);

        JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
        jwtAuthResponse.setAccessToken(token);
        return new ResponseEntity<>(jwtAuthResponse, HttpStatus.OK);
    }
}
