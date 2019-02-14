package io.pine.examples.cargo.interfaces.booking.facade.internal.assembler;

import io.pine.examples.cargo.domain.model.location.Location;
import io.pine.examples.cargo.interfaces.booking.facade.dto.LocationDTO;

import java.util.ArrayList;
import java.util.List;

public class LocationDTOAssembler {

  public LocationDTO toDTO(Location location) {
    return new LocationDTO(location.getUnLocode().toString(), location.getName());
  }

  public List<LocationDTO> toDTOList(List<Location> allLocations) {
    final List<LocationDTO> dtoList = new ArrayList<LocationDTO>(allLocations.size());
    for (Location location : allLocations) {
      dtoList.add(toDTO(location));
    }
    return dtoList;
  }
}
