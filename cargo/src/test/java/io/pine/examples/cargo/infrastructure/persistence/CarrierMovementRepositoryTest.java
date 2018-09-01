package io.pine.examples.cargo.infrastructure.persistence;

import io.pine.examples.cargo.domain.model.location.Location;
import io.pine.examples.cargo.domain.model.location.SampleLocations;
import io.pine.examples.cargo.domain.model.location.UnLocode;
import io.pine.examples.cargo.domain.model.voyage.SampleVoyages;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

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

    @Before
    public void setup() {
        locationRepository.saveAll(SampleLocations.getAll());
        voyageRepository.saveAll(Arrays.asList(
                SampleVoyages.HONGKONG_TO_NEW_YORK,
                SampleVoyages.NEW_YORK_TO_DALLAS,
                SampleVoyages.DALLAS_TO_HELSINKI,
                SampleVoyages.HELSINKI_TO_HONGKONG,
                SampleVoyages.DALLAS_TO_HELSINKI_ALT));
    }

    @After
    public void teardown() {
        locationRepository.deleteAll();
        voyageRepository.deleteAll();
    }

    @Test
    public void testFind() {

    }

}
