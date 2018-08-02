package io.pine.examples.hello.domain;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.util.Date;

/**
 * Simple JavaBean domain object with an id property. Used as a base class for objects
 * needing this property.
 */

@MappedSuperclass
public class TimestampsEntity extends BaseEntity {
    @Column(name = "created_at", nullable = false)
    private Date createdAt;
    
    @Column(name = "updated_at", nullable = false)
    private Date updatedAt;
    
    public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
}