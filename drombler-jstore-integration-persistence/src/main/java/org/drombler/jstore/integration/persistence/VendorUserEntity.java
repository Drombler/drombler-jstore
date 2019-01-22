package org.drombler.jstore.integration.persistence;


import javax.persistence.Entity;

@Entity
public class VendorUserEntity extends AbstractEntity {
    private VendorEntity vendor;
    private String userName;
    private VendorRole role;
}
