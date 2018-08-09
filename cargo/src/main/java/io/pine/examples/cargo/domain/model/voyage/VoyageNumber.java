package io.pine.examples.cargo.domain.model.voyage;

import io.pine.examples.cargo.domain.shared.ValueObject;
import org.springframework.util.Assert;

/**
 * @author Frank
 * @sinace 2018/8/9 0009.
 */
public class VoyageNumber implements ValueObject<VoyageNumber> {

    private String number;

    public VoyageNumber() {
        // Needed by Hibernate
    }

    public VoyageNumber(String number) {
        Assert.notNull(number, "Number is required!");

        this.number = number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null) { return false; }
        if (!(o instanceof VoyageNumber)) { return false; }

        final VoyageNumber other = (VoyageNumber) o;

        return sameValueAs(other);
    }

    @Override
    public int hashCode() {
        return number.hashCode();
    }

    @Override
    public boolean sameValueAs(VoyageNumber other) {
        return other != null && this.number.equals(other.number);
    }

    @Override
    public String toString() {
        return number;
    }

    public String idString() {
        return number;
    }
}
