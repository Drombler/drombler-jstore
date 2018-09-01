package org.drombler.jstore.web.controller.v1.managedcomponent;

import org.drombler.jstore.managed.jre.JreInfoManager;
import org.drombler.jstore.model.JStoreErrorCode;
import org.drombler.jstore.model.JStoreException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.drombler.jstore.web.controller.RestControllerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

import static org.drombler.jstore.web.controller.RestControllerUtils.MANAGED_COMPONENTS_V1_PATH;

@Api(tags = {"JreController V1"})
@RestController("JreControllerV1")
@RequestMapping(path = MANAGED_COMPONENTS_V1_PATH + "/jres")
public class JreController {

    @Autowired
    private List<JreInfoManager> jreVersionManagers;

    @GetMapping(path = "/{jreVendorId}/{jreImplementationId}")
    @ApiOperation("Gets the JRE package file.")
    public RedirectView getJRE(
//            @ApiParam(value = "Oracle license agreement", required = true, example = "accept-securebackup-cookie") @RequestHeader(value="oraclelicense") String oraclelicense,
            @ApiParam(value = "The JRE vendor id", required = true, example = "oracle") @PathVariable("jreVendorId") String jreVendorId,
            @ApiParam(value = "The JRE implementation id", required = true, example = "jre-10.0.2_linux-x64_bin.tar.gz") @PathVariable("jreImplementationId") String jreImplementationId
    ) throws JStoreException {
//        Assert.hasText(oraclelicense, "oraclelicense");
//        Assert.isTrue(oraclelicense.equals("accept-securebackup-cookie"), "unsupported oraclelicense value");
        String latestUpgradableJreUrl = jreVersionManagers.stream()
                .filter(jreInfoManager -> jreInfoManager.supportsVendorId(jreVendorId.toLowerCase().trim()))
                .findFirst()
                .orElseThrow(() -> new JStoreException(JStoreErrorCode.JSTORE_UNSUPPORTED_JRE_VENDOR, "Unsupported JRE vendor: " + jreVendorId))
                .getLatestUpgradableJreUrl(jreImplementationId)
                .orElseThrow(() -> new JStoreException(JStoreErrorCode.JSTORE_JRE_IMPLEMENTATION_NOT_FOUND, "No JRE implementation with id: " + jreImplementationId + " found for vendor: " + jreVendorId + "!"));
        return new RedirectView(latestUpgradableJreUrl);

    }

}
