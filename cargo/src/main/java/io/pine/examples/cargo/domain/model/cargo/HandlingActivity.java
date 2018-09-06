package io.pine.examples.cargo.domain.model.cargo;

import io.pine.examples.cargo.domain.model.handling.HandlingEvent;
import io.pine.examples.cargo.domain.model.location.Location;
import io.pine.examples.cargo.domain.model.voyage.Voyage;
import io.pine.examples.cargo.domain.shared.ValueObject;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.util.Assert;
import lombok.Data;

import javax.persistence.*;

/**
 * A handling activity represents how and where a cargo can be handled,
 * and can be used to express predictions about what is expected to
 * happen to a cargo in the future.
 *
 * @author Frank
 * @sinace 2018/8/9 0009.
 */
@Data
@Embeddable
public class HandlingActivity implements ValueObject<HandlingActivity> {

    // TODO make HandlingActivity a part of HandlingEvent too? There is some overlap.

    @Enumerated(EnumType.STRING)
    @Column(name = "next_expected_handling_event_type", nullable = false)
    private HandlingEvent.Type type;

    @ManyToOne
    @JoinColumn(name = "next_expected_location_id", foreignKey = @ForeignKey(name = "next_expected_location_fk"))
    private Location location;

    @ManyToOne
    @JoinColumn(name = "next_expected_voyage_id", foreignKey = @ForeignKey(name = "next_expected_voyage_fk"))
    private Voyage voyage;

    HandlingActivity() {}

    public HandlingActivity(final HandlingEvent.Type type, final Location location) {
        Assert.notNull(type, "Handling event type is required");
        Assert.notNull(location, "Location is required");

        this.type = type;
        this.location = location;
    }

    public HandlingActivity(final HandlingEvent.Type type, final Location location, final Voyage voyage) {
        Assert.notNull(type, "Handling event type is required");
        Assert.notNull(location, "Location is required");
        Assert.notNull(location, "Voyage is required");

        this.type = type;
        this.location = location;
        this.voyage = voyage;
    }

    @Override
    public boolean sameValueAs(final HandlingActivity other) {
        return other != null && new EqualsBuilder().
                append(this.type, other.type).
                append(this.location, other.location).
                append(this.voyage, other.voyage).
                isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().
                append(this.type).
                append(this.location).
                append(this.voyage).
                toHashCode();
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == this) { return true; }
        if (obj == null) { return false; }
        if (obj.getClass() != this.getClass()) { return false; }

        HandlingActivity other = (HandlingActivity) obj;
        return sameValueAs(other);
    }

}