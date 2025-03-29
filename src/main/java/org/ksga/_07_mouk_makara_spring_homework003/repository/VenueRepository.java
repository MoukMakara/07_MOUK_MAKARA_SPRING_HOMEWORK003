package org.ksga._07_mouk_makara_spring_homework003.repository;

import org.apache.ibatis.annotations.*;
import org.ksga._07_mouk_makara_spring_homework003.model.Venue;
import org.ksga._07_mouk_makara_spring_homework003.model.request.VenueCreateRequest;
import org.ksga._07_mouk_makara_spring_homework003.model.request.VenueUpdateRequest;

import java.util.List;

@Mapper
public interface VenueRepository {
    // find all venues
    @Select("""
        SELECT * FROM venues
        offset #{limit} * (#{offset} - 1)
        limit #{limit}
    """)
    @Results(id = "venueMapper", value = {
            @Result(property = "venueId", column = "venue_id"),
            @Result(property = "venueName", column = "venue_name")
    })
    List<Venue> findAllVenues(Integer offset, Integer limit);

    // add new venue
    @Select(("""
        INSERT INTO venues(venue_name, location)
        VALUES (#{venue.venueName},#{venue.location})
        RETURNING *;
"""))
    @ResultMap("venueMapper")
    Venue createVenue(@Param("venue")VenueCreateRequest venueCreateRequest);

    // find venue by id
    @Select("SELECT * FROM venues WHERE venue_id = #{venueId}")
    @ResultMap("venueMapper")
    Venue findVenueById(Integer id);

    // delete venue by id
    @Select("DELETE FROM venues WHERE venue_id = #{venueId}")
    Venue deleteVenueById(Integer id);

    // update venue by id
    @Select("UPDATE venues SET venue_name = #{venue.venueName}, location = #{venue.location} WHERE venue_id = #{id} RETURNING *")
    @ResultMap("venueMapper")
    Venue updateVenueById(@Param("id") Integer id, @Param("venue") VenueUpdateRequest venueUpdateRequest);


}
