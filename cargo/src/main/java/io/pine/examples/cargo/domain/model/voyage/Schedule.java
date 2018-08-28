package io.pine.examples.cargo.domain.model.voyage;

import io.pine.examples.cargo.domain.shared.ValueObject;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.util.Assert;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.util.Collections;
import java.util.List;

/**
 * A voyage schedule.
 *
 * @author Frank
 * @sinace 2018/8/9 0009.
 */
@Embeddable
public class Schedule implements ValueObject<Schedule> {
    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true)
    @JoinColumn(name = "voyage_id")
    private List<CarrierMovement> carrierMovements = Collections.EMPTY_LIST;

    public static final Schedule EMPTY = new Schedule();

    Schedule() {}

    public Schedule(final List<CarrierMovement> carrierMovements) {
        Assert.notNull(carrierMovements, "carrierMovements is required!");
        Assert.noNullElements(carrierMovements.toArray(), "Not supported null element!");
        Assert.notEmpty(carrierMovements, "carrierMovements ");

        this.carrierMovements = carrierMovements;
    }

    /**
     * @return Carrier movements.
     */
    public List<CarrierMovement> carrierMovements() {
        return Collections.unmodifiableList(carrierMovements);
    }

    @Override
    public boolean sameValueAs(final Schedule other) {
        return other != null && this.carrierMovements.equals(other.carrierMovements);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }

        final Schedule that = (Schedule) o;

        return sameValueAs(that);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(this.carrierMovements).toHashCode();
    }

}
