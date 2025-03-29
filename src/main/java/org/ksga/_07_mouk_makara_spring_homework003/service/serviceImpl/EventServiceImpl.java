package org.ksga._07_mouk_makara_spring_homework003.service.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.ksga._07_mouk_makara_spring_homework003.model.Attendee;
import org.ksga._07_mouk_makara_spring_homework003.model.Event;
import org.ksga._07_mouk_makara_spring_homework003.model.request.EventCreateRequest;
import org.ksga._07_mouk_makara_spring_homework003.model.request.EventUpdateRequest;
import org.ksga._07_mouk_makara_spring_homework003.repository.AttendeeRepository;
import org.ksga._07_mouk_makara_spring_homework003.repository.EventRepository;
import org.ksga._07_mouk_makara_spring_homework003.service.EventService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final AttendeeRepository attendeeRepository;


    @Override
    public List<Event> findAllEvents(Integer offset, Integer limit) {
        return eventRepository.findAllEvents(offset, limit);
    }

    @Override
    public Event findEventById(Integer id) {
        return eventRepository.findEventById(id);
    }

    @Override
    public Event createEvent(EventCreateRequest eventCreateRequest) {
        Integer eventId = eventRepository.createEvent(eventCreateRequest);

        for (Integer attendeeId : eventCreateRequest.getAttendees()){
            eventRepository.createEventAttendee(eventId, attendeeId);
        }
        return eventRepository.findEventById(eventId);
    }

    @Override
    public void deleteEventById(Integer id) {
        eventRepository.deleteEventById(id);
    }

    @Override
    public Event updateEventById(Integer id, EventUpdateRequest eventUpdateRequest) {
        // find event id
        Event event = eventRepository.findEventById(id);

        for (Integer attendeeId : eventUpdateRequest.getAttendees()){
            Attendee attendee = attendeeRepository.findAttendeeById(attendeeId);
            if (attendee == null){
                return null;
            }
        }
        // delete attendee
        eventRepository.deleteEventAttendee(event.getEventId());

        // update attendee
        for (Integer attendeeId : eventUpdateRequest.getAttendees()){
            eventRepository.updateEventAttendee(event.getEventId(), attendeeId);
        }

        // update new attendee
        event.setEventName(eventUpdateRequest.getEventName());
        event.setEventDate(eventUpdateRequest.getEventDate());


        // update venue
        eventRepository.updateEventById(event.getEventId(), eventUpdateRequest);

        return eventRepository.findEventById(event.getEventId());
    }
}
