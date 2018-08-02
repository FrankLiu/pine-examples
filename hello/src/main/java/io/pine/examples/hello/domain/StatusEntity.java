package io.pine.examples.hello.domain;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * Simple JavaBean domain object adds a enabled property to <code>BaseEntity</code>. Used as a base class for objects
 * needing these properties.
 */
@MappedSuperclass
public class StatusEntity extends TimestampsEntity {

    @Column(name = "enabled", nullable = false)
    private Boolean enabled;

    public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	@Override
    public String toString() {
        return this.getEnabled().toString();
    }

}
