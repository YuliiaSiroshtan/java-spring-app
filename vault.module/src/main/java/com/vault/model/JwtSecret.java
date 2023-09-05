package com.vault.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.vault.repository.mapping.Secret;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Secret
public class JwtSecret {
    private String jwtSecret;
}