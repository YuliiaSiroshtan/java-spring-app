package com.security.service.model;

import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table
public class UserScopes implements GrantedAuthority {
    private Long id;
    private String name;
    private Set<String> scopes;
    @Override
    public String getAuthority() {
        return name;
    }
}
