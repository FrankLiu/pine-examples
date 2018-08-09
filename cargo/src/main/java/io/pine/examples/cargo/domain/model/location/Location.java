package io.pine.examples.cargo.domain.model.location;

import io.pine.examples.cargo.domain.shared.Entity;
import org.springframework.util.Assert;

import javax.persistence.*;

/**\
 * A location is our model is stops on a journey, such as cargo
 * origin or destination, or carrier movement endpoints.
 *
 * It is uniquely identified by a UN Locode.
 *
 * @author Frank
 * @sinace 2018/8/6 0006.
 */
@javax.persistence.Entity
@Table(name = "t_location")
public class Location implements Entity<Location> {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "unlocode")
    @Embedded
    private UnLocode unLocode;

    @Column(name = "name")
    private String name ;

    /**
     * Special Location object that marks an unknown location.
     */
    public static final Location UNKNOWN = new Location(
            new UnLocode("XXXXX"), "Unknown location"
    );

    public Location(final UnLocode unLocode, final String name) {
        Assert.notNull(unLocode, "unlocaode should not be null!");
        Assert.hasText(name, "name should not be empty!");
        this.unLocode = unLocode;
        this.name = name;
    }

    /**
     * @return UN Locode for this location.
     */
    public UnLocode getUnLocode() {
        return this.unLocode;
    }

    /**
     * @return Actual name of this location, e.g. "Stockholm".
     */
    public String getName() {
        return this.name;
    }

    /**
     * @param object to compare
     * @return Since this is an entiy this will be true iff UN locodes are equal.
     */
    @Override
    public boolean equals(final Object object) {
        if (object == null) {
            return false;
        }
        if (this == object) {
            return true;
        }
        if (!(object instanceof Location)) {
            return false;
        }
        Location other = (Location) object;
        return sameIdentityAs(other);
    }

    /**
     * @return Hash code of UN locode.
     */
    @Override
    public int hashCode() {
        return unLocode.hashCode();
    }

    @Override
    public boolean sameIdentityAs(final Location other) {
        return this.unLocode.sameValueAs(other.unLocode);
    }

    @Override
    public String toString() {
        return name + " [" + unLocode + "]";
    }
}
