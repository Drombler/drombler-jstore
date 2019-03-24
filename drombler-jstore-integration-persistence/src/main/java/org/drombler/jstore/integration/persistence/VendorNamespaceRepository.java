package org.drombler.jstore.integration.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface VendorNamespaceRepository extends JpaRepository<VendorNamespaceEntity, Long> {

    @Query("SELECT n FROM VendorNamespaceEntity n " +
            "WHERE n.namespace IN :namespaces ")
    List<VendorNamespaceEntity> findAllByNamespaces(@Param("namespaces")Collection<String> namespaces);

    @Query("SELECT n FROM VendorNamespaceEntity n " +
            "WHERE n.namespace LIKE CONCAT(:namespace, '%') " +
            "OR  :namespace LIKE CONCAT(n.namespace, '%') ")
    List<VendorNamespaceEntity> findAllClaimedNamespaces(@Param("namespace")String namespace);

    @Query("SELECT n.vendor FROM VendorNamespaceEntity n " +
            "WHERE n.namespace = :namespace ")
    VendorEntity findVendorByNamespace(@Param("namespace")String namespace);

    List<VendorNamespaceEntity> findAllByVendor(VendorEntity vendorEntity);
}
