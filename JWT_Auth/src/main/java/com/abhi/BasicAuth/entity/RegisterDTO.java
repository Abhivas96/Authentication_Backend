package com.abhi.BasicAuth.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RegisterDTO {
    private String name;
    private String username;
    private String email;
    private String password;
    private Set<Role> roles;
}
