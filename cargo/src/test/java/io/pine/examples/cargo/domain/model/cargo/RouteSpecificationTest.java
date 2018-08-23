package io.pine.examples.cargo.domain.model.cargo;

import io.pine.examples.cargo.domain.model.voyage.Voyage;
import io.pine.examples.cargo.domain.model.voyage.VoyageNumber;
import org.junit.Test;

import java.util.Arrays;

import static io.pine.examples.cargo.common.DateTestUtil.toDate;
import static io.pine.examples.cargo.domain.model.location.SampleLocations.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Frank
 * @sinace 2018/8/14 0014.
 */
public class RouteSpecificationTest {
    final Voyage hongKongTokyoNewYork = new Voyage.Builder(
            new VoyageNumber("V001"), HONGKONG).
            addMovement(TOKYO, toDate("2009-02-01"), toDate("2009-02-05")).
            addMovement(NEWYORK, toDate("2009-02-06"), toDate("2009-02-10")).
            addMovement(HONGKONG, toDate("2009-02-11"), toDate("2009-02-14")).
            build();

    final Voyage dallasNewYorkChicago = new Voyage.Builder(
            new VoyageNumber("V002"), DALLAS).
            addMovement(NEWYORK, toDate("2009-02-06"), toDate("2009-02-07")).
            addMovement(CHICAGO, toDate("2009-02-12"), toDate("2009-02-20")).
            build();

    // TODO:
    // it shouldn't be possible to create Legs that have load/unload locations
    // and/or dates that don't match the voyage's carrier movements.
    final Itinerary itinerary = new Itinerary(Arrays.asList(
            new Leg(hongKongTokyoNewYork, HONGKONG, NEWYORK,
                    toDate("2009-02-01"), toDate("2009-02-10")),
            new Leg(dallasNewYorkChicago, NEWYORK, CHICAGO,
                    toDate("2009-02-12"), toDate("2009-02-20")))
    );

    @Test
    public void testIsSatisfiedBy_Success() {
        RouteSpecification routeSpecification = new RouteSpecification(
                HONGKONG, CHICAGO, toDate("2009-03-01")
        );

        assertTrue(routeSpecification.isSatisfiedBy(itinerary));
    }

    @Test
    public void testIsSatisfiedBy_WrongOrigin() {
        RouteSpecification routeSpecification = new RouteSpecification(
                HANGZOU, CHICAGO, toDate("2009-03-01")
        );

        assertFalse(routeSpecification.isSatisfiedBy(itinerary));
    }

    @Test
    public void testIsSatisfiedBy_WrongDestination() {
        RouteSpecification routeSpecification = new RouteSpecification(
                HONGKONG, DALLAS, toDate("2009-03-01")
        );

        assertFalse(routeSpecification.isSatisfiedBy(itinerary));
    }

    @Test
    public void testIsSatisfiedBy_MissedDeadline() {
        RouteSpecification routeSpecification = new RouteSpecification(
                HONGKONG, CHICAGO, toDate("2009-02-15")
        );

        assertFalse(routeSpecification.isSatisfiedBy(itinerary));
    }
}
