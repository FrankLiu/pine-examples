package io.pine.examples.cargo.infrastructure.persistence;

import io.pine.examples.cargo.domain.model.cargo.*;
import io.pine.examples.cargo.domain.model.location.Location;
import io.pine.examples.cargo.domain.model.location.SampleLocations;
import io.pine.examples.cargo.domain.model.voyage.SampleVoyages;
import io.pine.examples.cargo.domain.model.voyage.VoyageNumber;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;
import java.time.temporal.Temporal;
import java.util.Collections;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author Frank
 * @sinace 2018/9/23 0023.
 */
@RunWith(SpringRunner.class)
@DataJpaTest(includeFilters = @ComponentScan.Filter(Repository.class))
public class CargoRepositoryTest {
    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private VoyageRepository voyageRepository;

    @Autowired
    private CargoRepository cargoRepository;

    @Before
    public void setup() {
        locationRepository.saveAll(SampleLocations.getAll());
        voyageRepository.saveAll(SampleVoyages.getAll());

        TrackingId trackingId = new TrackingId("AAA");
        Location origin = locationRepository.findByUnLocode(SampleLocations.STOCKHOLM.getUnLocode()).get();
        Location destination = locationRepository.findByUnLocode(SampleLocations.MELBOURNE.getUnLocode()).get();

        Cargo abc123 = new Cargo(trackingId, new RouteSpecification(origin, destination, new Date(System.currentTimeMillis()+24*60*60)));

        abc123.assignToRoute(new Itinerary(
                Collections.singletonList(
                        new Leg(voyageRepository.findByVoyageNumber(new VoyageNumber("0300A")).get(),
                                locationRepository.findByUnLocode(SampleLocations.STOCKHOLM.getUnLocode()).get(),
                                locationRepository.findByUnLocode(SampleLocations.MELBOURNE.getUnLocode()).get(),
                                new Date(), new Date())
                ))
        );
        cargoRepository.save(abc123);
    }

    @After
    public void teardown() {
        cargoRepository.deleteAll();
        voyageRepository.deleteAll();
        locationRepository.deleteAll();
    }

    @Test
    public void testFind() {
        Cargo cargo = cargoRepository.findByTrackingId(new TrackingId("AAA")).get();
        assertNotNull(cargo);
        assertEquals("AAA", cargo.getTrackingId().toString());
        assertEquals("Stockholm", SampleLocations.STOCKHOLM.getName());
        assertEquals(1, cargo.getItinerary().getLegs().size());
    }

}
