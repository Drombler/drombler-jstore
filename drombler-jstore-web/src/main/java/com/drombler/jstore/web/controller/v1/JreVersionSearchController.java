package com.drombler.jstore.web.controller.v1;

import io.swagger.annotations.Api;
import org.drombler.jstore.protocol.json.*;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static com.drombler.jstore.web.controller.RestControllerUtils.V1_PATH;

@Api(tags = {"JreVersionSearchController V1"})
@RestController("JreVersionSearchControllerV1")
@RequestMapping(path = V1_PATH + "/jre-version-search", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class JreVersionSearchController {

    @PostMapping
    public JreVersionSearchResponse startApplicationVersionSearch(@RequestBody JreVersionSearchRequest request) {
        JreVersionSearchResponse response = new JreVersionSearchResponse();
        List<UpgradableJRE> upgradableJREs = request.getSelectedJREs().stream()
                .map(selectedJRE -> {
                    switch (selectedJRE.getJreInfo().getJreVendorId().toLowerCase().trim()) {
                        case "oracle":
                            UpgradableJRE jre = new UpgradableJRE();
                            jre.setJreInfo(selectedJRE.getJreInfo());
                            jre.setLatestUpgradableJREImplementationVersion("8u172");
                            return jre;
                        default:
                            //TODO
                            return null;
                    }
                })
                .collect(Collectors.toList());
        response.setUpgradableJREs(upgradableJREs);
        return response;
    }
}
