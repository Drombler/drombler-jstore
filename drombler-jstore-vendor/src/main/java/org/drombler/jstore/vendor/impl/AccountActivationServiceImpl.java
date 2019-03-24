package org.drombler.jstore.vendor.impl;

import org.drombler.commons.spring.jpa.stereotype.TransactionalService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

@TransactionalService
public class AccountActivationServiceImpl implements AccountActivationService {

    @Override
    public String createActivationCode() {
        return UUID.randomUUID().toString();
    }

    @Override
    public void sendActivationEmail(String emailAddress, String activationCode) {
// TODO: send email
    }
}
