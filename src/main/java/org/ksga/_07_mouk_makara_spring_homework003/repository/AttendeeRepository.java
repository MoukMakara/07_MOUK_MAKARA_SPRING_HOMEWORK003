package org.ksga._07_mouk_makara_spring_homework003.repository;

import org.apache.ibatis.annotations.*;
import org.ksga._07_mouk_makara_spring_homework003.model.Attendee;
import org.ksga._07_mouk_makara_spring_homework003.model.request.AttendeeCreateRequest;
import org.ksga._07_mouk_makara_spring_homework003.model.request.AttendeeUpdateRequest;

import java.util.List;

@Mapper
public interface AttendeeRepository {
    // find all Attendees
    @Select("""
        SELECT * FROM attendees
        offset #{limit} * (#{offset} - 1)
        limit #{limit}
    """)
    @Results(id = "attendeeMapper", value = {
            @Result(property = "attendeeId", column = "attendee_id"),
            @Result(property = "attendeeName", column = "attendee_name")
    })
    List<Attendee> findAllAttendees(Integer offset, Integer limit);

    // find attendee by id
    @Select("SELECT * FROM attendees WHERE attendee_id = #{attendeeId}")
    @ResultMap("attendeeMapper")
    Attendee findAttendeeById(Integer attendeeId);

    // create new attendee
    @Select("INSERT INTO attendees(attendee_name, email) VALUES (#{attendee.attendeeName}, #{attendee.email}) RETURNING *")
    @ResultMap("attendeeMapper")
    Attendee createAttendee(@Param("attendee") AttendeeCreateRequest attendeeCreateRequest);

    // delete attendee
    @Select("DELETE FROM attendees WHERE attendee_id = #{attendeeId} RETURNING *")
    Attendee deleteAttendeeById(Integer attendeeId);

    // update attendee
    @Select("UPDATE attendees SET attendee_name=#{attendee.attendeeName}, email=#{attendee.email} WHERE attendee_id=#{attendeeId}  RETURNING *")
    @ResultMap("attendeeMapper")
    Attendee updateAttendeeById(@Param("attendeeId") Integer attendeeId,@Param("attendee") AttendeeUpdateRequest attendeeUpdateRequest);
}
