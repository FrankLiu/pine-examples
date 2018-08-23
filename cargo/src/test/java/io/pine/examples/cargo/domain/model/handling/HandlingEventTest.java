package io.pine.examples.cargo.domain.model.handling;

import io.pine.examples.cargo.domain.model.cargo.Cargo;
import io.pine.examples.cargo.domain.model.cargo.RouteSpecification;
import io.pine.examples.cargo.domain.model.cargo.TrackingId;
import io.pine.examples.cargo.domain.model.location.SampleLocations;
import io.pine.examples.cargo.domain.model.voyage.SampleVoyages;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Date;

import static io.pine.examples.cargo.domain.model.handling.HandlingEvent.Type.*;
import static org.junit.Assert.*;

/**
 * @author Frank
 * @sinace 2018/8/14 0014.
 */
public class HandlingEventTest {
    private Cargo cargo;

    @Before
    public void setUp() throws Exception {
        TrackingId trackingId = new TrackingId("XYZ");
        RouteSpecification routeSpecification = new RouteSpecification(SampleLocations.HONGKONG, SampleLocations.NEWYORK, new Date());
        cargo = new Cargo(trackingId, routeSpecification);
    }

    @Test
    public void testNewWithCarrierMovement() throws Exception {

        HandlingEvent e1 = new HandlingEvent(cargo, new Date(), new Date(), HandlingEvent.Type.LOAD, SampleLocations.HONGKONG, SampleVoyages.CM003);
        assertEquals(SampleLocations.HONGKONG, e1.getLocation());

        HandlingEvent e2 = new HandlingEvent(cargo, new Date(), new Date(), HandlingEvent.Type.UNLOAD, SampleLocations.NEWYORK, SampleVoyages.CM003);
        assertEquals(SampleLocations.NEWYORK, e2.getLocation());

        // These event types prohibit a carrier movement association
        for (HandlingEvent.Type type : Arrays.asList(HandlingEvent.Type.CLAIM, HandlingEvent.Type.RECEIVE, HandlingEvent.Type.CUSTOMS)) {
            try {
                new HandlingEvent(cargo, new Date(), new Date(), type, SampleLocations.HONGKONG, SampleVoyages.CM003);
                fail("Handling event type " + type + " prohibits carrier movement");
            } catch (IllegalArgumentException expected) {}
        }

        // These event types requires a carrier movement association
        for (HandlingEvent.Type type : Arrays.asList(HandlingEvent.Type.LOAD, HandlingEvent.Type.UNLOAD)) {
            try {
                new HandlingEvent(cargo, new Date(), new Date(), type, SampleLocations.HONGKONG, null);
                fail("Handling event type " + type + " requires carrier movement");
            } catch (IllegalArgumentException expected) {}
        }
    }

    @Test
    public void testNewWithLocation() throws Exception {
        HandlingEvent e1 = new HandlingEvent(cargo, new Date(), new Date(), HandlingEvent.Type.CLAIM, SampleLocations.HELSINKI);
        assertEquals(SampleLocations.HELSINKI, e1.getLocation());
    }

    @Test
    public void testCurrentLocationLoadEvent() throws Exception {

        HandlingEvent ev = new HandlingEvent(cargo, new Date(), new Date(), HandlingEvent.Type.LOAD, SampleLocations.CHICAGO, SampleVoyages.CM004);

        assertEquals(SampleLocations.CHICAGO, ev.getLocation());
    }

    @Test
    public void testCurrentLocationUnloadEvent() throws Exception {
        HandlingEvent ev = new HandlingEvent(cargo, new Date(), new Date(), HandlingEvent.Type.UNLOAD, SampleLocations.HAMBURG, SampleVoyages.CM004);

        assertEquals(SampleLocations.HAMBURG, ev.getLocation());
    }

    @Test
    public void testCurrentLocationReceivedEvent() throws Exception {
        HandlingEvent ev = new HandlingEvent(cargo, new Date(), new Date(), HandlingEvent.Type.RECEIVE, SampleLocations.CHICAGO);

        assertEquals(SampleLocations.CHICAGO, ev.getLocation());
    }

    @Test
    public void testCurrentLocationClaimedEvent() throws Exception {
        HandlingEvent ev = new HandlingEvent(cargo, new Date(), new Date(), HandlingEvent.Type.CLAIM, SampleLocations.CHICAGO);

        assertEquals(SampleLocations.CHICAGO, ev.getLocation());
    }

    @Test
    public void testParseType() throws Exception {
        assertEquals(HandlingEvent.Type.CLAIM, valueOf("CLAIM"));
        assertEquals(HandlingEvent.Type.LOAD, valueOf("LOAD"));
        assertEquals(HandlingEvent.Type.UNLOAD, valueOf("UNLOAD"));
        assertEquals(HandlingEvent.Type.RECEIVE, valueOf("RECEIVE"));
    }

    @Test
    public void testParseTypeIllegal() throws Exception {
        try {
            valueOf("NOT_A_HANDLING_EVENT_TYPE");
            assertTrue("Expected IllegaArgumentException to be thrown", false);
        } catch (IllegalArgumentException e) {
            // All's well
        }
    }

    @Test
    public void testEqualsAndSameAs() throws Exception {
        Date timeOccured = new Date();
        Date timeRegistered = new Date();

        HandlingEvent ev1 = new HandlingEvent(cargo, timeOccured, timeRegistered, HandlingEvent.Type.LOAD, SampleLocations.CHICAGO, SampleVoyages.CM005);
        HandlingEvent ev2 = new HandlingEvent(cargo, timeOccured, timeRegistered, HandlingEvent.Type.LOAD, SampleLocations.CHICAGO, SampleVoyages.CM005);

        // Two handling events are not equal() even if all non-uuid fields are identical
        assertTrue(ev1.equals(ev2));
        assertTrue(ev2.equals(ev1));

        assertTrue(ev1.equals(ev1));

        assertFalse(ev2.equals(null));
        assertFalse(ev2.equals(new Object()));
    }

}
