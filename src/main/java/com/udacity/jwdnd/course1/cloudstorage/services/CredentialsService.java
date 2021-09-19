package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CredentialsService {
    private final CredentialMapper credentialMapper;
    private final EncryptionService encryptionService;

    public CredentialsService(CredentialMapper credentialMapper, EncryptionService encryptionService) {
        this.credentialMapper = credentialMapper;
        this.encryptionService = encryptionService;
    }

    public List<Credential> getCredentials(Integer userId) {
        return credentialMapper.getCredentials(userId).stream().map(
                credential -> {
                    credential.setDecryptedPassword(
                            encryptionService.decryptValue(
                                    credential.getPassword(),
                                    credential.getKey()
                            )
                    );
                    return credential;
                }
        ).collect(Collectors.toList());
    }

    public void saveCredential(Credential credential) {
        credentialMapper.saveCredential(populateCredentialPassword(credential));
    }

    public void updateCredential(Credential credential) {
        credentialMapper.updateCredential(populateCredentialPassword(credential));
    }

    public void deleteCredentialById(Integer credentialId) {
        credentialMapper.deleteCredential(credentialId);
    }

    private String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    private Credential populateCredentialPassword(Credential credential) {
        String key = generateSalt();
        String encryptedPassword = encryptionService.encryptValue(credential.getPassword(), key);
        credential.setPassword(encryptedPassword);
        credential.setKey(key);
        return credential;
    }
}
