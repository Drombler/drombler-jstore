package org.drombler.jstore.integration.persistence;

import org.drombler.jstore.protocol.json.VendorRole;

import javax.persistence.Embeddable;

@Embeddable
public class VendorUserRoleMapping {
    private String username;
    private VendorRole role;
}
