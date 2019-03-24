package org.drombler.jstore.integration.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VendorRegistrationRepository extends JpaRepository<VendorRegistrationEntity, Long> {
    VendorRegistrationEntity findByVendor(VendorEntity vendorEntity);
}
