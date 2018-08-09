package io.pine.examples.cargo.domain.model.cargo;

import io.pine.examples.cargo.domain.shared.ValueObject;

/**
 * @author Administrator
 * @sinace 2018/8/9 0009.
 */
public enum RoutingStatus implements ValueObject<RoutingStatus> {
    NOT_ROUTED, ROUTED, MISROUTED;

    @Override
    public boolean sameValueAs(final RoutingStatus other) {
        return this.equals(other);
    }

}