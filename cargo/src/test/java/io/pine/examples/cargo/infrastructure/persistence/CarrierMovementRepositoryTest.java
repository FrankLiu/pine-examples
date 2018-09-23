package io.pine.examples.cargo.infrastructure.persistence;

import io.pine.examples.cargo.common.DateTestUtil;
import io.pine.examples.cargo.domain.model.cargo.*;
import io.pine.examples.cargo.domain.model.handling.HandlingEvent;
import io.pine.examples.cargo.domain.model.handling.HandlingHistory;
import io.pine.examples.cargo.domain.model.location.SampleLocations;
import io.pine.examples.cargo.domain.model.voyage.SampleVoyages;
import io.pine.examples.cargo.domain.model.voyage.Schedule;
import io.pine.examples.cargo.domain.model.voyage.Voyage;
import io.pine.examples.cargo.domain.model.voyage.VoyageNumber;
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
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author Frank
 * @sinace 2018/8/29 0029.
 */
@RunWith(SpringRunner.class)
@DataJpaTest(includeFilters = @ComponentScan.Filter(Repository.class))
public class CarrierMovementRepositoryTest {
    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private VoyageRepository voyageRepository;

    @Before
    public void setup() {
        locationRepository.saveAll(SampleLocations.getAll());
        voyageRepository.saveAll(SampleVoyages.getAll());
    }

    @After
    public void teardown() {
        voyageRepository.deleteAll();
        locationRepository.deleteAll();
    }

    @Test
    public void testFind() {
        List<Voyage> voyages = voyageRepository.findAll();
        assertEquals(15, voyages.size());
        Voyage voyage = voyageRepository.findByVoyageNumber(new VoyageNumber("0301S")).get();
        assertNotNull(voyage);
        assertEquals("0301S", voyage.voyageNumber().idString());
        Schedule schedule = voyage.getSchedule();
        assertNotNull(schedule);
        assertEquals(1, schedule.getCarrierMovements().size());
    }

}
