package io.pine.examples.cargo.domain.model.voyage;

import io.pine.examples.cargo.domain.model.location.Location;
import io.pine.examples.cargo.domain.shared.ValueObject;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.util.Assert;

import java.util.Date;

/**
 * A carrier movement is a vessel voyage from one location to another.
 *
 * @author Frank
 * @sinace 2018/8/9 0009.
 */
public class CarrierMovement implements ValueObject<CarrierMovement> {
    private Long id;
    private Location departureLocation;
    private Location arrivalLocation;
    private Date departureTime;
    private Date arrivalTime;

    // NULL Object pattern
    public static final CarrierMovement NONE = new CarrierMovement(
            Location.UNKNOWN, Location.UNKNOWN, new Date(0), new Date(0));


    public CarrierMovement() {}

    public CarrierMovement(Location departureLocation,
                           Location arrivalLocation,
                           Date departureTime,
                           Date arrivalTime) {
        Assert.noNullElements(new Object[]{departureLocation, arrivalLocation, departureTime, arrivalTime},
                "departureLocation, arrivalLocation, departureTime, arrivalTime are required!");
        this.departureLocation = departureLocation;
        this.arrivalLocation = arrivalLocation;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }

        final CarrierMovement that = (CarrierMovement) o;
        return sameValueAs(that);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().
                append(this.departureLocation).
                append(this.departureTime).
                append(this.arrivalLocation).
                append(this.arrivalTime).
                toHashCode();
    }

    @Override
    public boolean sameValueAs(CarrierMovement other) {
        return other != null && new EqualsBuilder().
                append(this.departureLocation, other.departureLocation).
                append(this.departureTime, other.departureTime).
                append(this.arrivalLocation, other.arrivalLocation).
                append(this.arrivalTime, other.arrivalTime).
                isEquals();
    }

}
