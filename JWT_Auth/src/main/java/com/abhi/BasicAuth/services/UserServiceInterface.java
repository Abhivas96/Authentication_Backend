package com.abhi.BasicAuth.services;

import com.abhi.BasicAuth.entity.LoginDTO;
import com.abhi.BasicAuth.entity.RegisterDTO;
import org.springframework.http.ResponseEntity;

public interface UserServiceInterface {

    ResponseEntity<String> register(RegisterDTO registerDTO);
    ResponseEntity<String> login(LoginDTO loginDTO);
}
