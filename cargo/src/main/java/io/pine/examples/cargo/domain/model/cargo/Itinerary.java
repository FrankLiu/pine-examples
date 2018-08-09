package io.pine.examples.cargo.domain.model.cargo;

import io.pine.examples.cargo.domain.model.handling.HandlingEvent;
import io.pine.examples.cargo.domain.model.location.Location;
import io.pine.examples.cargo.domain.shared.ValueObject;
import org.springframework.util.Assert;

import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * An itinerary.
 *
 * @author Frank
 * @sinace 2018/8/9 0009.
 */
public class Itinerary implements ValueObject<Itinerary> {
    private List<Leg> legs = Collections.emptyList();

    static final Itinerary EMPTY_ITINERARY = new Itinerary();
    private static final Date END_OF_DAYS = new Date(Long.MAX_VALUE);

    public Itinerary() {}

    public Itinerary(final List<Leg> legs) {
        Assert.notEmpty(legs, "Legs should not be empty!");
        Assert.noNullElements(legs.toArray(), "Legs should not contains null element!");

        this.legs = legs;
    }

    public List<Leg> getLegs() {
        return Collections.unmodifiableList(legs);
    }

    /**
     * Test if the given handling event is expected when executing this itinerary.
     *
     * @param event Event to test.
     * @return <code>true</code> if the event is expected
     */
    public boolean isExpected(final HandlingEvent event) {
        if (legs.isEmpty()) {
            return true;
        }

        if (event.getType() == HandlingEvent.Type.RECEIVE) {
            //Check that the first leg's origin is the event's location
            final Leg leg = legs.get(0);
            return (leg.getLoadLocation().equals(event));
        }

        if (event.getType() == HandlingEvent.Type.LOAD) {
            //Check that the there is one leg with same load location and voyage
            for (Leg leg : legs) {
                if (leg.getLoadLocation().sameIdentityAs(event.getLocation()) &&
                        leg.getVoyage().sameIdentityAs(event.getVoyage())) {
                    return true;
                }
            }
            return false;
        }

        if (event.getType() == HandlingEvent.Type.UNLOAD) {
            //Check that the there is one leg with same unload location and voyage
            for (Leg leg : legs) {
                if (leg.getUnloadLocation().equals(event.getLocation()) &&
                        leg.getVoyage().equals(event.getVoyage())) {
                    return true;
                }
            }
            return false;
        }

        if (event.getType() == HandlingEvent.Type.CLAIM) {
            //Check that the last leg's destination is from the event's location
            final Leg leg = lastLeg();
            return (leg.getUnloadLocation().equals(event.getLocation()));
        }

        //HandlingEvent.Type.CUSTOMS;
        return true;
    }

    /**
     * @return The initial departure location.
     */
    Location initialDepartureLocation() {
        if (legs.isEmpty()) {
            return Location.UNKNOWN;
        } else {
            return legs.get(0).getLoadLocation();
        }
    }

    /**
     * @return The final arrival location.
     */
    Location finalArrivalLocation() {
        if (legs.isEmpty()) {
            return Location.UNKNOWN;
        } else {
            return lastLeg().getUnloadLocation();
        }
    }

    /**
     * @return Date when cargo arrives at final destination.
     */
    Date finalArrivalDate() {
        final Leg lastLeg = lastLeg();

        if (lastLeg == null) {
            return new Date(END_OF_DAYS.getTime());
        } else {
            return new Date(lastLeg.getUnloadTime().getTime());
        }
    }

    /**
     * @return The last leg on the itinerary.
     */
    Leg lastLeg() {
        if (legs.isEmpty()) {
            return null;
        } else {
            return legs.get(legs.size() - 1);
        }
    }

    /**
     * @param other itinerary to compare
     * @return <code>true</code> if the legs in this and the other itinerary are all equal.
     */
    @Override
    public boolean sameValueAs(final Itinerary other) {
        return other != null && legs.equals(other.legs);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }

        final Itinerary itinerary = (Itinerary) o;

        return sameValueAs(itinerary);
    }

    @Override
    public int hashCode() {
        return legs.hashCode();
    }
}
