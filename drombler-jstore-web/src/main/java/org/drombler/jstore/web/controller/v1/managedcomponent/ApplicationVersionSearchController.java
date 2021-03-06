package org.drombler.jstore.web.controller.v1.managedcomponent;

import org.drombler.jstore.web.controller.v1.managedcomponent.converter.SystemInfoNormalizer;
import io.swagger.annotations.Api;
import org.drombler.jstore.protocol.json.ApplicationVersionSearchRequest;
import org.drombler.jstore.protocol.json.ApplicationVersionSearchResponse;
import org.drombler.jstore.protocol.json.UpgradableApplication;
import org.drombler.jstore.web.controller.RestControllerUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static org.drombler.jstore.web.controller.RestControllerUtils.MANAGED_COMPONENTS_V1_PATH;

@Api(tags = {"ApplicationVersionSearchController V1"})
@RestController("ApplicationVersionSearchControllerV1")
@RequestMapping(path = MANAGED_COMPONENTS_V1_PATH + "/application-version-search", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class ApplicationVersionSearchController {

    @PostMapping
    public ApplicationVersionSearchResponse startApplicationVersionSearch(@RequestBody ApplicationVersionSearchRequest request) {
        SystemInfoNormalizer systemInfoNormalizer = new SystemInfoNormalizer();
        systemInfoNormalizer.normalizeSystemInfo(request.getSystemInfo());

        ApplicationVersionSearchResponse response = new ApplicationVersionSearchResponse();
        List<UpgradableApplication> upgradableApplications = request.getSelectedApplications().stream()
                .map(selectedApplication -> {
                    switch (selectedApplication.getApplicationId().getArtifactId()) {
                        case "drombler-jstore-client-application":
                            UpgradableApplication app = new UpgradableApplication();
                            app.setApplicationId(selectedApplication.getApplicationId());
                            app.setLatestUpgradableApplicationVersion("0.1-SNAPTHOT");
                            return app;
                        case "drombler-jstore-client-agent-application":
                            UpgradableApplication app2 = new UpgradableApplication();
                            app2.setApplicationId(selectedApplication.getApplicationId());
                            app2.setLatestUpgradableApplicationVersion("0.1-SNAPTHOT");
                            return app2;
                        default:
                            //TODO
                            return null;
                    }
                })
                .collect(Collectors.toList());
        response.setUpgradableApplications(upgradableApplications);
        return response;
    }
}
