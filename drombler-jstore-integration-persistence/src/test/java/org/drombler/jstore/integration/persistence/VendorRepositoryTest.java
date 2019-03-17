package org.drombler.jstore.integration.persistence;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SpringBootWebSecurityConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.test.context.annotation.SecurityTestExecutionListeners;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithSecurityContext;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.net.MalformedURLException;

import static org.drombler.jstore.integration.persistence.PersistenceTestHelper.createVendorEntity;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest//(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@DataJpaTest
@ContextConfiguration(classes = JStorePersistenceConfig.class)
@EnableAutoConfiguration
//@SecurityTestExecutionListeners
//@Import(SpringBootWebSecurityConfiguration.class)
@WithMockUser(username = "test", password = "test", roles = "USER")
@ExtendWith(SpringExtension.class)
class VendorRepositoryTest {

    @Autowired
    private VendorRepository testee;

//    @Autowired
//    private SecurityContext securityContext;

//    @Autowired
//    private WebApplicationContext webApplicationContext;
//    private MockMvc mockMvc;

//    @BeforeEach
//    public void before() {
//        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
//                .apply(SecurityMockMvcConfigurers.springSecurity())
//                .build();
//    }

    @Nested
    class FindByVendorId{
        @Test
        public void test() throws MalformedURLException {
            VendorEntity acmeVendorEntity = createVendorEntity("acme");
//            Authentication authentication = securityContext.getAuthentication();
            VendorEntity savedAcmeVendorEntity = testee.saveAndFlush(acmeVendorEntity);
            testee.findByVendorId("acme");
        }
    }
}