package com.udacity.jwdnd.course1.cloudstorage.service;

import com.udacity.jwdnd.course1.cloudstorage.converter.CredentialConverter;
import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialsMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialsService {

    private CredentialsMapper credentialsMapper;
    private EncryptionService encryptionService;

    public CredentialsService(CredentialsMapper credentialsMapper, EncryptionService encryptionService) {
        this.credentialsMapper = credentialsMapper;
        this.encryptionService = encryptionService;
    }

    public List<Credential> getUserCredentials(Integer userId) {
        List<Credential> credentials = credentialsMapper.getUserCredentials(userId);
        credentials.forEach(credential -> {
            String decryptedPassword = encryptionService.decryptValue(credential.getPassword(), credential.getKey());
            credential.setDecryptedPassword(decryptedPassword);
        });

        return credentials;
    }

    public int saveCredential(CredentialForm credentialForm, Integer userId) {
        Credential credential = CredentialConverter.credentialFormToCredential(credentialForm);

        if (credential.getCredentialId() == null) {
            generateKeyAndEncryptPassword(credential);
            credential.setUserId(userId);
            return credentialsMapper.insert(credential);
        } else {
            Credential existedCredential = credentialsMapper.getCredential(credential.getCredentialId());
            if (existedCredential != null && userId.equals(existedCredential.getUserId())) {
                generateKeyAndEncryptPassword(credential);
                return credentialsMapper.update(credential);
            } else {
                return -1;
            }
        }
    }

    public int deleteCredential(Integer credentialId, Integer userId) {
        Credential existedCredential = credentialsMapper.getCredential(credentialId);
        if (existedCredential != null && userId.equals(existedCredential.getUserId())) {
            return credentialsMapper.delete(credentialId);
        } else {
            return -1;
        }
    }

    private void generateKeyAndEncryptPassword(Credential credential) {
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        String encryptedPassword = encryptionService.encryptValue(credential.getDecryptedPassword(), encodedKey);
        credential.setKey(encodedKey);
        credential.setPassword(encryptedPassword);
    }

}
