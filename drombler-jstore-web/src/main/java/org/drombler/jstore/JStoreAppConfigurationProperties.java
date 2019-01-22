package org.drombler.jstore;

import org.drombler.jstore.integration.nexus.NexusClientConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.context.annotation.Bean;

@ConfigurationProperties(prefix = "jstore")
public class JStoreAppConfigurationProperties {

    @NestedConfigurationProperty
    private NexusClientConfigurationProperties nexus = new NexusClientConfigurationProperties();

    @Bean
    public NexusClientConfigurationProperties getNexus() {
        return nexus;
    }
}
