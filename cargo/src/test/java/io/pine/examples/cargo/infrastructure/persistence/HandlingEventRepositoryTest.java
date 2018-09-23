package io.pine.examples.cargo.infrastructure.persistence;

import io.pine.examples.cargo.domain.model.location.Location;
import io.pine.examples.cargo.domain.model.location.SampleLocations;
import io.pine.examples.cargo.domain.model.location.UnLocode;
import org.junit.After;
import org.junit.Before;
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
public class HandlingEventRepositoryTest {
    @Autowired
    private HandlingEventRepository handlingEventRepository;

    @Autowired
    private LocationRepository locationRepository;


    @Before
    public void setup() {
        locationRepository.saveAll(SampleLocations.getAll());
    }

    @After
    public void tearDown() {
        locationRepository.deleteAll();
        handlingEventRepository.deleteAll();
    }

    @Test
    public void testFind() {

    }
}
