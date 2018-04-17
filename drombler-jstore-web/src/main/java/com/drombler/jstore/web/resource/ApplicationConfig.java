package com.drombler.jstore.web.resource;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

/**
 * TODO: Required in Wildfly Swarm?
 *
 * @author puce
 */
@ApplicationPath("webresources")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method. It is automatically populated with all resources defined in the
     * project. If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(
            Set<Class<?>> resources) {
        resources.add(com.drombler.jstore.web.resource.HelloWorldEndpoint.class);
    }

}
