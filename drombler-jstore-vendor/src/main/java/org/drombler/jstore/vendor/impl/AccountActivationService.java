package org.drombler.jstore.vendor.impl;

public interface AccountActivationService {
    String createActivationCode();

    void sendActivationEmail(String emailAddress, String activationCode);
}
