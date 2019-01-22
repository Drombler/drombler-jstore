package org.drombler.jstore.web.controller.v1.admin;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.drombler.jstore.model.JStoreException;
import org.drombler.jstore.protocol.json.CreateVendorRequest;
import org.drombler.jstore.protocol.json.VendorInfo;
import org.drombler.jstore.vendor.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;

import static org.drombler.jstore.web.controller.RestControllerUtils.ADMIN_V1_PATH;

@Api(tags = {"VendorController V1"})
@RestController("VendorControllerV1")
@RequestMapping(path = ADMIN_V1_PATH + "/vendors", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class VendorController {

    @Autowired
    private VendorService vendorService;

    @ApiOperation("Registers a new application vendor.")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @PermitAll
    public VendorInfo createVendor(@RequestBody CreateVendorRequest request) throws JStoreException {
        return vendorService.createVendor(request.getVendorInfo());
    }

    @PutMapping(path = "/{vendorId}/verified")
    @RolesAllowed("ADMIN")
    public void verifyVendor(@PathVariable String vendorId){

    }
}
