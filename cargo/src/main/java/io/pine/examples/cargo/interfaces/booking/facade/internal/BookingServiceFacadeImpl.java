package io.pine.examples.cargo.interfaces.booking.facade.internal;

import io.pine.examples.cargo.application.BookingService;
import io.pine.examples.cargo.domain.model.cargo.Cargo;
import io.pine.examples.cargo.domain.model.cargo.Itinerary;
import io.pine.examples.cargo.domain.model.cargo.TrackingId;
import io.pine.examples.cargo.domain.model.location.Location;
import io.pine.examples.cargo.domain.model.location.UnLocode;
import io.pine.examples.cargo.infrastructure.persistence.CargoRepository;
import io.pine.examples.cargo.infrastructure.persistence.LocationRepository;
import io.pine.examples.cargo.infrastructure.persistence.VoyageRepository;
import io.pine.examples.cargo.interfaces.booking.facade.BookingServiceFacade;
import io.pine.examples.cargo.interfaces.booking.facade.dto.CargoRoutingDTO;
import io.pine.examples.cargo.interfaces.booking.facade.dto.LocationDTO;
import io.pine.examples.cargo.interfaces.booking.facade.dto.RouteCandidateDTO;
import io.pine.examples.cargo.interfaces.booking.facade.internal.assembler.CargoRoutingDTOAssembler;
import io.pine.examples.cargo.interfaces.booking.facade.internal.assembler.ItineraryCandidateDTOAssembler;
import io.pine.examples.cargo.interfaces.booking.facade.internal.assembler.LocationDTOAssembler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * This implementation has additional support from the infrastructure, for exposing as an RMI
 * service and for keeping the OR-mapper unit-of-work open during DTO assembly,
 * analogous to the view rendering for web interfaces.
 *
 */
public class BookingServiceFacadeImpl implements BookingServiceFacade {

  private BookingService bookingService;
  private LocationRepository locationRepository;
  private CargoRepository cargoRepository;
  private VoyageRepository voyageRepository;
  private final Logger logger = LoggerFactory.getLogger(BookingServiceFacadeImpl.class);

  @Override
  public List<LocationDTO> listShippingLocations() {
    final List<Location> allLocations = locationRepository.findAll();
    final LocationDTOAssembler assembler = new LocationDTOAssembler();
    return assembler.toDTOList(allLocations);
  }

  @Override
  public String bookNewCargo(String origin, String destination, Date arrivalDeadline) {
    TrackingId trackingId = bookingService.bookNewCargo(
      new UnLocode(origin),
      new UnLocode(destination),
      arrivalDeadline
    );
    return trackingId.toString();
  }

  @Override
  public CargoRoutingDTO loadCargoForRouting(String trackingId) {
    final Cargo cargo = cargoRepository.findByTrackingId(new TrackingId(trackingId)).get();
    final CargoRoutingDTOAssembler assembler = new CargoRoutingDTOAssembler();
    return assembler.toDTO(cargo);
  }

  @Override
  public void assignCargoToRoute(String trackingIdStr, RouteCandidateDTO routeCandidateDTO) {
    final Itinerary itinerary = new ItineraryCandidateDTOAssembler().fromDTO(routeCandidateDTO, voyageRepository, locationRepository);
    final TrackingId trackingId = new TrackingId(trackingIdStr);

    bookingService.assignCargoToRoute(itinerary, trackingId);
  }

  @Override
  public void changeDestination(String trackingId, String destinationUnLocode) throws RemoteException {
    bookingService.changeDestination(new TrackingId(trackingId), new UnLocode(destinationUnLocode));
  }

  @Override
  public List<CargoRoutingDTO> listAllCargos() {
    final List<Cargo> cargoList = cargoRepository.findAll();
    final List<CargoRoutingDTO> dtoList = new ArrayList<CargoRoutingDTO>(cargoList.size());
    final CargoRoutingDTOAssembler assembler = new CargoRoutingDTOAssembler();
    for (Cargo cargo : cargoList) {
      dtoList.add(assembler.toDTO(cargo));
    }
    return dtoList;
  }

  @Override
  public List<RouteCandidateDTO> requestPossibleRoutesForCargo(String trackingId) throws RemoteException {
    final List<Itinerary> itineraries = bookingService.requestPossibleRoutesForCargo(new TrackingId(trackingId));

    final List<RouteCandidateDTO> routeCandidates = new ArrayList<RouteCandidateDTO>(itineraries.size());
    final ItineraryCandidateDTOAssembler dtoAssembler = new ItineraryCandidateDTOAssembler();
    for (Itinerary itinerary : itineraries) {
      routeCandidates.add(dtoAssembler.toDTO(itinerary));
    }

    return routeCandidates;
  }

  public void setBookingService(BookingService bookingService) {
    this.bookingService = bookingService;
  }

  public void setLocationRepository(LocationRepository locationRepository) {
    this.locationRepository = locationRepository;
  }

  public void setCargoRepository(CargoRepository cargoRepository) {
    this.cargoRepository = cargoRepository;
  }

  public void setVoyageRepository(VoyageRepository voyageRepository) {
    this.voyageRepository = voyageRepository;
  }
}
