package io.pine.examples.cargo.domain.model.cargo;

import io.pine.examples.cargo.common.DateTestUtil;
import io.pine.examples.cargo.domain.model.handling.HandlingEvent;
import io.pine.examples.cargo.domain.model.handling.HandlingHistory;
import io.pine.examples.cargo.domain.model.location.Location;
import io.pine.examples.cargo.domain.model.voyage.Voyage;
import io.pine.examples.cargo.domain.model.voyage.VoyageNumber;
import org.junit.Before;
import org.junit.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static io.pine.examples.cargo.domain.model.cargo.RoutingStatus.MISROUTED;
import static io.pine.examples.cargo.domain.model.cargo.RoutingStatus.NOT_ROUTED;
import static io.pine.examples.cargo.domain.model.cargo.RoutingStatus.ROUTED;
import static io.pine.examples.cargo.domain.model.cargo.TransportStatus.NOT_RECEIVED;
import static io.pine.examples.cargo.domain.model.location.SampleLocations.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Frank
 * @sinace 2018/8/14 0014.
 */
public class CargoTest {
    private List<HandlingEvent> events;
    private Voyage voyage;

    @Before
    public void setUp() throws Exception {
        events = new ArrayList<HandlingEvent>();

        voyage = new Voyage.Builder(new VoyageNumber("0123"), STOCKHOLM).
                addMovement(HAMBURG, new Date(), new Date()).
                addMovement(HONGKONG, new Date(), new Date()).
                addMovement(MELBOURNE, new Date(), new Date()).
                build();
    }

    @Test
    public void testConstruction() throws Exception {
        final TrackingId trackingId = new TrackingId("XYZ");
        final Date arrivalDeadline = DateTestUtil.toDate("2009-03-13");
        final RouteSpecification routeSpecification = new RouteSpecification(
                STOCKHOLM, MELBOURNE, arrivalDeadline
        );

        final Cargo cargo = new Cargo(trackingId, routeSpecification);

        assertEquals(NOT_ROUTED, cargo.getDelivery().getRoutingStatus());
        assertEquals(NOT_RECEIVED, cargo.getDelivery().getTransportStatus());
        assertEquals(Location.UNKNOWN, cargo.getDelivery().getLastKnownLocation());
        assertEquals(Voyage.NONE, cargo.getDelivery().getCurrentVoyage());
    }

    @Test
    public void testRoutingStatus() throws Exception {
        final Cargo cargo = new Cargo(new TrackingId("XYZ"), new RouteSpecification(STOCKHOLM, MELBOURNE, new Date()));
        final Itinerary good = new Itinerary();
        final Itinerary bad = new Itinerary();
        final RouteSpecification acceptOnlyGood = new RouteSpecification(cargo.getOrigin(), cargo.getRouteSpecification().getDestination(), new Date()) {
            @Override
            public boolean isSatisfiedBy(Itinerary itinerary) {
                return itinerary == good;
            }
        };

        cargo.specifyNewRoute(acceptOnlyGood);

        assertEquals(NOT_ROUTED, cargo.getDelivery().getRoutingStatus());

        cargo.assignToRoute(bad);
        assertEquals(MISROUTED, cargo.getDelivery().getRoutingStatus());

        cargo.assignToRoute(good);
        assertEquals(ROUTED, cargo.getDelivery().getRoutingStatus());
    }

    @Test
    public void testlastKnownLocationUnknownWhenNoEvents() throws Exception {
        Cargo cargo = new Cargo(new TrackingId("XYZ"), new RouteSpecification(STOCKHOLM, MELBOURNE, new Date()));

        assertEquals(Location.UNKNOWN, cargo.getDelivery().getLastKnownLocation());
    }

    @Test
    public void testlastKnownLocationReceived() throws Exception {
        Cargo cargo = populateCargoReceivedStockholm();

        assertEquals(STOCKHOLM, cargo.getDelivery().getLastKnownLocation());
    }

    @Test
    public void testlastKnownLocationClaimed() throws Exception {
        Cargo cargo = populateCargoClaimedMelbourne();

        assertEquals(MELBOURNE, cargo.getDelivery().getLastKnownLocation());
    }

    @Test
    public void testlastKnownLocationUnloaded() throws Exception {
        Cargo cargo = populateCargoOffHongKong();

        assertEquals(HONGKONG, cargo.getDelivery().getLastKnownLocation());
    }

    @Test
    public void testlastKnownLocationloaded() throws Exception {
        Cargo cargo = populateCargoOnHamburg();

        assertEquals(HAMBURG, cargo.getDelivery().getLastKnownLocation());
    }

    @Test
    public void testEquality() throws Exception {
        RouteSpecification spec1 = new RouteSpecification(STOCKHOLM, HONGKONG, new Date());
        RouteSpecification spec2 = new RouteSpecification(STOCKHOLM, MELBOURNE, new Date());
        Cargo c1 = new Cargo(new TrackingId("ABC"), spec1);
        Cargo c2 = new Cargo(new TrackingId("CBA"), spec1);
        Cargo c3 = new Cargo(new TrackingId("ABC"), spec2);
        Cargo c4 = new Cargo(new TrackingId("ABC"), spec1);

        assertTrue("Cargos should be equal when TrackingIDs are equal", c1.equals(c4));
        assertTrue("Cargos should be equal when TrackingIDs are equal", c1.equals(c3));
        assertTrue("Cargos should be equal when TrackingIDs are equal", c3.equals(c4));
        assertFalse("Cargos are not equal when TrackingID differ", c1.equals(c2));
    }

    @Test
    public void testIsUnloadedAtFinalDestination() throws Exception {
        Cargo cargo = setUpCargoWithItinerary(HANGZOU, TOKYO, NEWYORK);
        assertFalse(cargo.getDelivery().isUnloadedAtDestination());

        // Adding an event unrelated to unloading at final destination
        events.add(
                new HandlingEvent(cargo, new Date(10), new Date(), HandlingEvent.Type.RECEIVE, HANGZOU));
        cargo.deriveDeliveryProgress(new HandlingHistory(events));
        assertFalse(cargo.getDelivery().isUnloadedAtDestination());

        Voyage voyage = new Voyage.Builder(new VoyageNumber("0123"), HANGZOU).
                addMovement(NEWYORK, new Date(), new Date()).
                build();

        // Adding an unload event, but not at the final destination
        events.add(
                new HandlingEvent(cargo, new Date(20), new Date(), HandlingEvent.Type.UNLOAD, TOKYO, voyage));
        cargo.deriveDeliveryProgress(new HandlingHistory(events));
        assertFalse(cargo.getDelivery().isUnloadedAtDestination());

        // Adding an event in the final destination, but not unload
        events.add(
                new HandlingEvent(cargo, new Date(30), new Date(), HandlingEvent.Type.CUSTOMS, NEWYORK));
        cargo.deriveDeliveryProgress(new HandlingHistory(events));
        assertFalse(cargo.getDelivery().isUnloadedAtDestination());

        // Finally, cargo is unloaded at final destination
        events.add(
                new HandlingEvent(cargo, new Date(40), new Date(), HandlingEvent.Type.UNLOAD, NEWYORK, voyage));
        cargo.deriveDeliveryProgress(new HandlingHistory(events));
        assertTrue(cargo.getDelivery().isUnloadedAtDestination());
    }

    // TODO: Generate test data some better way
    private Cargo populateCargoReceivedStockholm() throws Exception {
        final Cargo cargo = new Cargo(new TrackingId("XYZ"), new RouteSpecification(STOCKHOLM, MELBOURNE, new Date()));

        HandlingEvent he = new HandlingEvent(cargo, getDate("2007-12-01"), new Date(), HandlingEvent.Type.RECEIVE, STOCKHOLM);
        events.add(he);
        cargo.deriveDeliveryProgress(new HandlingHistory(events));

        return cargo;
    }

    private Cargo populateCargoClaimedMelbourne() throws Exception {
        final Cargo cargo = populateCargoOffMelbourne();

        events.add(new HandlingEvent(cargo, getDate("2007-12-09"), new Date(), HandlingEvent.Type.CLAIM, MELBOURNE));
        cargo.deriveDeliveryProgress(new HandlingHistory(events));

        return cargo;
    }

    private Cargo populateCargoOffHongKong() throws Exception {
        final Cargo cargo = new Cargo(new TrackingId("XYZ"), new RouteSpecification(STOCKHOLM, MELBOURNE, new Date()));


        events.add(new HandlingEvent(cargo, getDate("2007-12-01"), new Date(), HandlingEvent.Type.LOAD, STOCKHOLM, voyage));
        events.add(new HandlingEvent(cargo, getDate("2007-12-02"), new Date(), HandlingEvent.Type.UNLOAD, HAMBURG, voyage));

        events.add(new HandlingEvent(cargo, getDate("2007-12-03"), new Date(), HandlingEvent.Type.LOAD, HAMBURG, voyage));
        events.add(new HandlingEvent(cargo, getDate("2007-12-04"), new Date(), HandlingEvent.Type.UNLOAD, HONGKONG, voyage));

        cargo.deriveDeliveryProgress(new HandlingHistory(events));
        return cargo;
    }

    private Cargo populateCargoOnHamburg() throws Exception {
        final Cargo cargo = new Cargo(new TrackingId("XYZ"), new RouteSpecification(STOCKHOLM, MELBOURNE, new Date()));

        events.add(new HandlingEvent(cargo, getDate("2007-12-01"), new Date(), HandlingEvent.Type.LOAD, STOCKHOLM, voyage));
        events.add(new HandlingEvent(cargo, getDate("2007-12-02"), new Date(), HandlingEvent.Type.UNLOAD, HAMBURG, voyage));
        events.add(new HandlingEvent(cargo, getDate("2007-12-03"), new Date(), HandlingEvent.Type.LOAD, HAMBURG, voyage));

        cargo.deriveDeliveryProgress(new HandlingHistory(events));
        return cargo;
    }

    private Cargo populateCargoOffMelbourne() throws Exception {
        final Cargo cargo = new Cargo(new TrackingId("XYZ"), new RouteSpecification(STOCKHOLM, MELBOURNE, new Date()));

        events.add(new HandlingEvent(cargo, getDate("2007-12-01"), new Date(), HandlingEvent.Type.LOAD, STOCKHOLM, voyage));
        events.add(new HandlingEvent(cargo, getDate("2007-12-02"), new Date(), HandlingEvent.Type.UNLOAD, HAMBURG, voyage));

        events.add(new HandlingEvent(cargo, getDate("2007-12-03"), new Date(), HandlingEvent.Type.LOAD, HAMBURG, voyage));
        events.add(new HandlingEvent(cargo, getDate("2007-12-04"), new Date(), HandlingEvent.Type.UNLOAD, HONGKONG, voyage));

        events.add(new HandlingEvent(cargo, getDate("2007-12-05"), new Date(), HandlingEvent.Type.LOAD, HONGKONG, voyage));
        events.add(new HandlingEvent(cargo, getDate("2007-12-07"), new Date(), HandlingEvent.Type.UNLOAD, MELBOURNE, voyage));

        cargo.deriveDeliveryProgress(new HandlingHistory(events));
        return cargo;
    }

    private Cargo populateCargoOnHongKong() throws Exception {
        final Cargo cargo = new Cargo(new TrackingId("XYZ"), new RouteSpecification(STOCKHOLM, MELBOURNE, new Date()));

        events.add(new HandlingEvent(cargo, getDate("2007-12-01"), new Date(), HandlingEvent.Type.LOAD, STOCKHOLM, voyage));
        events.add(new HandlingEvent(cargo, getDate("2007-12-02"), new Date(), HandlingEvent.Type.UNLOAD, HAMBURG, voyage));

        events.add(new HandlingEvent(cargo, getDate("2007-12-03"), new Date(), HandlingEvent.Type.LOAD, HAMBURG, voyage));
        events.add(new HandlingEvent(cargo, getDate("2007-12-04"), new Date(), HandlingEvent.Type.UNLOAD, HONGKONG, voyage));

        events.add(new HandlingEvent(cargo, getDate("2007-12-05"), new Date(), HandlingEvent.Type.LOAD, HONGKONG, voyage));

        cargo.deriveDeliveryProgress(new HandlingHistory(events));
        return cargo;
    }

    @Test
    public void testIsMisdirected() throws Exception {
        //A cargo with no itinerary is not misdirected
        Cargo cargo = new Cargo(new TrackingId("TRKID"), new RouteSpecification(SHANGHAI, GOTHENBURG, new Date()));
        assertFalse(cargo.getDelivery().isMisdirected());

        cargo = setUpCargoWithItinerary(SHANGHAI, ROTTERDAM, GOTHENBURG);

        //A cargo with no handling events is not misdirected
        assertFalse(cargo.getDelivery().isMisdirected());

        Collection<HandlingEvent> handlingEvents = new ArrayList<HandlingEvent>();

        //Happy path
        handlingEvents.add(new HandlingEvent(cargo, new Date(10), new Date(20), HandlingEvent.Type.RECEIVE, SHANGHAI));
        handlingEvents.add(new HandlingEvent(cargo, new Date(30), new Date(40), HandlingEvent.Type.LOAD, SHANGHAI, voyage));
        handlingEvents.add(new HandlingEvent(cargo, new Date(50), new Date(60), HandlingEvent.Type.UNLOAD, ROTTERDAM, voyage));
        handlingEvents.add(new HandlingEvent(cargo, new Date(70), new Date(80), HandlingEvent.Type.LOAD, ROTTERDAM, voyage));
        handlingEvents.add(new HandlingEvent(cargo, new Date(90), new Date(100), HandlingEvent.Type.UNLOAD, GOTHENBURG, voyage));
        handlingEvents.add(new HandlingEvent(cargo, new Date(110), new Date(120), HandlingEvent.Type.CLAIM, GOTHENBURG));
        handlingEvents.add(new HandlingEvent(cargo, new Date(130), new Date(140), HandlingEvent.Type.CUSTOMS, GOTHENBURG));

        events.addAll(handlingEvents);
        cargo.deriveDeliveryProgress(new HandlingHistory(events));
        assertFalse(cargo.getDelivery().isMisdirected());

        //Try a couple of failing ones

        cargo = setUpCargoWithItinerary(SHANGHAI, ROTTERDAM, GOTHENBURG);
        handlingEvents = new ArrayList<HandlingEvent>();

        handlingEvents.add(new HandlingEvent(cargo, new Date(), new Date(), HandlingEvent.Type.RECEIVE, HANGZOU));
        events.addAll(handlingEvents);
        cargo.deriveDeliveryProgress(new HandlingHistory(events));

        assertTrue(cargo.getDelivery().isMisdirected());


        cargo = setUpCargoWithItinerary(SHANGHAI, ROTTERDAM, GOTHENBURG);
        handlingEvents = new ArrayList<HandlingEvent>();

        handlingEvents.add(new HandlingEvent(cargo, new Date(10), new Date(20), HandlingEvent.Type.RECEIVE, SHANGHAI));
        handlingEvents.add(new HandlingEvent(cargo, new Date(30), new Date(40), HandlingEvent.Type.LOAD, SHANGHAI, voyage));
        handlingEvents.add(new HandlingEvent(cargo, new Date(50), new Date(60), HandlingEvent.Type.UNLOAD, ROTTERDAM, voyage));
        handlingEvents.add(new HandlingEvent(cargo, new Date(70), new Date(80), HandlingEvent.Type.LOAD, ROTTERDAM, voyage));

        events.addAll(handlingEvents);
        cargo.deriveDeliveryProgress(new HandlingHistory(events));

        assertTrue(cargo.getDelivery().isMisdirected());


        cargo = setUpCargoWithItinerary(SHANGHAI, ROTTERDAM, GOTHENBURG);
        handlingEvents = new ArrayList<HandlingEvent>();

        handlingEvents.add(new HandlingEvent(cargo, new Date(10), new Date(20), HandlingEvent.Type.RECEIVE, SHANGHAI));
        handlingEvents.add(new HandlingEvent(cargo, new Date(30), new Date(40), HandlingEvent.Type.LOAD, SHANGHAI, voyage));
        handlingEvents.add(new HandlingEvent(cargo, new Date(50), new Date(60), HandlingEvent.Type.UNLOAD, ROTTERDAM, voyage));
        handlingEvents.add(new HandlingEvent(cargo, new Date(), new Date(), HandlingEvent.Type.CLAIM, ROTTERDAM));

        events.addAll(handlingEvents);
        cargo.deriveDeliveryProgress(new HandlingHistory(events));

        assertTrue(cargo.getDelivery().isMisdirected());
    }

    private Cargo setUpCargoWithItinerary(Location origin, Location midpoint, Location destination) {
        Cargo cargo = new Cargo(new TrackingId("CARGO1"), new RouteSpecification(origin, destination, new Date()));

        Itinerary itinerary = new Itinerary(
                Arrays.asList(
                        new Leg(voyage, origin, midpoint, new Date(), new Date()),
                        new Leg(voyage, midpoint, destination, new Date(), new Date())
                )
        );

        cargo.assignToRoute(itinerary);
        return cargo;
    }

    /**
     * Parse an ISO 8601 (YYYY-MM-DD) String to Date
     *
     * @param isoFormat String to parse.
     * @return Created date instance.
     * @throws ParseException Thrown if parsing fails.
     */
    private Date getDate(String isoFormat) throws ParseException {
        final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.parse(isoFormat);
    }
}
