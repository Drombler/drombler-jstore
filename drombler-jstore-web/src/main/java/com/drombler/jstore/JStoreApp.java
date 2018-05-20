package com.drombler.jstore;

import ch.sbb.esta.openshift.gracefullshutdown.GracefulshutdownSpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JStoreApp {

    public static void main(String[] args) {
        GracefulshutdownSpringApplication.run(JStoreApp.class, args);
    }
}
