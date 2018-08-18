package com.drombler.jstore.web.controller.v1.managedcomponent;

import com.drombler.jstore.managed.jre.JreInfoManager;
import com.drombler.jstore.model.JStoreErrorCode;
import com.drombler.jstore.model.JStoreException;
import com.drombler.jstore.web.controller.v1.managedcomponent.converter.SystemInfoNormalizer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.drombler.jstore.protocol.json.JreInfo;
import org.drombler.jstore.protocol.json.SelectedJRE;
import org.drombler.jstore.protocol.json.SystemInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

import static com.drombler.jstore.web.controller.RestControllerUtils.MANAGED_COMPONENTS_V1_PATH;

@Api(tags = {"JreController V1"})
@RestController("JreControllerV1")
@RequestMapping(path = MANAGED_COMPONENTS_V1_PATH + "/jres")
public class JreController {

    @Autowired
    private List<JreInfoManager> jreVersionManagers;

    @GetMapping(path = "/{jreVendorId}/{javaSpecificationVersion}/{osName}/{osArch}")
    @ApiOperation("Gets the application package file.")
    public RedirectView getApplication(
            @ApiParam(value = "The JRE vendor id", required = true, example = "oracle") @PathVariable("jreVendorId") String jreVendorId,
            @ApiParam(value = "The Java specification version", required = true, example = "8") @PathVariable("javaSpecificationVersion") String javaSpecificationVersion,
            @ApiParam(value = "The OS name", required = true, example = "windows") @PathVariable("osName") String osName,
            @ApiParam(value = "The OS architecture", required = true, example = "x64") @PathVariable("osArch") String osArch,
            @ApiParam(value = "The currently installed JRE version", example = "8u161") @RequestParam(value = "installedImplementationVersion", required = false) String installedImplementationVersion,
            @ApiParam(value = "true, if a server version is preferred, else false", example = "false") @RequestParam(value = "headless", required = false) Boolean headless
    ) throws JStoreException {
        SystemInfo systemInfo = new SystemInfo();
        systemInfo.setOsName(osName);
        systemInfo.setOsArch(osArch);
        systemInfo.setHeadless(headless);

        SystemInfoNormalizer systemInfoNormalizer = new SystemInfoNormalizer();
        systemInfoNormalizer.normalizeSystemInfo(systemInfo);

        SelectedJRE selectedJRE = new SelectedJRE();
        JreInfo jreInfo = new JreInfo();
        jreInfo.setJreVendorId(jreVendorId);
        jreInfo.setJavaSpecificationVersion(javaSpecificationVersion);


        selectedJRE.setJreInfo(jreInfo);
        selectedJRE.setInstalledImplementationVersion(installedImplementationVersion);


        String latestUpgradableJreUrl = jreVersionManagers.stream()
                .filter(jreInfoManager -> jreInfoManager.supportsVendorId(jreVendorId.toLowerCase().trim()))
                .findFirst()
                .orElseThrow(() -> new JStoreException(JStoreErrorCode.JSTORE_UNSUPPORTED_JRE_VENDOR, "Unsupported JRE vendor: " + jreVendorId))
                .getLatestUpgradableJreUrl(systemInfo, selectedJRE)
                .orElseThrow(() -> new JStoreException(JStoreErrorCode.JSTORE_NO_UPGRADEABLE_JRE_FOUND, "No upgradable JRE found!"));
        return new RedirectView(latestUpgradableJreUrl);

    }

}
