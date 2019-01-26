package org.drombler.jstore.integration.persistence;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.drombler.jstore.integration.persistence.PersistenceTestHelper.createVendorEntity;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ContextConfiguration(classes = JStorePersistenceConfig.class)
@EnableAutoConfiguration
@ExtendWith(SpringExtension.class)
class VendorRepositoryTest {

    @Autowired
    private VendorRepository testee;


    @Nested
    class FindByVendorId{
        @Test
        public void test(){
            VendorEntity acmeVendorEntity = createVendorEntity("acme");
            VendorEntity savedAcmeVendorEntity = testee.saveAndFlush(acmeVendorEntity);
            testee.findByVendorId("acme");
        }
    }
}