package org.drombler.jstore.integration.persistence;

import javax.persistence.Embeddable;

@Embeddable
public class VendorUserRoleMapping {
    private String userName;
    private VendorRole role;
}
