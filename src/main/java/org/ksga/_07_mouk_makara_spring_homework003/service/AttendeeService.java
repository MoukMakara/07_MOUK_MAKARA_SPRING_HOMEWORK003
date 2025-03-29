package org.ksga._07_mouk_makara_spring_homework003.service;

import org.ksga._07_mouk_makara_spring_homework003.model.Attendee;
import org.ksga._07_mouk_makara_spring_homework003.model.request.AttendeeCreateRequest;
import org.ksga._07_mouk_makara_spring_homework003.model.request.AttendeeUpdateRequest;

import java.util.List;

public interface AttendeeService {
    List<Attendee> findAllAttendees(Integer offset, Integer limit);
    Attendee findAttendeeById(Integer id);
    Attendee createAttendee(AttendeeCreateRequest attendeeCreateRequest);
    Attendee deleteAttendeeById(Integer id);
    Attendee updateAttendeeById(Integer id, AttendeeUpdateRequest attendeeUpdateRequest);
}
