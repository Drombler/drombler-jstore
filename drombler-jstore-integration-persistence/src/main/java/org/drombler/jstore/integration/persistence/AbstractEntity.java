package org.drombler.jstore.integration.persistence;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import java.time.Instant;

@MappedSuperclass
public class AbstractEntity {

    @CreatedDate
    @Column(name = "CREATED_AT", updatable = false)
    private Instant createdAt;

    @CreatedBy
    @Column(name = "CREATED_BY", updatable = false)
    private String createdBy;

    @LastModifiedDate
    @Column(name = "LAST_MODIFIED_AT")
    private Instant lastModifiedAt;

    @LastModifiedBy
    @Column(name = "LAST_MODIFIED_BY")
    private String lastModifiedBy;

    @Version
    @Column(name = "ENTITY_VERSION")
    private Long version;
}
