package org.ksga._07_mouk_makara_spring_homework003.service.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.ksga._07_mouk_makara_spring_homework003.model.Attendee;
import org.ksga._07_mouk_makara_spring_homework003.model.request.AttendeeCreateRequest;
import org.ksga._07_mouk_makara_spring_homework003.model.request.AttendeeUpdateRequest;
import org.ksga._07_mouk_makara_spring_homework003.repository.AttendeeRepository;
import org.ksga._07_mouk_makara_spring_homework003.service.AttendeeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AttendeeServiceImpl implements AttendeeService {
    private final AttendeeRepository attendeeRepository;


    @Override
    public List<Attendee> findAllAttendees(Integer offset, Integer limit) {
        return attendeeRepository.findAllAttendees(offset, limit);
    }

    @Override
    public Attendee findAttendeeById(Integer id) {
        return attendeeRepository.findAttendeeById(id);
    }

    @Override
    public Attendee createAttendee(AttendeeCreateRequest attendeeCreateRequest) {
        return attendeeRepository.createAttendee(attendeeCreateRequest);
    }

    @Override
    public Attendee deleteAttendeeById(Integer id) {
        return attendeeRepository.deleteAttendeeById(id);
    }

    @Override
    public Attendee updateAttendeeById(Integer id, AttendeeUpdateRequest attendeeUpdateRequest) {
        return attendeeRepository.updateAttendeeById(id, attendeeUpdateRequest);
    }
}
