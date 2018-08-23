package io.pine.examples.cargo.domain.shared;

/**
 * @author Frank
 * @sinace 2018/8/13 0013.
 */
public class AlwaysFalseSpec<T> extends AbstractSpecification<T> {

    @Override
    public boolean isSatisfiedBy(T alwaysFalseSpec) {
        return false;
    }
}
