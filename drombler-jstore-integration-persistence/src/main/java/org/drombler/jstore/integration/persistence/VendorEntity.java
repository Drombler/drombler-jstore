package org.drombler.jstore.integration.persistence;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Entity
@Table(name = "VENDOR")
public class VendorEntity extends AbstractEntity{

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
    private VendorStatus status;


    private boolean verified = false;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name="VENDOR_NAMESPACES", joinColumns=@JoinColumn(name="VENDOR_FK"))
    @Column(name="NAMESPACE")
    private Set<String> namespaces;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name="VENDOR_USER", joinColumns=@JoinColumn(name="VENDOR_FK"))
    @Column(name="NAMESPACE")
    @AttributeOverrides({
            @AttributeOverride(name="key", column=@Column(name="USER_NAME")),
            @AttributeOverride(name="value.name", column=@Column(name="ROLE"))
    })
    public Map<String, List<VendorRole>> vendorUserRoleMappings;

    public Long getId() {
        return id;
    }

    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }
}
