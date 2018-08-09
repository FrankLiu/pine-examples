package io.pine.examples.cargo.domain.shared;

import java.io.Serializable;

/**
 * @author Frank
 * @sinace 2018/8/2 0002.
 */
public interface ValueObject<T> extends Serializable {
    /**
     * Value objects compare by the values of their attributes, they don't have an identity.
     *
     * @param other The other value object.
     * @return <code>true</code> if the given value object's and this value object's attributes are the same.
     */
    boolean sameValueAs(T other);
}
