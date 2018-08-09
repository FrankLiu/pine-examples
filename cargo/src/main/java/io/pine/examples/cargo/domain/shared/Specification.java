package io.pine.examples.cargo.domain.shared;

/**
 * Specificaiton interface.
 * <p/>
 * Use {@link io.pine.examples.cargo.domain.shared.AbstractSpecification} as base for creating specifications, and
 * @author Administrator
 * @sinace 2018/8/2 0002.
 */

public interface Specification<T> {

    /**
     * Check if {@code t} is satisfied by the specification.
     *
     * @param t Object to test.
     * @return {@code true} if {@code t} satisfies the specification.
     */
    boolean isSatisfiedBy(T t);

    /**
     * Create a new specification that is the AND operation of {@code this} specification and another specification.
     * @param specification Specification to AND.
     * @return A new specification.
     */
    Specification<T> and(Specification<T> specification);

    /**
     * Create a new specification that is the OR operation of {@code this} specification and another specification.
     * @param specification Specification to OR.
     * @return A new specification.
     */
    Specification<T> or(Specification<T> specification);

    /**
     * Create a new specification that is the NOT operation of {@code this} specification.
     * @param specification Specification to NOT.
     * @return A new specification.
     */
    Specification<T> not(Specification<T> specification);
}
