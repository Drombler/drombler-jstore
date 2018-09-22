package org.drombler.jstore;

import org.drombler.jstore.integration.nexus.NexusConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.context.annotation.Bean;

@ConfigurationProperties(prefix = "jstore")
public class JStoreAppConfigurationProperties {

    @NestedConfigurationProperty
    private NexusConfigurationProperties nexus = new NexusConfigurationProperties();

    @Bean
    public NexusConfigurationProperties getNexus() {
        return nexus;
    }
}
