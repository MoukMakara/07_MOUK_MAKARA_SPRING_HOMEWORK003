package org.ksga._07_mouk_makara_spring_homework003.service;

import org.ksga._07_mouk_makara_spring_homework003.model.Venue;
import org.ksga._07_mouk_makara_spring_homework003.model.request.VenueCreateRequest;
import org.ksga._07_mouk_makara_spring_homework003.model.request.VenueUpdateRequest;

import java.util.List;

public interface VenueService {
    List<Venue> findAllVenues(Integer offset, Integer limit);
    Venue createVenue(VenueCreateRequest venueCreateRequest);
    Venue findVenueById(Integer id);
    Venue deleteVenueById(Integer id);
    Venue updateVenueById(Integer id, VenueUpdateRequest venueUpdateRequest);
}
