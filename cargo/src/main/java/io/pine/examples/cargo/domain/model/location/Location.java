package io.pine.examples.cargo.domain.model.location;

import org.springframework.util.Assert;

import javax.persistence.*;
import lombok.Data;

/**\
 * A location is our model is stops on a journey, such as cargo
 * origin or destination, or carrier movement endpoints.
 *
 * It is uniquely identified by a UN Locode.
 *
 * @author Frank
 * @sinace 2018/8/6 0006.
 */
@Entity
@Table(name = "t_location")
@Data
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    public boolean sameIdentityAs(final Location other) {
        return this.unLocode.sameValueAs(other.unLocode);
    }

    @Override
    public String toString() {
        return name + " [" + unLocode + "]";
    }
}
