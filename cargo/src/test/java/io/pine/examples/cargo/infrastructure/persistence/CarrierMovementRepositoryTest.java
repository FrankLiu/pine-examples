package io.pine.examples.cargo.infrastructure.persistence;

import io.pine.examples.cargo.common.DateTestUtil;
import io.pine.examples.cargo.domain.model.cargo.*;
import io.pine.examples.cargo.domain.model.handling.HandlingEvent;
import io.pine.examples.cargo.domain.model.handling.HandlingHistory;
import io.pine.examples.cargo.domain.model.location.SampleLocations;
import io.pine.examples.cargo.domain.model.voyage.SampleVoyages;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static io.pine.examples.cargo.common.DateTestUtil.toDate;
import static io.pine.examples.cargo.domain.model.location.SampleLocations.*;

/**
 * @author Frank
 * @sinace 2018/8/29 0029.
 */
@RunWith(SpringRunner.class)
@DataJpaTest(includeFilters = @ComponentScan.Filter(Repository.class))
public class CarrierMovementRepositoryTest {
    @Autowired
    private VoyageRepository voyageRepository;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private CargoRepository cargoRepository;

    @Autowired
    private HandlingEventRepository handlingEventRepository;

    @Before
    public void setup() {
        locationRepository.saveAll(SampleLocations.getAll());
        voyageRepository.saveAll(Arrays.asList(
                SampleVoyages.HONGKONG_TO_NEW_YORK,
                SampleVoyages.NEW_YORK_TO_DALLAS,
                SampleVoyages.DALLAS_TO_HELSINKI,
                SampleVoyages.HELSINKI_TO_HONGKONG,
                SampleVoyages.DALLAS_TO_HELSINKI_ALT));

        RouteSpecification routeSpecification = new RouteSpecification(HONGKONG, HELSINKI, toDate("2009-03-15"));
        TrackingId trackingId = new TrackingId("ABC123");
        Cargo abc123 = new Cargo(trackingId, routeSpecification);

        List<Leg> legs = new ArrayList<>();
        legs.add(new Leg(SampleVoyages.HONGKONG_TO_NEW_YORK, HONGKONG, NEWYORK, toDate("2009-03-02"), toDate("2009-03-05")));
        legs.add(new Leg(SampleVoyages.NEW_YORK_TO_DALLAS, NEWYORK, DALLAS, toDate("2009-03-06"), toDate("2009-03-08")));
        legs.add(new Leg(SampleVoyages.DALLAS_TO_HELSINKI, DALLAS, HELSINKI, toDate("2009-03-09"), toDate("2009-03-12")));
        Itinerary itinerary = new Itinerary(legs);
        abc123.assignToRoute(itinerary);
        cargoRepository.save(abc123);

        HandlingEvent event1 = new HandlingEvent(abc123, new Date(), toDate("2009-03-01"), HandlingEvent.Type.RECEIVE, HONGKONG);
        HandlingEvent event2 = new HandlingEvent(abc123, new Date(), toDate("2009-03-02"), HandlingEvent.Type.LOAD, HONGKONG, SampleVoyages.HONGKONG_TO_NEW_YORK);
        HandlingEvent event3 = new HandlingEvent(abc123, new Date(), toDate("2009-03-05"), HandlingEvent.Type.UNLOAD, NEWYORK, SampleVoyages.HONGKONG_TO_NEW_YORK);
        handlingEventRepository.saveAll(Arrays.asList(
           event1, event2, event3
        ));

        HandlingHistory handlingHistory = new HandlingHistory(handlingEventRepository.findAllByTrackingId(trackingId));
        abc123.deriveDeliveryProgress(handlingHistory);
        cargoRepository.save(abc123);

    }

    @After
    public void teardown() {
//        cargoRepository.deleteAll();
//        voyageRepository.deleteAll();
//        locationRepository.deleteAll();
    }

    @Test
    public void testFind() {

    }

}
