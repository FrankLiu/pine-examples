package io.pine.examples.cargo.infrastructure.persistence;

/**
 * @author Frank
 * @sinace 2018/8/27 0027.
 */

import io.pine.examples.cargo.domain.model.location.Location;
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
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest(includeFilters = @ComponentScan.Filter(Repository.class))
public class LocationRepositoryTest {

    @Autowired
    private LocationRepository locationRepository;

    @Before
    public void setup() {
        locationRepository.saveAll(Arrays.asList(
                new Location(new UnLocode("SESTO"), "Stockholm"),
                new Location(new UnLocode("AUMEL"), "Melbourne"),
                new Location(new UnLocode("CNHKG"), "Hongkong"),
                new Location(new UnLocode("JPTOK"), "Tokyo"),
                new Location(new UnLocode("FIHEL"), "Helsinki"),
                new Location(new UnLocode("DEHAM"), "Hamburg"),
                new Location(new UnLocode("USCHI"), "Chicago")
        ));
    }

    @After
    public void tearDown() {
        locationRepository.deleteAll();
    }

    @Test
    public void testFindAll() {
        List<Location> allLocations = locationRepository.findAll();

        assertNotNull(allLocations);
        assertEquals(7, allLocations.size());
    }

    @Test
    public void testFindByUnLocode() {
        final UnLocode melbourne = new UnLocode("AUMEL");
        Optional<Location> location = locationRepository.findByUnLocode(melbourne);
        assertNotNull(location.get());
        assertEquals(melbourne, location.get().getUnLocode());

        assertFalse(locationRepository.findByUnLocode(new UnLocode("NOLOC")).isPresent());
    }
}
