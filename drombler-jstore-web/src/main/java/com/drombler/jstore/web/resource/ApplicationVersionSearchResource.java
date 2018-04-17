package com.drombler.jstore.web.resource;

import io.swagger.annotations.Api;
import org.drombler.jstore.protocol.json.ApplicationVersionInfo;
import org.drombler.jstore.protocol.json.ApplicationVersionSearchRequest;
import org.drombler.jstore.protocol.json.ApplicationVersionSearchResponse;
import org.drombler.jstore.protocol.json.JreInfo;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.stream.Collectors;

@Api(tags = {"ApplicationVersionSearchResource"})
@Path("/application-version-search")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ApplicationVersionSearchResource {

    @POST
    public ApplicationVersionSearchResponse startApplicationVersionSearch(ApplicationVersionSearchRequest request){
        ApplicationVersionSearchResponse response = new ApplicationVersionSearchResponse();
        List<ApplicationVersionInfo> infos = request.getApplicationIds().stream()
                .map(applicationId -> {
                    switch (applicationId.getVendorApplicationId()) {
                        case "drombler-jstore-client":
                            ApplicationVersionInfo info = new ApplicationVersionInfo();
                            info.setApplicationId(applicationId);
                            info.setApplicationVersion("0.1-SNAPTHOT");
                            JreInfo jreInfo = new JreInfo();
                            jreInfo.setJreVendorId("oracle");
                            jreInfo.setJavaMajorVersion("8");
                            info.setJreInfo(jreInfo);
                            return info;
                        case "drombler-jstore-client-service":
                            ApplicationVersionInfo info2 = new ApplicationVersionInfo();
                            info2.setApplicationId(applicationId);
                            info2.setApplicationVersion("0.1-SNAPTHOT");
                            JreInfo jreInfo2 = new JreInfo();
                            jreInfo2.setJreVendorId("drombler");
                            jreInfo2.setJavaMajorVersion("9");
                            jreInfo2.setCustomJREId("drombler-base");
                            jreInfo2.getModules().add("java.base");
                            info2.setJreInfo(jreInfo2);
                            return info2;
                        default:
                            //TODO
                            return null;
                    }
                })
                .collect(Collectors.toList());
        return response;
    }
}
