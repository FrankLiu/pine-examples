package io.pine.examples.cargo.domain.shared;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Frank
 * @sinace 2018/8/13 0013.
 */
public class OrSpecificationTest {
    @Test
    public void testAndIsSatisifedBy() throws Exception {
        AlwaysTrueSpec trueSpec = new AlwaysTrueSpec();
        AlwaysFalseSpec falseSpec = new AlwaysFalseSpec();

        OrSpecification<Object> orSpecification = new OrSpecification<Object>(trueSpec, trueSpec);
        assertTrue(orSpecification.isSatisfiedBy(new Object()));

        orSpecification = new OrSpecification<Object>(falseSpec, trueSpec);
        assertTrue(orSpecification.isSatisfiedBy(new Object()));

        orSpecification = new OrSpecification<Object>(trueSpec, falseSpec);
        assertTrue(orSpecification.isSatisfiedBy(new Object()));

        orSpecification = new OrSpecification<Object>(falseSpec, falseSpec);
        assertFalse(orSpecification.isSatisfiedBy(new Object()));

    }
}
