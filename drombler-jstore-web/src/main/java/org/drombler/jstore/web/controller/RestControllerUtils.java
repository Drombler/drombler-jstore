package org.drombler.jstore.web.controller;

public final class RestControllerUtils {
    public static final String V1_PATH_SEGMENT = "v1";
    public static final String MANAGED_COMPONENTS_PATH_SEGMENT = "managed-components";
    public static final String WEB_RESOURCES_PATH = "/webresources";
    public static final String V1_PATH = WEB_RESOURCES_PATH + "/" + V1_PATH_SEGMENT;
    public static final String MANAGED_COMPONENTS_V1_PATH = V1_PATH + "/" + MANAGED_COMPONENTS_PATH_SEGMENT;

    private RestControllerUtils() {
    }
}
