package io.pine.examples.cargo.domain.model.handling;

import io.pine.examples.cargo.domain.model.cargo.Cargo;
import io.pine.examples.cargo.domain.model.cargo.RouteSpecification;
import io.pine.examples.cargo.domain.model.cargo.TrackingId;
import io.pine.examples.cargo.domain.model.voyage.Voyage;
import io.pine.examples.cargo.domain.model.voyage.VoyageNumber;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static io.pine.examples.cargo.common.DateTestUtil.toDate;
import static io.pine.examples.cargo.domain.model.location.SampleLocations.DALLAS;
import static io.pine.examples.cargo.domain.model.location.SampleLocations.HONGKONG;
import static io.pine.examples.cargo.domain.model.location.SampleLocations.SHANGHAI;
import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

/**
 * @author Frank
 * @sinace 2018/8/14 0014.
 */
public class HandlingHistoryTest {
    Cargo cargo;
    Voyage voyage;
    HandlingEvent event1;
    HandlingEvent event1duplicate;
    HandlingEvent event2;
    HandlingHistory handlingHistory;

    @Before
    public void setUp() throws Exception {
        cargo = new Cargo(new TrackingId("ABC"), new RouteSpecification(SHANGHAI, DALLAS, toDate("2009-04-01")));
        voyage = new Voyage.Builder(new VoyageNumber("X25"), HONGKONG).
                addMovement(SHANGHAI, new Date(), new Date()).
                addMovement(DALLAS, new Date(), new Date()).
                build();
        event1 = new HandlingEvent(cargo, toDate("2009-03-05"), new Date(100), HandlingEvent.Type.LOAD, SHANGHAI, voyage);
        event1duplicate = new HandlingEvent(cargo, toDate("2009-03-05"), new Date(200), HandlingEvent.Type.LOAD, SHANGHAI, voyage);
        event2 = new HandlingEvent(cargo, toDate("2009-03-10"), new Date(150), HandlingEvent.Type.UNLOAD, DALLAS, voyage);

        handlingHistory = new HandlingHistory(asList(event2, event1, event1duplicate));
    }

    @Test
    public void testDistinctEventsByCompletionTime() {
        assertEquals(asList(event1, event2), handlingHistory.distinctEventsByCompletionTime());
    }

    @Test
    public void testMostRecentlyCompletedEvent() {
        assertEquals(event2, handlingHistory.mostRecentlyCompletedEvent());
    }
}
