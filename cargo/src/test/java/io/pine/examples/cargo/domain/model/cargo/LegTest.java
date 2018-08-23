package io.pine.examples.cargo.domain.model.cargo;

import org.junit.Test;

import static org.junit.Assert.fail;

/**
 * @author Frank
 * @sinace 2018/8/14 0014.
 */
public class LegTest {
    @Test
    public void testConstructor() throws Exception {
        try {
            new Leg(null,null,null,null,null);
            fail("Should not accept null constructor arguments");
        } catch (IllegalArgumentException expected) {}
    }
}
