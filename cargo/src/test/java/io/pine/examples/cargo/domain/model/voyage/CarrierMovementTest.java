package io.pine.examples.cargo.domain.model.voyage;

import org.junit.Test;

import java.util.Date;

import static io.pine.examples.cargo.domain.model.location.SampleLocations.HAMBURG;
import static io.pine.examples.cargo.domain.model.location.SampleLocations.STOCKHOLM;
import static org.junit.Assert.*;

/**
 * @author Frank
 * @sinace 2018/8/13 0013.
 */
public class CarrierMovementTest {
    @Test
    public void testConstructor() throws Exception {
        try {
            new CarrierMovement(null, null, new Date(), new Date());
            fail("Should not accept null constructor arguments");
        } catch (IllegalArgumentException expected) {}

        try {
            new CarrierMovement(STOCKHOLM, null, new Date(), new Date());
            fail("Should not accept null constructor arguments");
        } catch (IllegalArgumentException expected) {}

        // Legal
        new CarrierMovement(STOCKHOLM, HAMBURG, new Date(), new Date());
    }

    @Test
    public void testSameValueAsEqualsHashCode() throws Exception {
        CarrierMovement cm1 = new CarrierMovement(STOCKHOLM, HAMBURG, new Date(), new Date());
        CarrierMovement cm2 = new CarrierMovement(STOCKHOLM, HAMBURG, new Date(), new Date());
        CarrierMovement cm3 = new CarrierMovement(HAMBURG, STOCKHOLM, new Date(), new Date());
        CarrierMovement cm4 = new CarrierMovement(HAMBURG, STOCKHOLM, new Date(), new Date());

        assertTrue(cm1.sameValueAs(cm2));
        assertFalse(cm2.sameValueAs(cm3));
        assertTrue(cm3.sameValueAs(cm4));

        assertTrue(cm1.equals(cm2));
        assertFalse(cm2.equals(cm3));
        assertTrue(cm3.equals(cm4));

        assertTrue(cm1.hashCode() == cm2.hashCode());
        assertFalse(cm2.hashCode() == cm3.hashCode());
        assertTrue(cm3.hashCode() == cm4.hashCode());
    }
}
