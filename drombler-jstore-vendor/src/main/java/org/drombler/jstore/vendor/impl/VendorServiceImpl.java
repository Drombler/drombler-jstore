package org.drombler.jstore.vendor.impl;

import org.drombler.jstore.integration.persistence.VendorEntity;
import org.drombler.jstore.integration.persistence.VendorNamespaceEntity;
import org.drombler.jstore.integration.persistence.VendorNamespaceRepository;
import org.drombler.jstore.integration.persistence.VendorRepository;
import org.drombler.jstore.model.JStoreException;
import org.drombler.jstore.model.TransactionalService;
import org.drombler.jstore.protocol.json.VendorInfo;
import org.drombler.jstore.vendor.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.drombler.jstore.integration.nexus.client.NexusClient;

import java.util.List;

@TransactionalService
public class VendorServiceImpl implements VendorService {

    @Autowired
    private VendorRepository vendorRepository;

//    @Autowired
//    private VendorNamespaceRepository vendorNamespaceRepository;

    @Autowired
    private NexusClient nexusClient;

    @Override
    public VendorInfo createVendor(VendorInfo vendorInfo) throws JStoreException {
        String vendorId = vendorInfo.getVendorId();
       VendorEntity vendorEntity = vendorRepository.findByVendorId(vendorId);
       if (vendorEntity != null){
// TODO
       }
//        List<VendorNamespaceEntity> vendorNamespaceEntities = vendorNamespaceRepository.findByNamespaces(vendorInfo.getNamespaces());
//        if (! vendorNamespaceEntities.isEmpty()){
//// TODO
//        }
        vendorEntity = createVendorEntity(vendorInfo);
       vendorRepository.save(vendorEntity);
       addVendorProxy(vendorInfo);
        return vendorInfo;
    }

    private void addVendorProxy(VendorInfo vendorInfo) {
        nexusClient.createProxy(vendorInfo.getVendorId());
        nexusClient.addRepoToGroup();
    }

    private VendorEntity createVendorEntity(VendorInfo vendorInfo) {
        VendorEntity vendorEntity = new VendorEntity();
        vendorEntity.setVendorId(vendorInfo.getVendorId());
        return vendorEntity;
    }
}
