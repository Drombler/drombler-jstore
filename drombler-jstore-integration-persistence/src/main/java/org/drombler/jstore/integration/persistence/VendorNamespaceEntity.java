package org.drombler.jstore.integration.persistence;

import javax.persistence.Entity;

@Entity
public class VendorNamespaceEntity extends AbstractEntity {

    private VendorEntity vendor;
    private String namespace;
}
