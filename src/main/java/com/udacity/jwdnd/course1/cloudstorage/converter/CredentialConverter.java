package com.udacity.jwdnd.course1.cloudstorage.converter;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;

public class CredentialConverter {

    private CredentialConverter() {}

    public static Credential credentialFormToCredential(CredentialForm credentialForm) {
        Credential credential = new Credential();

        credential.setCredentialId(credentialForm.getId());
        credential.setUrl(credentialForm.getUrl());
        credential.setUsername(credentialForm.getUsername());
        credential.setPassword(credentialForm.getPassword());

        return credential;
    }

}
