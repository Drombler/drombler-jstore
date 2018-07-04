package com.drombler.jstore.web.controller.v1;

import com.drombler.jstore.managed.jre.JreInfoManager;
import io.swagger.annotations.Api;
import org.drombler.jstore.protocol.json.JreVersionSearchRequest;
import org.drombler.jstore.protocol.json.JreVersionSearchResponse;
import org.drombler.jstore.protocol.json.UpgradableJRE;
import org.springframework.core.io.support.SpringFactoriesLoader;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.drombler.jstore.web.controller.RestControllerUtils.V1_PATH;

@Api(tags = {"JreVersionSearchController V1"})
@RestController("JreVersionSearchControllerV1")
@RequestMapping(path = V1_PATH + "/jre-version-search", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class JreVersionSearchController {

    @PostMapping
    public JreVersionSearchResponse startJreVersionSearch(@RequestBody JreVersionSearchRequest request) {
        List<JreInfoManager> jreVersionManagers = SpringFactoriesLoader.loadFactories(JreInfoManager.class, null);
        JreVersionSearchResponse response = new JreVersionSearchResponse();
        List<UpgradableJRE> upgradableJREs = new ArrayList<>();
        request.getSelectedJREs().forEach(selectedJRE -> {
            Optional<JreInfoManager> optionalJreInfoManager = jreVersionManagers.stream()
                    .filter(jreInfoManager -> jreInfoManager.supportsVendorId(selectedJRE.getJreInfo().getJreVendorId().toLowerCase().trim()))
                    .findFirst();
            if (optionalJreInfoManager.isPresent()) {
                JreInfoManager jreInfoManager = optionalJreInfoManager.get();
                Optional<String> latestUpgradableJREImplementationVersion = jreInfoManager.getLatestUpgradableJREImplementationVersion(request.getSystemInfo(), selectedJRE);

                if (latestUpgradableJREImplementationVersion.isPresent()) {
                    UpgradableJRE jre = new UpgradableJRE();
                    jre.setJreInfo(selectedJRE.getJreInfo());
                    jre.setLatestUpgradableJREImplementationVersion(latestUpgradableJREImplementationVersion.get());
                    upgradableJREs.add(jre);
                }
            }
        });
        response.setUpgradableJREs(upgradableJREs);
        return response;
    }
}
