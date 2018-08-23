package io.pine.examples.cargo.domain.model.voyage;

import io.pine.examples.cargo.domain.model.location.Location;
import io.pine.examples.cargo.domain.shared.Entity;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Frank
 * @sinace 2018/8/9 0009.
 */
@javax.persistence.Entity
@Table(name = "t_voyage")
public class Voyage implements Entity<Voyage> {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private VoyageNumber voyageNumber;

    private Schedule schedule;

    // Null object pattern
    public static final Voyage NONE = new Voyage(
            new VoyageNumber(""), Schedule.EMPTY
    );

    Voyage() {}

    public Voyage(VoyageNumber voyageNumber, Schedule schedule) {
        Assert.notNull(voyageNumber, "VoyageNumber is required!");
        Assert.notNull(schedule, "Schedule is required!");
        this.voyageNumber = voyageNumber;
        this.schedule = schedule;
    }

    /**
     * @return Voyage number.
     */
    public VoyageNumber voyageNumber() {
        return voyageNumber;
    }

    /**
     * @return Schedule.
     */
    public Schedule schedule() {
        return schedule;
    }

    @Override
    public int hashCode() {
        return voyageNumber.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null) { return false; }
        if (!(o instanceof Voyage)) { return false; }

        final Voyage that = (Voyage) o;

        return sameIdentityAs(that);
    }

    @Override
    public boolean sameIdentityAs(Voyage other) {
        return other != null && this.voyageNumber().sameValueAs(other.voyageNumber());
    }

    @Override
    public String toString() {
        return "Voyage " + voyageNumber;
    }

    /**
     * Builder pattern is used for incremental construction
     * of a Voyage aggregate. This serves as an aggregate factory.
     */
    public static final class Builder {

        private final List<CarrierMovement> carrierMovements = new ArrayList<CarrierMovement>();
        private final VoyageNumber voyageNumber;
        private Location departureLocation;

        public Builder(final VoyageNumber voyageNumber, final Location departureLocation) {
            Assert.notNull(voyageNumber, "Voyage number is required");
            Assert.notNull(departureLocation, "Departure location is required");

            this.voyageNumber = voyageNumber;
            this.departureLocation = departureLocation;
        }

        public Builder addMovement(Location arrivalLocation, Date departureTime, Date arrivalTime) {
            carrierMovements.add(new CarrierMovement(departureLocation, arrivalLocation, departureTime, arrivalTime));
            // Next departure location is the same as this arrival location
            this.departureLocation = arrivalLocation;
            return this;
        }

        public Voyage build() {
            return new Voyage(voyageNumber, new Schedule(carrierMovements));
        }

    }
}
