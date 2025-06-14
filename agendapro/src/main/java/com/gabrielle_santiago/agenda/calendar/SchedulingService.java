package com.gabrielle_santiago.agenda.calendar;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventAttendee;
import com.google.api.services.calendar.model.EventDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.ZoneId;
import java.util.Collections;

@Service
public class SchedulingService {

    @Autowired
    private final GoogleCalendarAuthConfig googleCalendarAuthConfig;

    private static final String PROFESSIONAL_USER_ID = "PROFESSIONAL_USER";
    private static final String CALENDAR_ID = "primary";
    private static final ZoneId LOCAL_TIME_ZONE = ZoneId.of("America/Sao_Paulo");

    public SchedulingService(GoogleCalendarAuthConfig googleCalendarAuthConfig) {
        this.googleCalendarAuthConfig = googleCalendarAuthConfig;
    }

    public Event scheduleNewEvent(SchedulingRequestDTO requestDTO) throws IOException {
        Calendar service = googleCalendarAuthConfig.getCalendarService(PROFESSIONAL_USER_ID);

        Event event = new Event()
                .setSummary(requestDTO.getTitle());

        DateTime startGoogleDateTime = new DateTime(requestDTO.getStart().atZone(LOCAL_TIME_ZONE).toInstant().toEpochMilli());
        event.setStart(new EventDateTime()
                .setDateTime(startGoogleDateTime)
                .setTimeZone(LOCAL_TIME_ZONE.getId()));

        DateTime endGoogleDateTime = new DateTime(requestDTO.getEnd().atZone(LOCAL_TIME_ZONE).toInstant().toEpochMilli());
        event.setEnd(new EventDateTime()
                .setDateTime(endGoogleDateTime)
                .setTimeZone(LOCAL_TIME_ZONE.getId()));

        EventAttendee participant = new EventAttendee().setEmail(requestDTO.getEmailPatient());
        event.setAttendees(Collections.singletonList(participant));

        return service.events().insert(CALENDAR_ID, event).execute();
    }
}