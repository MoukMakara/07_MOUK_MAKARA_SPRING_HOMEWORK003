CREATE DATABASE db_spring_rest_api_homework003;

CREATE TABLE venues (
    venue_id SERIAL PRIMARY KEY ,
    venue_name VARCHAR(50) NOT NULL ,
    location TEXT
);

CREATE TABLE events (
    event_id SERIAL PRIMARY KEY ,
    event_name VARCHAR(250) NOT NULL ,
    event_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    venue_id INT REFERENCES venues(venue_id) ON DELETE CASCADE NOT NULL
);

CREATE TABLE attendees (
    attendee_id SERIAL PRIMARY KEY ,
    attendee_name VARCHAR(50) NOT NULL ,
    email VARCHAR(250) NOT NULL
);

CREATE TABLE event_attendee (
    id SERIAL PRIMARY KEY ,
    event_id INT REFERENCES events(event_id) ON DELETE CASCADE NOT NULL,
    attendee_id INT REFERENCES attendees(attendee_id) ON DELETE CASCADE NOT NULL
);