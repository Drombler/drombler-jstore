package org.drombler.jstore.integration.persistence;


import org.drombler.commons.spring.jpa.AbstractAuditableEntity;
import org.springframework.data.jpa.domain.AbstractAuditable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import static org.drombler.jstore.integration.persistence.VendorUserEntity.ALLOCATION_SIZE;
import static org.drombler.jstore.integration.persistence.VendorUserEntity.VENDOR_USER_GENERATOR;

@Entity
@EntityListeners(AuditingEntityListener.class)
@SequenceGenerator(name = VENDOR_USER_GENERATOR, sequenceName = "VENDOR_USER_SEQ", allocationSize = ALLOCATION_SIZE)
@Table(name = "VENDOR_USER")
public class VendorUserEntity extends AbstractAuditableEntity {
    public static final int ALLOCATION_SIZE = 1;
    public static final String VENDOR_USER_GENERATOR = "VENDOR_USER_GENERATOR";

    @Id
    @GeneratedValue(generator = VENDOR_USER_GENERATOR, strategy = GenerationType.SEQUENCE)
    @Column(name = "ID")
    private Long id;

    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name="VENDOR_PK", nullable=false, updatable=false)
    private VendorEntity vendor;

    @NotBlank
    @Column(name = "USERNAME")
    private String username;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "ROLE")
    private VendorRole role;

    /**
     * The technical id of this entity (surrogate PK).
     */
    public Long getId() {
        return id;
    }

    public VendorEntity getVendor() {
        return vendor;
    }

    public void setVendor(VendorEntity vendor) {
        this.vendor = vendor;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public VendorRole getRole() {
        return role;
    }

    public void setRole(VendorRole role) {
        this.role = role;
    }
}
