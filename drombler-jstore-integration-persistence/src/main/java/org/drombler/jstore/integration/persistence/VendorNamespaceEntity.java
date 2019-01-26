package org.drombler.jstore.integration.persistence;

import org.softsmithy.lib.persistence.AbstractEntity;

import javax.persistence.Entity;

@Entity
public class VendorNamespaceEntity extends AbstractEntity {

    private VendorEntity vendor;
    private String namespace;
}
