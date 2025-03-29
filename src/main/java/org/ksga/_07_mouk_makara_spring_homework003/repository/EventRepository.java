package org.ksga._07_mouk_makara_spring_homework003.repository;

import org.apache.ibatis.annotations.*;
import org.ksga._07_mouk_makara_spring_homework003.model.Attendee;
import org.ksga._07_mouk_makara_spring_homework003.model.Event;
import org.ksga._07_mouk_makara_spring_homework003.model.Venue;
import org.ksga._07_mouk_makara_spring_homework003.model.request.EventCreateRequest;
import org.ksga._07_mouk_makara_spring_homework003.model.request.EventUpdateRequest;

import java.util.List;

@Mapper
public interface EventRepository {
    @Select("""
        SELECT * FROM  events
        LIMIT #{limit} OFFSET ((#{offset} - 1) * #{limit})
    """)
    @Results(id = "eventMapper", value = {
        @Result(property = "eventId", column = "event_id"),
            @Result(property = "eventName", column = "event_name"),
            @Result(property = "eventDate", column = "event_date"),
            @Result(property = "venue", column = "venue_id",
                one = @One(select = "org.ksga._07_mouk_makara_spring_homework003.repository.VenueRepository.findVenueById")
            ),
            @Result(property = "attendees", column = "event_id",
            many = @Many(select = "org.ksga._07_mouk_makara_spring_homework003.repository.EventRepository.findAttendeesByEventId")
            )

    })
    List<Event> findAllEvents(Integer offset, Integer limit);


    // find attendees list by event id
    @Select("""
        SELECT a.* FROM attendees a
        INNER JOIN event_attendee ea ON a.attendee_id = ea.attendee_id
        WHERE ea.event_id = #{eventId}
    """)
    @Results(id = "eventMapperAttendee", value = {
            @Result(property = "attendeeId", column = "attendee_id"),
            @Result(property = "attendeeName", column = "attendee_name")
    })
    List<Attendee> findAttendeesByEventId(@Param("eventId") Integer eventId);

    // find event by id
    @Select("""
        SELECT * FROM events
        WHERE event_id = #{eventId}
    """)
    @ResultMap("eventMapper")
    Event findEventById(Integer eventId);

    // create new event
    @Select("""
        INSERT INTO events(event_name, event_date, venue_id) 
        VALUES (#{event.eventName},#{event.eventDate},#{event.venue}) 
        RETURNING event_id
""")
    Integer createEvent(@Param("event")EventCreateRequest eventCreateRequest);

    // create event in table event_attendee
    @Insert("""
    INSERT INTO event_attendee(event_id, attendee_id)
    VALUES (#{eventId}, #{attendeeId})
    """)
    void createEventAttendee(Integer eventId, Integer attendeeId);

    // delete event by id
    @Select("DELETE FROM events WHERE event_id=#{eventId}")
    void deleteEventById(Integer eventId);

    // update event by id
    @Select("""
    UPDATE events SET event_name=#{event.eventName} ,event_date=#{event.eventDate}, venue_id=#{event.venue}
    WHERE event_id=#{eventId} RETURNING event_id
""")
    Event updateEventById(Integer eventId, @Param("event")EventUpdateRequest eventUpdateRequest);

    // update event_attendee
    @Insert("""
    INSERT INTO event_attendee(event_id, attendee_id)
    VALUES (#{eventId}, #{attendeeId})
""")
    void updateEventAttendee(Integer eventId, Integer attendeeId);

    // delete event_attendee
    @Select("""
    DELETE FROM event_attendee WHERE event_id=#{eventId}
""")
    void deleteEventAttendee(Integer eventId);
}
