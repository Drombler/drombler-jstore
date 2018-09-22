package org.drombler.jstore.web.controller.v1.managedcomponent;

import org.drombler.jstore.managed.jre.JreInfoManager;
import org.drombler.jstore.model.JStoreException;
import org.drombler.jstore.model.VersionedPlatform;
import org.drombler.jstore.web.controller.v1.managedcomponent.converter.SystemInfoNormalizer;
import io.swagger.annotations.Api;
import org.drombler.jstore.protocol.json.JreVersionSearchRequest;
import org.drombler.jstore.protocol.json.JreVersionSearchResponse;
import org.drombler.jstore.protocol.json.SelectedJRE;
import org.drombler.jstore.protocol.json.UpgradableJRE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.drombler.jstore.web.controller.RestControllerUtils.MANAGED_COMPONENTS_V1_PATH;

@Api(tags = {"JreVersionSearchController V1"})
@RestController("JreVersionSearchControllerV1")
@RequestMapping(path = MANAGED_COMPONENTS_V1_PATH + "/jre-version-search", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class JreVersionSearchController {
    @Autowired
    private List<JreInfoManager> jreVersionManagers;

//    @PostConstruct
//    public void init() {
//        jreVersionManagers = SpringFactoriesLoader.loadFactories(JreInfoManager.class, JreInfoManager.class.getClassLoader());
//    }

    @PostMapping
    public JreVersionSearchResponse startJreVersionSearch(@RequestBody JreVersionSearchRequest request) throws JStoreException {
        SystemInfoNormalizer systemInfoNormalizer = new SystemInfoNormalizer();
        systemInfoNormalizer.normalizeSystemInfo(request.getSystemInfo());

        JreVersionSearchResponse response = new JreVersionSearchResponse();
        List<UpgradableJRE> upgradableJREs = new ArrayList<>();
        for (SelectedJRE selectedJRE : request.getSelectedJREs()) {
            Optional<JreInfoManager> optionalJreInfoManager = jreVersionManagers.stream()
                    .filter(jreInfoManager -> jreInfoManager.supportsVendorId(selectedJRE.getJreInfo().getJreVendorId().toLowerCase().trim()))
                    .findFirst();
            if (optionalJreInfoManager.isPresent()) {
                JreInfoManager jreInfoManager = optionalJreInfoManager.get();
                Optional<VersionedPlatform> latestUpgradableJREImplementationVersion = jreInfoManager.getLatestUpgradableJREImplementationVersion(request.getSystemInfo(), selectedJRE);

                if (latestUpgradableJREImplementationVersion.isPresent()) {
                    VersionedPlatform versionedPlatform = latestUpgradableJREImplementationVersion.get();
                    UpgradableJRE jre = new UpgradableJRE();
                    jre.setJreInfo(selectedJRE.getJreInfo());
                    jre.setLatestUpgradableJREImplementationVersion(versionedPlatform.getCategory().getJreImplementationVersionString());
                    jre.setJreImplementationId(versionedPlatform.getJreImplementationId());
                    jre.setJreImplementationFileName(versionedPlatform.getJreImplementationFileName());
                    jre.getChecksums().addAll(versionedPlatform.getPlatform().getChecksums());
                    upgradableJREs.add(jre);
                }
            }
        }
        response.setUpgradableJREs(upgradableJREs);
        return response;
    }
}
