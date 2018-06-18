package com.drombler.jstore.integration.nexus.client;

import java.net.URI;

public class NexusClientConfiguration {
    private URI endpoint;
    private String allRepo;
    private String freeOnlyRepo;
    private String paidOnlyRepo;

    public URI getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(URI endpoint) {
        this.endpoint = endpoint;
    }

    public String getAllRepo() {
        return allRepo;
    }

    public void setAllRepo(String allRepo) {
        this.allRepo = allRepo;
    }

    public String getFreeOnlyRepo() {
        return freeOnlyRepo;
    }

    public void setFreeOnlyRepo(String freeOnlyRepo) {
        this.freeOnlyRepo = freeOnlyRepo;
    }

    public String getPaidOnlyRepo() {
        return paidOnlyRepo;
    }

    public void setPaidOnlyRepo(String paidOnlyRepo) {
        this.paidOnlyRepo = paidOnlyRepo;
    }
}
