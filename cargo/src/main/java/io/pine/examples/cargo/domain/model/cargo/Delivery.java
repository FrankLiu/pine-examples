package io.pine.examples.cargo.domain.model.cargo;

import io.pine.examples.cargo.domain.model.handling.HandlingEvent;
import io.pine.examples.cargo.domain.model.handling.HandlingHistory;
import io.pine.examples.cargo.domain.model.location.Location;
import io.pine.examples.cargo.domain.model.voyage.Voyage;
import io.pine.examples.cargo.domain.shared.ValueObject;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.Iterator;

import lombok.Data;

import javax.persistence.*;

import static io.pine.examples.cargo.domain.model.cargo.RoutingStatus.MISROUTED;
import static io.pine.examples.cargo.domain.model.cargo.RoutingStatus.NOT_ROUTED;
import static io.pine.examples.cargo.domain.model.cargo.RoutingStatus.ROUTED;
import static io.pine.examples.cargo.domain.model.cargo.TransportStatus.*;
import static io.pine.examples.cargo.domain.model.handling.HandlingEvent.Type.*;

/**
 * The actual transportation of the cargo, as opposed to
 * the customer requirement (RouteSpecification) and the plan (Itinerary).
 *
 * @author Frank
 * @sinace 2018/8/9 0009.
 */
@Data
@Embeddable
public class Delivery implements ValueObject<Delivery> {
    @Enumerated(EnumType.STRING)
    @Column(name = "transport_status", nullable = false)
    private TransportStatus transportStatus;

    @ManyToOne
    @JoinColumn(name = "last_known_location_id", nullable = false)
    private Location lastKnownLocation;

    @ManyToOne
    @JoinColumn(name = "current_voyage_id", nullable = false)
    private Voyage currentVoyage;

    @Column(name = "is_misdirected", nullable = false)
    private boolean misdirected;

    @Temporal(TemporalType.DATE)
    @Column(name = "eta")
    private Date eta;

    @Embedded
    private HandlingActivity nextExpectedActivity;

    @Column(name = "unloaded_at_dest", nullable = false)
    private boolean isUnloadedAtDestination;

    @Enumerated(EnumType.STRING)
    @Column(name = "routing_status", nullable = false)
    private RoutingStatus routingStatus;

    @Temporal(TemporalType.DATE)
    @Column(name = "calculated_at")
    private Date calculatedAt;

    @ManyToOne
    @JoinColumn(name = "last_event_id", nullable = false)
    private HandlingEvent lastEvent;

    private static final Date ETA_UNKOWN = null;
    private static final HandlingActivity NO_ACTIVITY = null;

    /**
     * Internal constructor.
     *
     * @param lastEvent last event
     * @param itinerary itinerary
     * @param routeSpecification route specification
     */
    private Delivery(HandlingEvent lastEvent, Itinerary itinerary, RouteSpecification routeSpecification) {
        this.calculatedAt = new Date();
        this.lastEvent = lastEvent;

        this.misdirected = calculateMisdirectionStatus(itinerary);
        this.routingStatus = calculateRoutingStatus(itinerary, routeSpecification);
        this.transportStatus = calculateTransportStatus();
        this.lastKnownLocation = calculateLastKnownLocation();
        this.currentVoyage = calculateCurrentVoyage();
        this.eta = calculateEta(itinerary);
        this.nextExpectedActivity = calculateNextExpectedActivity(routeSpecification, itinerary);
        this.isUnloadedAtDestination = calculateUnloadedAtDestination(routeSpecification);
    }

    /**
     * Creates a new delivery snapshot to reflect changes in routing, i.e.
     * when the route specification or the itinerary has changed
     * but no additional handling of the cargo has been performed.
     *
     * @param routeSpecification route specification
     * @param itinerary itinerary
     * @return An up to date delivery
     */
    Delivery updateOnRouting(RouteSpecification routeSpecification, Itinerary itinerary) {
        Assert.notNull(routeSpecification, "Route specification is required");

        return new Delivery(this.lastEvent, itinerary, routeSpecification);
    }

    /**
     * Creates a new delivery snapshot based on the complete handling history of a cargo,
     * as well as its route specification and itinerary.
     *
     * @param routeSpecification route specification
     * @param itinerary itinerary
     * @param handlingHistory delivery history
     * @return An up to date delivery.
     */
    static Delivery derivedFrom(RouteSpecification routeSpecification, Itinerary itinerary, HandlingHistory handlingHistory) {
        Assert.notNull(routeSpecification, "Route specification is required");
        Assert.notNull(handlingHistory, "Delivery history is required");

        final HandlingEvent lastEvent = handlingHistory.mostRecentlyCompletedEvent();
        return new Delivery(lastEvent, itinerary, routeSpecification);
    }

    /**
     * Check if cargo is misdirected.
     * <p/>
     * <ul>
     * <li>A cargo is misdirected if it is in a location that's not in the itinerary.
     * <li>A cargo with no itinerary can not be misdirected.
     * <li>A cargo that has received no handling events can not be misdirected.
     * </ul>
     *
     * @return <code>true</code> if the cargo has been misdirected,
     */
    public boolean isMisdirected() {
        return misdirected;
    }

    /**
     * @return Estimated time of arrival
     */
    public Date estimatedTimeOfArrival() {
        if (eta != ETA_UNKOWN) {
            return new Date(eta.getTime());
        } else {
            return ETA_UNKOWN;
        }
    }

    /**
     * @return The next expected handling activity.
     */
    public HandlingActivity nextExpectedActivity() {
        return nextExpectedActivity;
    }

    /**
     * @return True if the cargo has been unloaded at the final destination.
     */
    public boolean isUnloadedAtDestination() {
        return isUnloadedAtDestination;
    }

    public Location getLastKnownLocation() {
        return this.lastKnownLocation != null ? this.lastKnownLocation : Location.UNKNOWN;
    }

    /**
     * @return When this delivery was calculated.
     */
    public Date calculatedAt() {
        return new Date(calculatedAt.getTime());
    }

    public Voyage getCurrentVoyage() {
        return this.currentVoyage != null ? this.currentVoyage : Voyage.NONE;
    }

    // TODO add currentCarrierMovement (?)


    // --- Internal calculations below ---


    private TransportStatus calculateTransportStatus() {
        if (lastEvent == null) {
            return NOT_RECEIVED;
        }

        switch (lastEvent.getType()) {
            case LOAD:
                return ONBOARD_CARRIER;
            case UNLOAD:
            case RECEIVE:
            case CUSTOMS:
                return IN_PORT;
            case CLAIM:
                return CLAIMED;
            default:
                return UNKNOWN;
        }
    }

    private Location calculateLastKnownLocation() {
        if (lastEvent != null) {
            return lastEvent.getLocation();
        } else {
            return null;
        }
    }

    private Voyage calculateCurrentVoyage() {
        if (transportStatus.equals(ONBOARD_CARRIER) && lastEvent != null) {
            return lastEvent.getVoyage();
        } else {
            return null;
        }
    }

    private boolean calculateMisdirectionStatus(Itinerary itinerary) {
        if (lastEvent == null) {
            return false;
        } else {
            return !itinerary.isExpected(lastEvent);
        }
    }

    private Date calculateEta(Itinerary itinerary) {
        if (onTrack()) {
            return itinerary.finalArrivalDate();
        } else {
            return ETA_UNKOWN;
        }
    }

    private HandlingActivity calculateNextExpectedActivity(RouteSpecification routeSpecification, Itinerary itinerary) {
        if (!onTrack()) { return NO_ACTIVITY; }

        if (lastEvent == null) { return new HandlingActivity(HandlingEvent.Type.RECEIVE, routeSpecification.getOrigin()); }

        switch (lastEvent.getType()) {

            case LOAD:
                for (Leg leg : itinerary.getLegs()) {
                    if (leg.getLoadLocation().sameIdentityAs(lastEvent.getLocation())) {
                        return new HandlingActivity(HandlingEvent.Type.UNLOAD, leg.getUnloadLocation(), leg.getVoyage());
                    }
                }

                return NO_ACTIVITY;

            case UNLOAD:
                for (Iterator<Leg> it = itinerary.getLegs().iterator(); it.hasNext();) {
                    final Leg leg = it.next();
                    if (leg.getUnloadLocation().sameIdentityAs(lastEvent.getLocation())) {
                        if (it.hasNext()) {
                            final Leg nextLeg = it.next();
                            return new HandlingActivity(HandlingEvent.Type.LOAD, nextLeg.getUnloadLocation(), nextLeg.getVoyage());
                        } else {
                            return new HandlingActivity(HandlingEvent.Type.CLAIM, leg.getUnloadLocation());
                        }
                    }
                }

                return NO_ACTIVITY;

            case RECEIVE:
                final Leg firstLeg = itinerary.getLegs().iterator().next();
                return new HandlingActivity(HandlingEvent.Type.LOAD, firstLeg.getLoadLocation(), firstLeg.getVoyage());

            case CLAIM:
            default:
                return NO_ACTIVITY;
        }
    }

    private RoutingStatus calculateRoutingStatus(Itinerary itinerary, RouteSpecification routeSpecification) {
        if (itinerary == null) {
            return NOT_ROUTED;
        } else {
            if (routeSpecification.isSatisfiedBy(itinerary)) {
                return ROUTED;
            } else {
                return MISROUTED;
            }
        }
    }

    private boolean calculateUnloadedAtDestination(RouteSpecification routeSpecification) {
        return lastEvent != null &&
                HandlingEvent.Type.UNLOAD.sameValueAs(lastEvent.getType()) &&
                routeSpecification.getDestination().sameIdentityAs(lastEvent.getLocation());
    }

    private boolean onTrack() {
        return routingStatus.equals(ROUTED) && !misdirected;
    }

    @Override
    public boolean sameValueAs(final Delivery other) {
        return other != null && new EqualsBuilder().
                append(this.transportStatus, other.transportStatus).
                append(this.lastKnownLocation, other.lastKnownLocation).
                append(this.currentVoyage, other.currentVoyage).
                append(this.misdirected, other.misdirected).
                append(this.eta, other.eta).
                append(this.nextExpectedActivity, other.nextExpectedActivity).
                append(this.isUnloadedAtDestination, other.isUnloadedAtDestination).
                append(this.routingStatus, other.routingStatus).
                append(this.calculatedAt, other.calculatedAt).
                append(this.lastEvent, other.lastEvent).
                isEquals();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }

        final Delivery other = (Delivery) o;
        return sameValueAs(other);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().
                append(transportStatus).
                append(lastKnownLocation).
                append(currentVoyage).
                append(misdirected).
                append(eta).
                append(nextExpectedActivity).
                append(isUnloadedAtDestination).
                append(routingStatus).
                append(calculatedAt).
                append(lastEvent).
                toHashCode();
    }
}
