package org.drombler.jstore.integration.persistence;


import org.drombler.commons.spring.jpa.AbstractAuditableEntity;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.drombler.jstore.integration.persistence.VendorEntity.ALLOCATION_SIZE;
import static org.softsmithy.lib.persistence.AbstractEntity.PRIMARY_GENERATOR;

@Entity
@EntityListeners(AuditingEntityListener.class)
@SequenceGenerator(name = PRIMARY_GENERATOR, sequenceName = "VENDOR_SEQ", allocationSize = ALLOCATION_SIZE)
@Table(name = "VENDOR")
public class VendorEntity extends AbstractAuditableEntity {
    public static final int ALLOCATION_SIZE = 1;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vendorSequence")
    @SequenceGenerator(name = "vendorSequence")
    private Long id;

    @NotNull
    @Column(name = "VENDOR_ID", updatable = false)
    private String vendorId;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private VendorStatus status = VendorStatus.VERIFICATION_PENDING;


    private boolean verified = false;

//    @ElementCollection(fetch = FetchType.EAGER)
//    @CollectionTable(name="VENDOR_NAMESPACES", joinColumns=@JoinColumn(name="VENDOR_FK"))
//    @Column(name="NAMESPACE")
//    private Set<String> namespaces;
//
//    @ElementCollection(fetch = FetchType.EAGER)
//    @CollectionTable(name="VENDOR_USER", joinColumns=@JoinColumn(name="VENDOR_FK"))
//    @Column(name="NAMESPACE")
//    @AttributeOverrides({
//            @AttributeOverride(name="key", column=@Column(name="USER_NAME")),
//            @AttributeOverride(name="value.name", column=@Column(name="ROLE"))
//    })
//    public Map<String, List<VendorRole>> vendorUserRoleMappings;

    public Long getId() {
        return id;
    }

    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    public VendorStatus getStatus() {
        return status;
    }

    public void setStatus(VendorStatus status) {
        this.status = status;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }
}
