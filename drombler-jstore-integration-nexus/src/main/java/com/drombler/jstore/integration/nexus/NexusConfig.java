package com.drombler.jstore.integration.nexus;

import com.drombler.jstore.integration.nexus.client.NexuClient;
import com.drombler.jstore.integration.nexus.client.NexusClientConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class NexusConfig {

    @Bean
    public NexusClientConfiguration createNexusClientConfiguration(NexusConfigurationProperties nexusConfigurationProperties) {
        NexusClientConfiguration nexusClientConfiguration = new NexusClientConfiguration();
        nexusClientConfiguration.setEndpoint(nexusClientConfiguration.getEndpoint());
        nexusClientConfiguration.setAllRepo(nexusConfigurationProperties.getAllRepo());
        nexusClientConfiguration.setFreeOnlyRepo(nexusConfigurationProperties.getFreeOnlyRepo());
        nexusClientConfiguration.setPaidOnlyRepo(nexusConfigurationProperties.getPaidOnlyRepo());
        return nexusClientConfiguration;
    }

    @Bean
    public NexuClient createNexuClient(WebClient.Builder builder, NexusClientConfiguration nexusClientConfiguration) {
        return new NexuClient(nexusClientConfiguration);
    }
}
