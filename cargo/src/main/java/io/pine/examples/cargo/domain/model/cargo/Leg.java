package io.pine.examples.cargo.domain.model.cargo;

import io.pine.examples.cargo.domain.model.location.Location;
import io.pine.examples.cargo.domain.model.voyage.Voyage;
import io.pine.examples.cargo.domain.shared.ValueObject;
import lombok.Data;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.util.Assert;

import java.util.Date;

/**
 * An itinerary consists of one or more legs.
 *
 * @author Frank
 * @sinace 2018/8/9 0009.
 */
@Data
public class Leg implements ValueObject<Leg> {
    private Voyage voyage;
    private Location loadLocation;
    private Location unloadLocation;
    private Date loadTime;
    private Date unloadTime;

    public Leg(Voyage voyage, Location loadLocation, Location unloadLocation, Date loadTime, Date unloadTime) {
        Assert.noNullElements(new Object[] {voyage, loadLocation, unloadLocation, loadTime, unloadTime});

        this.voyage = voyage;
        this.loadLocation = loadLocation;
        this.unloadLocation = unloadLocation;
        this.loadTime = loadTime;
        this.unloadTime = unloadTime;
    }

    @Override
    public boolean sameValueAs(final Leg other) {
        return other != null && new EqualsBuilder().
                append(this.voyage, other.voyage).
                append(this.loadLocation, other.loadLocation).
                append(this.unloadLocation, other.unloadLocation).
                append(this.loadTime, other.loadTime).
                append(this.unloadTime, other.unloadTime).
                isEquals();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }

        Leg leg = (Leg) o;
        return sameValueAs(leg);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().
                append(voyage).
                append(loadLocation).
                append(unloadLocation).
                append(loadTime).
                append(unloadTime).
                toHashCode();
    }
}