package io.pine.examples.cargo.domain.shared;

/**
 * @author Frank
 * @sinace 2018/8/13 0013.
 */
public class AlwaysTrueSpec<T> extends AbstractSpecification<T> {
    @Override
    public boolean isSatisfiedBy(T alwaysTrueSpec) {
        return true;
    }
}
