package org.drombler.jstore.integration.persistence;

import org.drombler.commons.spring.jpa.AbstractAuditableEntity;
import org.softsmithy.lib.persistence.AbstractEntity;
import org.springframework.data.jpa.domain.AbstractAuditable_;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import static org.drombler.jstore.integration.persistence.VendorNamespaceEntity.ALLOCATION_SIZE;
import static org.drombler.jstore.integration.persistence.VendorNamespaceEntity.VENDOR_NAMESPACE_GENERATOR;


@Entity
@EntityListeners(AuditingEntityListener.class)
@SequenceGenerator(name = VENDOR_NAMESPACE_GENERATOR, sequenceName = "VENDOR_NAMESPACE_SEQ", allocationSize = ALLOCATION_SIZE)
@Table(name = "VENDOR_NAMESPACE")
public class VendorNamespaceEntity extends AbstractAuditableEntity {
    public static final int ALLOCATION_SIZE = 1;
    public static final String VENDOR_NAMESPACE_GENERATOR = "VENDOR_NAMESPACE_GENERATOR";

    @Id
    @GeneratedValue(generator = VENDOR_NAMESPACE_GENERATOR, strategy = GenerationType.SEQUENCE)
    @Column(name = "ID")
    private Long id;

    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name="VENDOR_PK", nullable=false, updatable=false)
    private VendorEntity vendor;

    @NotBlank
    @Column(name = "NAMESPACE")
    private String namespace;

    public Long getId() {
        return id;
    }

    public VendorEntity getVendor() {
        return vendor;
    }

    public void setVendor(VendorEntity vendor) {
        this.vendor = vendor;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }
}
