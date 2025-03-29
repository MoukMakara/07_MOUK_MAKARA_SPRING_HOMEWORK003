package org.ksga._07_mouk_makara_spring_homework003.service.serviceImpl;

import org.ksga._07_mouk_makara_spring_homework003.model.Venue;
import org.ksga._07_mouk_makara_spring_homework003.model.request.VenueCreateRequest;
import org.ksga._07_mouk_makara_spring_homework003.model.request.VenueUpdateRequest;
import org.ksga._07_mouk_makara_spring_homework003.repository.VenueRepository;
import org.ksga._07_mouk_makara_spring_homework003.service.VenueService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VenueServiceImpl implements VenueService {
    private final VenueRepository venueRepository;

    public VenueServiceImpl(VenueRepository venueRepository) {
        this.venueRepository = venueRepository;
    }


    @Override
    public List<Venue> findAllVenues(Integer offset, Integer limit) {
        return venueRepository.findAllVenues(offset, limit);
    }

    @Override
    public Venue createVenue(VenueCreateRequest venueCreateRequest) {
        return venueRepository.createVenue(venueCreateRequest);
    }

    @Override
    public Venue findVenueById(Integer id) {
        return venueRepository.findVenueById(id);
    }


    @Override
    public Venue deleteVenueById(Integer id) {
        return venueRepository.deleteVenueById(id);
    }

    @Override
    public Venue updateVenueById(Integer id, VenueUpdateRequest venueUpdateRequest) {
        return venueRepository.updateVenueById(id, venueUpdateRequest);
    }

}
