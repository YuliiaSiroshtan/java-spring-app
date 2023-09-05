package com.vault.service;

import com.vault.model.JwtSecret;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.vault.core.VaultKeyValueOperations;
import org.springframework.vault.core.VaultKeyValueOperationsSupport;
import org.springframework.vault.core.VaultTemplate;
import org.springframework.vault.support.VaultResponseSupport;

import java.util.Objects;

@Service
public class JwtSecretService {

    private VaultKeyValueOperations vaultKeyValueOperations;
    private final VaultTemplate vaultTemplate;

    @Value("${vault.appName}")
    private String appName;
    @Value("${vault.path-to-jwt-secret}")
    private String path;

    @Autowired
    public JwtSecretService(VaultTemplate vaultTemplate){
        this.vaultTemplate = vaultTemplate;
    }

    @PostConstruct
    public void initJwtSecretService() {
        this.vaultKeyValueOperations = this.vaultTemplate.opsForKeyValue(appName, VaultKeyValueOperationsSupport.KeyValueBackend.KV_2);
    }

    public String accessToJwtSecret() {
        VaultResponseSupport<JwtSecret> response = vaultKeyValueOperations.get(path, JwtSecret.class);
        assert response != null;
        return Objects.requireNonNull(response.getData()).getJwtSecret();
    }
}

