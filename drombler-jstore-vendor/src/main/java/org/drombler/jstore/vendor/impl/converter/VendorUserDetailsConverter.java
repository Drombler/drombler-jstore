package org.drombler.jstore.vendor.impl.converter;

import org.drombler.jstore.integration.persistence.VendorUserEntity;
import org.drombler.jstore.protocol.json.VendorRole;
import org.drombler.jstore.protocol.json.VendorUserDetails;
import org.drombler.jstore.protocol.json.VendorUserMapping;

import java.util.List;

import static java.util.stream.Collectors.*;

public class VendorUserDetailsConverter {
    public VendorUserDetails convertVendorUserDetails(List<VendorUserEntity> vendorUserEntities) {
        VendorUserDetails userDetails = new VendorUserDetails();

        vendorUserEntities.stream().
                collect(groupingBy(VendorUserEntity::getUsername, mapping(VendorUserEntity::getRole, toList())))
                .entrySet().stream()
                .map(entry -> createVendorUserMapping(entry.getKey(), entry.getValue()))
                .collect(toList());
        return userDetails;
    }

    private VendorUserMapping createVendorUserMapping(String username, List<VendorRole> roles) {
        VendorUserMapping vendorUserMapping = new VendorUserMapping();
        vendorUserMapping.setUsername(username);
        vendorUserMapping.setRoles(roles);
        return vendorUserMapping;
    }
}
