package com.drombler.jstore.web.controller.v1;

import io.swagger.annotations.Api;
import org.drombler.jstore.protocol.json.ApplicationVersionInfo;
import org.drombler.jstore.protocol.json.ApplicationVersionSearchRequest;
import org.drombler.jstore.protocol.json.ApplicationVersionSearchResponse;
import org.drombler.jstore.protocol.json.JreInfo;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static com.drombler.jstore.web.controller.RestControllerUtils.V1_PATH;

@Api(tags = {"ApplicationVersionSearchController"})
@RestController
@RequestMapping(path = V1_PATH + "/application-version-search", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class ApplicationVersionSearchController {

    @PostMapping
    public ApplicationVersionSearchResponse startApplicationVersionSearch(@RequestBody ApplicationVersionSearchRequest request) {
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
        response.setApplicationVersionInfos(infos);
        return response;
    }
}
