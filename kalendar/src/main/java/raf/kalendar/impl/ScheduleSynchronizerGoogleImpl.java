package raf.kalendar.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;

import raf.kalendar.model.Schedule;
import raf.kalendar.spec.ScheduleSynchronizer;

/**
 * Vrsi sinhronizaciju sa Google kalendarom
 * 
 * @author Tamara
 *
 */
public class ScheduleSynchronizerGoogleImpl implements ScheduleSynchronizer {
	
	/** Application name. */
    private static final String APPLICATION_NAME =
        "Google Calendar API Java Quickstart";

    /** Directory to store user credentials for this application. */
    private static final java.io.File DATA_STORE_DIR = new java.io.File(
        System.getProperty("user.home"), ".credentials/calendar-java-quickstart");

    /** Global instance of the {@link FileDataStoreFactory}. */
    private static FileDataStoreFactory DATA_STORE_FACTORY;

    /** Global instance of the JSON factory. */
    private static final JsonFactory JSON_FACTORY =
        JacksonFactory.getDefaultInstance();

    /** Global instance of the HTTP transport. */
    private static HttpTransport HTTP_TRANSPORT;

    /** Global instance of the scopes required by this quickstart.
     *
     * If modifying these scopes, delete your previously saved credentials
     * at ~/.credentials/calendar-java-quickstart
     */
    private static final List<String> SCOPES =
        Arrays.asList(CalendarScopes.CALENDAR);

    static {
        try {
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);
        } catch (Throwable t) {
            t.printStackTrace();
            System.exit(1);
        }
    }
    
    /**
     * Creates an authorized Credential object.
     * @return an authorized Credential object.
     * @throws IOException
     */
    private static Credential authorize() throws IOException {
        // Load client secrets.
        InputStream in =
            ScheduleSynchronizerGoogleImpl.class.getResourceAsStream("/client_secret.json");
        GoogleClientSecrets clientSecrets =
            GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow =
                new GoogleAuthorizationCodeFlow.Builder(
                        HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(DATA_STORE_FACTORY)
                .setAccessType("offline")
                .build();
        Credential credential = new AuthorizationCodeInstalledApp(
            flow, new LocalServerReceiver()).authorize("user");
        System.out.println(
                "Credentials saved to " + DATA_STORE_DIR.getAbsolutePath());
        return credential;
    }

    /**
     * Build and return an authorized Calendar client service.
     * @return an authorized Calendar client service
     * @throws IOException
     */
    private static com.google.api.services.calendar.Calendar
        getCalendarService() throws IOException {
        Credential credential = authorize();
        return new com.google.api.services.calendar.Calendar.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }
    
    private Event getEventFromSchedule(Schedule schedule){
		final Event event = new Event();
		DateTimeFormatter formatter = DateTimeFormatter .ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").withZone(ZoneId.of("UTC"));
		
		event.setSummary(schedule.getSubject());
		event.setLocation(event.getLocation());
		
		ZoneId zoneId = ZoneId.of("UTC");
		LocalDateTime nextDayDate = LocalDateTime.now(zoneId).with(TemporalAdjusters.next(schedule.getDayOfWeek()));
		
		LocalDateTime dateFrom = LocalDateTime.of(nextDayDate.getYear(), nextDayDate.getMonthValue(), nextDayDate.getDayOfMonth(), schedule.getTimeFrom().getHour(), schedule.getTimeFrom().getMinute());
		LocalDateTime dateTo = LocalDateTime.of(nextDayDate.getYear(), nextDayDate.getMonthValue(), nextDayDate.getDayOfMonth(), schedule.getTimeTo().getHour(), schedule.getTimeTo().getMinute());
		
		DateTime startDateTime = DateTime.parseRfc3339(dateFrom.format(formatter));
		DateTime endDateTime = DateTime.parseRfc3339(dateTo.format(formatter));
		
		EventDateTime start = new EventDateTime().setDateTime(startDateTime).setTimeZone("Europe/Belgrade");
		EventDateTime end = new EventDateTime().setDateTime(endDateTime).setTimeZone("Europe/Belgrade");
	
		event.setStart(start);
		event.setEnd(end);
		event.setDescription(schedule.getSubject() + ", " + schedule.getTeacher() + "(" + schedule.getType() + ") - " + schedule.getGroups());
		event.setRecurrence(Arrays.asList("RRULE:FREQ=WEEKLY;COUNT=1"));
		
		return event;
	}

	@Override
	public void synchronize(ArrayList<Schedule> list) throws Exception {
		com.google.api.services.calendar.Calendar service = getCalendarService();
		String calendarId = "primary";
		
		for(Schedule schedule : list) {
			Event event = getEventFromSchedule(schedule);
			event = service.events().insert(calendarId, event).execute();
			System.out.printf("Event created: %s\n", event.getHtmlLink());
		}
	}
	
}
