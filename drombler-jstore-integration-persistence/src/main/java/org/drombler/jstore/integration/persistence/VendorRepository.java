package org.drombler.jstore.integration.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VendorRepository extends JpaRepository<VendorEntity, Long> {
    VendorEntity findByVendorId(String vendorId);

//    List<VendorEntity> findAllByUsername(String username);
}
