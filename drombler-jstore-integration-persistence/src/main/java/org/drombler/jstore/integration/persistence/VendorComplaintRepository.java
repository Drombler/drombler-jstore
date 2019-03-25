package org.drombler.jstore.integration.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;

@Repository
@NoRepositoryBean
public interface VendorComplaintRepository extends JpaRepository<VendorComplaintEntity, Long> {
}
