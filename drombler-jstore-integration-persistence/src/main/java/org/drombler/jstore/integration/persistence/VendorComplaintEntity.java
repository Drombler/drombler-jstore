package org.drombler.jstore.integration.persistence;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

public class VendorComplaintEntity {
//    public static final int ALLOCATION_SIZE = 1;
//    public static final String VENDOR_USER_GENERATOR = "VENDOR_USER_GENERATOR";
//
//    @Id
//    @GeneratedValue(generator = VENDOR_USER_GENERATOR, strategy = GenerationType.SEQUENCE)
//    @Column(name = "ID")
//    private Long id;
//
//    @NotNull
//    @ManyToOne(optional = false)
//    @JoinColumn(name="VENDOR_PK", nullable=false, updatable=false)
//    private VendorEntity vendor;

    private VendorComplaintReason reason;

}
