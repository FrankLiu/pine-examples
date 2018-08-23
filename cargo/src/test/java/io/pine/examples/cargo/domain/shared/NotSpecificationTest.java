package io.pine.examples.cargo.domain.shared;

import org.junit.Test;

import static org.junit.Assert.*;


/**
 * @author Frank
 * @sinace 2018/8/13 0013.
 */
public class NotSpecificationTest {
    @Test
    public void testAndIsSatisifedBy() throws Exception {
        AlwaysTrueSpec trueSpec = new AlwaysTrueSpec();
        AlwaysFalseSpec falseSpec = new AlwaysFalseSpec();

        NotSpecification<Object> notSpecification = new NotSpecification<Object>(trueSpec);
        assertFalse(notSpecification.isSatisfiedBy(new Object()));

        notSpecification = new NotSpecification<Object>(falseSpec);
        assertTrue(notSpecification.isSatisfiedBy(new Object()));

    }
}
