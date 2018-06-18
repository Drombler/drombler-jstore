package com.drombler.jstore.web.controller.v1.managedcomponent;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.drombler.jstore.web.controller.RestControllerUtils.MANAGED_COMPONENTS_V1_PATH;

@Api(tags = {"ApplicationController"})
@RestController
@RequestMapping(path = MANAGED_COMPONENTS_V1_PATH + "/applications")
public class ApplicationController {

    @GetMapping(path = "/{groupId}/{artifactId}")
    @ApiOperation("Gets the application package file.")
    public HttpEntity<byte[]> getApplication(
            @ApiParam(value = "The Maven groupId of the application", required = true, example = "org.drombler.jstore.client") @PathVariable("groupId") String groupId,
            @ApiParam(value = "The Maven artifactId of the application", required = true, example = "drombler-jstore-client-application") @PathVariable("artifactId") String artifactId,
            @ApiParam(value = "The currently installed Maven version of the application", required = false, example = "0.1.0") @RequestParam(value = "installedVersion", required = false) String installedVersion
    ) {
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_PDF);
//        headers.set(HttpHeaders.CONTENT_DISPOSITION,
//                "attachment; filename=" + fileName.replace(" ", "_"));
//        headers.setContentLength(documentBody.length);
//        UrlResource urlResource = new UrlResource()
//        return new HttpEntity<byte[]>(documentBody, headers);

        return ResponseEntity.ok()
                .build();
    }

}
