package io.pine.examples.cargo.domain.shared;

/**
 * @author Frank
 * @sinace 2018/8/2 0002.
 */
public interface Entity<T> {

    /**
     * Entities compare by identity, not by attributes.
     *
     * @param other The other entity.
     * @return true if the identities are the same, regardles of other attributes.
     */
    boolean sameIdentityAs(T other);

}
