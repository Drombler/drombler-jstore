package org.drombler.jstore.integration.nexus.client;

import org.drombler.jstore.integration.nexus.NexusClientConfigurationProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;

public class NexusClient {

    private static final String PING_RESOURCE = "/service/metrics/ping";
    private final NexusClientConfigurationProperties configurationProperties;
    private final WebClient webClient;

    @Autowired
    public NexusClient(NexusClientConfigurationProperties configurationProperties) {
        this.configurationProperties = configurationProperties;
        webClient = WebClient.create(configurationProperties.getEndpoint().toString());
    }

    public void ping() {
        webClient.get();
    }

    public void createProxy(String vendorId) {

    }

    public void addRepoToGroup() {

    }
}
