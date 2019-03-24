package org.drombler.jstore.integration.persistence;

import org.drombler.commons.spring.jpa.AbstractAuditableEntity;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import static org.drombler.jstore.integration.persistence.FieldLengthUtils.EMAIL_ADDRESS_MAX_LENGTH;
import static org.drombler.jstore.integration.persistence.VendorRegistrationEntity.ALLOCATION_SIZE;
import static org.drombler.jstore.integration.persistence.VendorRegistrationEntity.VENDOR_REGISTRATION_GENERATOR;

@Entity
@EntityListeners(AuditingEntityListener.class)
@SequenceGenerator(name = VENDOR_REGISTRATION_GENERATOR, sequenceName = "VENDOR_REG_SEQ", allocationSize = ALLOCATION_SIZE)
@Table(name = "VENDOR_REGISTRATION")
public class VendorRegistrationEntity extends AbstractAuditableEntity {
    public static final int ALLOCATION_SIZE = 1;
    public static final String VENDOR_REGISTRATION_GENERATOR = "VENDOR_REGISTRATION_GENERATOR";

    @Id
    @GeneratedValue(generator = VENDOR_REGISTRATION_GENERATOR, strategy = GenerationType.SEQUENCE)
    @Column(name = "ID")
    private Long id;

    @NotNull
    @OneToOne(optional = false)
    @JoinColumn(name="VENDOR_PK", nullable=false, updatable=false)
    private VendorEntity vendor;

    @Email
    @Size(max = EMAIL_ADDRESS_MAX_LENGTH)
    @Column(name = "REGISTRATION_EMAIL_ADDRESS")
    private String registrationEmailAddress;

    @NotBlank
    @Size(max = EMAIL_ADDRESS_MAX_LENGTH)
    @Column(name = "ACTIVATION_CODE")
    private String activationCode;

    public Long getId() {
        return id;
    }

    public VendorEntity getVendor() {
        return vendor;
    }

    public void setVendor(VendorEntity vendor) {
        this.vendor = vendor;
    }

    public String getRegistrationEmailAddress() {
        return registrationEmailAddress;
    }

    public void setRegistrationEmailAddress(String registrationEmailAddress) {
        this.registrationEmailAddress = registrationEmailAddress;
    }

    public String getActivationCode() {
        return activationCode;
    }

    public void setActivationCode(String activationCode) {
        this.activationCode = activationCode;
    }
}
