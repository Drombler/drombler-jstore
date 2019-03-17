package org.drombler.jstore.integration.persistence;


import org.drombler.commons.spring.jpa.AbstractAuditableEntity;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.net.URL;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.drombler.jstore.integration.persistence.FieldLengthUtils.*;
import static org.drombler.jstore.integration.persistence.VendorEntity.ALLOCATION_SIZE;
import static org.drombler.jstore.integration.persistence.VendorEntity.VENDOR_GENERATOR;

@Entity
@EntityListeners(AuditingEntityListener.class)
@SequenceGenerator(name = VENDOR_GENERATOR, sequenceName = "VENDOR_SEQ", allocationSize = ALLOCATION_SIZE)
@Table(name = "VENDOR")
public class VendorEntity extends AbstractAuditableEntity {
    public static final int ALLOCATION_SIZE = 1;
    public static final String VENDOR_GENERATOR = "VENDOR_GENERATOR";

    /**
     * The technical id of this entity (surrogate PK).
     */
    @Id
    @GeneratedValue(generator = VENDOR_GENERATOR, strategy = GenerationType.SEQUENCE)
    @Column(name = "ID")
    private Long id;

    @NotNull
    @Size(max = USERNAME_MAX_LENGTH)
    @Column(name = "VENDOR_ID", updatable = false)
    private String vendorId;

    @NotNull
    @Size(max = VENDOR_NAME_MAX_LENGTH)
    @Column(name = "NAME")
    private String name;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private VendorStatus status = VendorStatus.VERIFICATION_PENDING;


    @Column(name = "VERIFIED")
    private boolean verified = false;

    @NotNull
    @Size(max = HOMEPAGE_MAX_LENGTH)
    @Column(name = "HOMEPAGE")
    private URL homepage;

    @NotBlank
    @Email
    @Size(max = EMAIL_ADDRESS_MAX_LENGTH)
    @Column(name = "REGISTRATION_EMAIL_ADDRESS", updatable = false) // TODO: provide a verification process for updating the registration email address
    private String registrationEmailAddress;

    @NotBlank
    @Email
    @Size(max = EMAIL_ADDRESS_MAX_LENGTH)
    @Column(name = "CUSTOMER_EMAIL_ADDRESS")
    private String customerContactEmailAddress;

//    @Version
//    @Column(name = "ENTITY_VERSION")
//    private Long version;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public URL getHomepage() {
        return homepage;
    }

    public void setHomepage(URL homepage) {
        this.homepage = homepage;
    }

    public String getRegistrationEmailAddress() {
        return registrationEmailAddress;
    }

    public void setRegistrationEmailAddress(String registrationEmailAddress) {
        this.registrationEmailAddress = registrationEmailAddress;
    }

    public String getCustomerContactEmailAddress() {
        return customerContactEmailAddress;
    }

    public void setCustomerContactEmailAddress(String customerContactEmailAddress) {
        this.customerContactEmailAddress = customerContactEmailAddress;
    }
}
