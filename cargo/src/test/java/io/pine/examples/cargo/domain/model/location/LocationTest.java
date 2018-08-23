package io.pine.examples.cargo.domain.model.location;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Frank
 * @sinace 2018/8/13 0013.
 */
public class LocationTest {
    @Test
    public void testEquals() {
        // Same UN locode - equal
        assertTrue(new Location(new UnLocode("ATEST"),"test-name").
                equals(new Location(new UnLocode("ATEST"),"test-name")));

        // Different UN locodes - not equal
        assertFalse(new Location(new UnLocode("ATEST"),"test-name").
                equals(new Location(new UnLocode("TESTB"), "test-name")));

        // Always equal to itself
        Location location = new Location(new UnLocode("ATEST"),"test-name");
        assertTrue(location.equals(location));

        // Never equal to null
        assertFalse(location.equals(null));

        // Special UNKNOWN location is equal to itself
        assertTrue(Location.UNKNOWN.equals(Location.UNKNOWN));

        try {
            new Location(null, null);
            fail("Should not allow any null constructor arguments");
        } catch (IllegalArgumentException expected) {}
    }

}
