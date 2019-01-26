package org.drombler.jstore.integration.persistence;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

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
            testee.findByVendorId("acme");
        }
    }
}