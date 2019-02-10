package io.pine.examples.cargo.application;

import io.pine.examples.cargo.domain.model.cargo.Cargo;
import io.pine.examples.cargo.domain.model.cargo.Itinerary;
import io.pine.examples.cargo.domain.model.cargo.RouteSpecification;
import io.pine.examples.cargo.domain.model.cargo.TrackingId;
import io.pine.examples.cargo.domain.model.location.Location;
import io.pine.examples.cargo.domain.model.location.UnLocode;
import io.pine.examples.cargo.domain.service.RoutingService;
import io.pine.examples.cargo.infrastructure.persistence.CargoRepository;
import io.pine.examples.cargo.infrastructure.persistence.LocationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Cargo booking service.
 */
@Service
@Transactional(rollbackOn = Exception.class)
public class BookingService {

  private final Logger logger = LoggerFactory.getLogger(BookingService.class);

  @Autowired
  private CargoRepository cargoRepository;

  @Autowired
  private LocationRepository locationRepository;

  @Autowired
  private RoutingService routingService;

  public CargoRepository getCargoRepository() {
    return cargoRepository;
  }

  public void setCargoRepository(CargoRepository cargoRepository) {
    this.cargoRepository = cargoRepository;
  }

  public LocationRepository getLocationRepository() {
    return locationRepository;
  }

  public void setLocationRepository(LocationRepository locationRepository) {
    this.locationRepository = locationRepository;
  }

  public RoutingService getRoutingService() {
    return routingService;
  }

  public void setRoutingService(RoutingService routingService) {
    this.routingService = routingService;
  }

  /**
   * Registers a new cargo in the tracking system, not yet routed.
   *
   * @param originUnLocode      cargo origin unlocode
   * @param destinationUnLocode cargo destination unlocode
   * @param arrivalDeadline arrival deadline
   * @return Cargo tracking id
   */
  @Transactional
  public TrackingId bookNewCargo(final UnLocode originUnLocode,
                          final UnLocode destinationUnLocode,
                          final Date arrivalDeadline) {
    final TrackingId trackingId = TrackingId.nextTrackingId();
    final Optional<Location> origin = this.locationRepository.findByUnLocode(originUnLocode);
    final Optional<Location> destination = this.locationRepository.findByUnLocode(destinationUnLocode);
    final RouteSpecification routeSpecification = new RouteSpecification(origin.get(), destination.get(), arrivalDeadline);

    final Cargo cargo = new Cargo(trackingId, routeSpecification);
    this.cargoRepository.save(cargo);
    logger.info("Booked new cargo with tracking id " + cargo.getTrackingId().toString());

    return cargo.getTrackingId();
  }

  /**
   * Requests a list of itineraries describing possible routes for this cargo.
   *
   * @param trackingId cargo tracking id
   * @return A list of possible itineraries for this cargo
   */
  @Transactional
  public List<Itinerary> requestPossibleRoutesForCargo(final TrackingId trackingId) {
    final Optional<Cargo> cargo = this.cargoRepository.findByTrackingId(trackingId);

    if (!cargo.isPresent()) {
      return Collections.emptyList();
    }

    return routingService.fetchRoutesForSpecification(cargo.get().getRouteSpecification());
  }

  /**
   * @param itinerary itinerary describing the selected route
   * @param trackingId cargo tracking id
   */
  @Transactional
  public void assignCargoToRoute(final Itinerary itinerary, final TrackingId trackingId) {
    final Optional<Cargo> cargo = this.cargoRepository.findByTrackingId(trackingId);
    if(!cargo.isPresent()) {
      throw new IllegalArgumentException("Can't assign itinerary to non-existing cargo " + trackingId);
    }

    cargo.get().assignToRoute(itinerary);
    this.cargoRepository.save(cargo.get());
    this.logger.info("Assigned cargo " + trackingId + " to new route");
  }

  /**
   * Changes the destination of a cargo.
   *
   * @param trackingId cargo tracking id
   * @param unLocode UN locode of new destination
   */
  @Transactional
  public void changeDestination(TrackingId trackingId, UnLocode unLocode) {
    final Optional<Cargo> cargo = this.cargoRepository.findByTrackingId(trackingId);
    if(!cargo.isPresent()) {
      throw new IllegalArgumentException("Can't assign itinerary to non-existing cargo " + trackingId);
    }

    final Optional<Location> newDestination = this.locationRepository.findByUnLocode(unLocode);
    final RouteSpecification routeSpecification = new RouteSpecification(
            cargo.get().getOrigin(), newDestination.get(), cargo.get().getRouteSpecification().getArrivalDeadline()
    );
    cargo.get().specifyNewRoute(routeSpecification);

    cargoRepository.save(cargo.get());
    logger.info("Changed destination for cargo " + trackingId + " to " + routeSpecification.getDestination());
  }

}
