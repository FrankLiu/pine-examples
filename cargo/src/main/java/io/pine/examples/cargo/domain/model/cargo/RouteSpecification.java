package io.pine.examples.cargo.domain.model.cargo;


import io.pine.examples.cargo.domain.model.location.Location;
import io.pine.examples.cargo.domain.shared.AbstractSpecification;
import io.pine.examples.cargo.domain.shared.ValueObject;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.util.Assert;

import java.util.Date;
import lombok.Data;

/**
 * Route specification. Describes where a cargo orign and destination is,
 * and the arrival deadline.
 *
 * @author Frank
 * @sinace 2018/8/9 0009.
 */
@Data
public class RouteSpecification extends AbstractSpecification<Itinerary> implements ValueObject<RouteSpecification> {

    private Location origin;
    private Location destination;
    private Date arrivalDeadline;

    public RouteSpecification(final Location origin, final Location destination, final Date arrivalDeadline) {
        Assert.notNull(origin, "Origin is required!");
        Assert.notNull(destination, "Destination is required!");
        Assert.notNull(arrivalDeadline, "Arrival deadline is required!");
        Assert.isTrue(!origin.sameIdentityAs(destination), "Origin and destination can't be the same: " + origin);

        this.origin = origin;
        this.destination = destination;
        this.arrivalDeadline = arrivalDeadline;
    }


    @Override
    public boolean isSatisfiedBy(final Itinerary itinerary) {
        return itinerary != null &&
                origin.sameIdentityAs(itinerary.initialDepartureLocation()) &&
                destination.sameIdentityAs(itinerary.finalArrivalLocation()) &&
                arrivalDeadline.after(itinerary.finalArrivalDate());
    }

    @Override
    public boolean sameValueAs(final RouteSpecification other) {
        return other != null && new EqualsBuilder().
                append(this.origin, other.origin).
                append(this.destination, other.destination).
                append(this.arrivalDeadline, other.arrivalDeadline).
                isEquals();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }

        final RouteSpecification that = (RouteSpecification) o;

        return sameValueAs(that);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().
                append(this.origin).
                append(this.destination).
                append(this.arrivalDeadline).
                toHashCode();
    }
}
