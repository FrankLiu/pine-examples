package io.pine.examples.cargo.application;

import io.pine.examples.cargo.domain.model.cargo.Cargo;
import io.pine.examples.cargo.domain.model.cargo.TrackingId;
import io.pine.examples.cargo.domain.model.handling.*;
import io.pine.examples.cargo.domain.model.location.Location;
import io.pine.examples.cargo.domain.model.location.UnLocode;
import io.pine.examples.cargo.domain.model.voyage.Voyage;
import io.pine.examples.cargo.domain.model.voyage.VoyageNumber;
import io.pine.examples.cargo.infrastructure.persistence.CargoRepository;
import io.pine.examples.cargo.infrastructure.persistence.HandlingEventRepository;
import io.pine.examples.cargo.infrastructure.persistence.LocationRepository;
import io.pine.examples.cargo.infrastructure.persistence.VoyageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.Optional;

/**
 * Handling event service.
 */
@Service
public class HandlingEventService {

  private final Logger logger = LoggerFactory.getLogger(HandlingEventService.class);

  @Autowired
  private ApplicationEvents applicationEvents;

  @Autowired
  private CargoRepository cargoRepository;

  @Autowired
  private VoyageRepository voyageRepository;

  @Autowired
  private LocationRepository locationRepository;

  @Autowired
  private HandlingEventRepository handlingEventRepository;

  /**
   * Registers a handling event in the system, and notifies interested
   * parties that a cargo has been handled.
   *
   * @param completionTime when the event was completed
   * @param trackingId cargo tracking id
   * @param voyageNumber voyage number
   * @param unLocode UN locode for the location where the event occurred
   * @param type type of event
   * @throws io.pine.examples.cargo.domain.model.handling.CannotCreateHandlingEventException
   *  if a handling event that represents an actual event that's relevant to a cargo we're tracking
   *  can't be created from the parameters 
   */
  @Transactional(rollbackOn = CannotCreateHandlingEventException.class)
  public void registerHandlingEvent(Date completionTime,
                             TrackingId trackingId,
                             VoyageNumber voyageNumber,
                             UnLocode unLocode,
                             HandlingEvent.Type type) throws CannotCreateHandlingEventException {
    final Date registrationTime = new Date();
    /* Using a factory to create a HandlingEvent (aggregate). This is where
       it is determined wether the incoming data, the attempt, actually is capable
       of representing a real handling event. */
    final HandlingEvent event = createHandlingEvent(
            registrationTime, completionTime, trackingId, voyageNumber, unLocode, type
    );

    /* Store the new handling event, which updates the persistent
       state of the handling event aggregate (but not the cargo aggregate -
       that happens asynchronously!)
     */
    handlingEventRepository.save(event);

    /* Publish an event stating that a cargo has been handled. */
    applicationEvents.cargoWasHandled(event);

    logger.info("Registered handling event");
  }

  /**
   * @param registrationTime  time when this event was received by the system
   * @param completionTime    when the event was completed, for example finished loading
   * @param trackingId        cargo tracking id
   * @param voyageNumber      voyage number
   * @param unlocode          United Nations Location Code for the location of the event
   * @param type              type of event
   * @throws UnknownVoyageException   if there's no voyage with this number
   * @throws UnknownCargoException    if there's no cargo with this tracking id
   * @throws UnknownLocationException if there's no location with this UN Locode
   * @return A handling event.
   */
  public HandlingEvent createHandlingEvent(final Date registrationTime,
                                           final Date completionTime,
                                           final TrackingId trackingId,
                                           final VoyageNumber voyageNumber,
                                           final UnLocode unlocode,
                                           final HandlingEvent.Type type)
          throws CannotCreateHandlingEventException {
    final Optional<Cargo> cargo = cargoRepository.findByTrackingId(trackingId);
    if(!cargo.isPresent()) {
      throw new UnknownCargoException(trackingId);
    }
    final Optional<Voyage> voyage = voyageRepository.findByVoyageNumber(voyageNumber);
    if(!voyage.isPresent()) {
      throw new UnknownVoyageException(voyageNumber);
    }
    final Optional<Location> location = locationRepository.findByUnLocode(unlocode);
    if(!location.isPresent()) {
      throw new UnknownLocationException(unlocode);
    }

    try {
      if (!voyage.isPresent()) {
        return new HandlingEvent(cargo.get(), completionTime, registrationTime, type, location.get());
      } else {
        return new HandlingEvent(cargo.get(), completionTime, registrationTime, type, location.get(), voyage.get());
      }
    } catch (Exception e) {
      throw new CannotCreateHandlingEventException(e);
    }
  }
}
