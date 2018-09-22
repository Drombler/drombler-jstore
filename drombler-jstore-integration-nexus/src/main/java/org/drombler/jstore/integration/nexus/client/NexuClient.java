package org.drombler.jstore.integration.nexus.client;

import org.springframework.web.reactive.function.client.WebClient;

public class NexuClient {

    private static final String PING_RESOURCE = "/service/metrics/ping";
    private final NexusClientConfiguration configuration;
    private final WebClient webClient;

    public NexuClient(NexusClientConfiguration configuration) {
        this.configuration = configuration;
        webClient = WebClient.create(configuration.getEndpoint().toString());
    }

    public void ping() {
        webClient.get();
    }
}
