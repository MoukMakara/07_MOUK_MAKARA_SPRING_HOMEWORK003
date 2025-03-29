package org.ksga._07_mouk_makara_spring_homework003.service;

import org.ksga._07_mouk_makara_spring_homework003.model.Event;
import org.ksga._07_mouk_makara_spring_homework003.model.request.EventCreateRequest;
import org.ksga._07_mouk_makara_spring_homework003.model.request.EventUpdateRequest;

import java.util.List;

public interface EventService {
    List<Event> findAllEvents(Integer offset, Integer limit);
    Event findEventById(Integer id);
    Event createEvent(EventCreateRequest eventCreateRequest);
    void deleteEventById(Integer id);
    Event updateEventById(Integer id, EventUpdateRequest eventUpdateRequest);
}
