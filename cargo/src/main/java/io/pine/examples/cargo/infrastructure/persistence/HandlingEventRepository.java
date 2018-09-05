package io.pine.examples.cargo.infrastructure.persistence;

import io.pine.examples.cargo.domain.model.cargo.TrackingId;
import io.pine.examples.cargo.domain.model.handling.HandlingEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.QueryHint;
import java.util.List;

/**
 * @author Frank
 * @sinace 2018/8/14 0014.
 */
@Repository
public interface HandlingEventRepository extends JpaRepository<HandlingEvent, Long> {
    @Query("from HandlingEvent where cargo.trackingId = :tid")
    List<HandlingEvent> findAllByTrackingId(@Param("tid") TrackingId trackingId);
}
