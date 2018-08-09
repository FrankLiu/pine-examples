package io.pine.examples.cargo.domain.model.cargo;

import io.pine.examples.cargo.domain.shared.ValueObject;

/**
 * Represents the different transport statuses for a cargo.
 *
 * @author Frank
 * @sinace 2018/8/9 0009.
 */
public enum TransportStatus implements ValueObject<TransportStatus> {
    NOT_RECEIVED, IN_PORT, ONBOARD_CARRIER, CLAIMED, UNKNOWN;

    @Override
    public boolean sameValueAs(final TransportStatus other) {
        return this.equals(other);
    }
}