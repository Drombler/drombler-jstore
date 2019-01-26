package org.drombler.jstore.integration.persistence;


import org.drombler.commons.spring.jpa.AbstractAuditableEntity;
import org.softsmithy.lib.persistence.AbstractEntity;
import org.springframework.data.jpa.domain.AbstractAuditable;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import static org.drombler.jstore.integration.persistence.VendorUserEntity.ALLOCATION_SIZE;
import static org.softsmithy.lib.persistence.AbstractEntity.PRIMARY_GENERATOR;

@Entity
@SequenceGenerator(name = PRIMARY_GENERATOR, sequenceName = "VENDOR_USER_SEQ", allocationSize = ALLOCATION_SIZE)
@Table(name = "VENDOR_USER")
public class VendorUserEntity extends AbstractAuditableEntity {
    public static final int ALLOCATION_SIZE = 1;

    private VendorEntity vendor;
    private String userName;
    private VendorRole role;
}
