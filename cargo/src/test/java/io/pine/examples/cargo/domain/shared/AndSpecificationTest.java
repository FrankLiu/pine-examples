package io.pine.examples.cargo.domain.shared;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Frank
 * @sinace 2018/8/13 0013.
 */
public class AndSpecificationTest {
    @Test
    public void testAndIsSatisifedBy() throws Exception {
        AlwaysTrueSpec trueSpec = new AlwaysTrueSpec();
        AlwaysFalseSpec falseSpec = new AlwaysFalseSpec();

        AndSpecification<Object> andSpecification = new AndSpecification<Object>(trueSpec, trueSpec);
        assertTrue(andSpecification.isSatisfiedBy(new Object()));

        andSpecification = new AndSpecification<Object>(falseSpec, trueSpec);
        assertFalse(andSpecification.isSatisfiedBy(new Object()));

        andSpecification = new AndSpecification<Object>(trueSpec, falseSpec);
        assertFalse(andSpecification.isSatisfiedBy(new Object()));

        andSpecification = new AndSpecification<Object>(falseSpec, falseSpec);
        assertFalse(andSpecification.isSatisfiedBy(new Object()));

    }
}
