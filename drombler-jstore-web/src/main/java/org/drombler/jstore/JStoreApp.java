package org.drombler.jstore;

import ch.sbb.esta.openshift.gracefullshutdown.GracefulshutdownSpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(JStoreAppConfigurationProperties.class)
@SpringBootApplication
public class JStoreApp {

    public static void main(String[] args) {
        GracefulshutdownSpringApplication.run(JStoreApp.class, args);
    }
}
