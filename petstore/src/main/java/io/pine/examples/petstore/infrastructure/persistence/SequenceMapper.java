package io.pine.examples.petstore.infrastructure.persistence;

import io.pine.examples.petstore.domain.Sequence;
import org.springframework.stereotype.Repository;

/**
 * @author frank.liu
 * @since 2019/2/11 0011.
 */
@Repository
public interface SequenceMapper {

    /**
     * 得到序列
     */
    Sequence getSequence(Sequence sequence);

    /**
     * 更新序列
     */
    boolean updateSequence(Sequence sequence);
}
