package org.drombler.jstore.integration.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VendorUserRepository  extends JpaRepository<VendorUserEntity, Long> {

//    @Query("SELECT vu.role FROM VendorUserEntity vu where vu.vendor = :vendor AND vu.username = :username")
//    List<VendorRole> findRolesByVendorAndUser(@Param("vendor") VendorEntity vendorEntity,
//                                              @Param("username") String username);

    List<VendorUserEntity> findByUsername(String username);
}
