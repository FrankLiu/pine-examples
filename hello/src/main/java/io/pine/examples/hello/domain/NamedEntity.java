package io.pine.examples.hello.domain;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * Simple JavaBean domain object adds a name property to <code>BaseEntity</code>. Used as a base class for objects
 * needing these properties.
 */
@MappedSuperclass
public class NamedEntity extends StatusEntity {

    @Column(name = "name", nullable = false)
    private String name;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.getName();
    }

}
