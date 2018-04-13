package raf.kalendar.model;

import java.time.DayOfWeek;
import java.time.LocalTime;

/**
 * Predstavlja jedan dogadjaj u rasporedu. Dogadjaj je definisan 
 * predmetom, tipom, predavacem, grupama, danom, 
 * pocetnim i krajnjim vremenom i ucionicom u kojoj se odvija.
 * 
 * @author Tamara
 *
 */
public class Schedule {
	
	private String subject;
	
	private String type;
	
	private String teacher;
	
	private String groups;
	
	private DayOfWeek dayOfWeek;
	
	private LocalTime timeFrom;
	
	private LocalTime timeTo;
	
	private String location;
	
	
	public Schedule(String subject, String type, String teacher, String groups, DayOfWeek dayOfWeek, LocalTime timeFrom,
			LocalTime timeTo, String location) {
		super();
		this.subject = subject;
		this.type = type;
		this.teacher = teacher;
		this.groups = groups;
		this.dayOfWeek = dayOfWeek;
		this.timeFrom = timeFrom;
		this.timeTo = timeTo;
		this.location = location;
	}

	public String getSubject() {
		return subject;
	}
	
	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getTeacher() {
		return teacher;
	}
	
	public void setTeacher(String teacher) {
		this.teacher = teacher;
	}
	
	public String getGroups() {
		return groups;
	}
	
	public void setGroups(String groups) {
		this.groups = groups;
	}
	
	public DayOfWeek getDayOfWeek() {
		return dayOfWeek;
	}
	
	public void setDayOfWeek(DayOfWeek dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}
	
	public LocalTime getTimeFrom() {
		return timeFrom;
	}
	
	public LocalTime getTimeTo() {
		return timeTo;
	}
	
	public void setTimeFrom(LocalTime timeFrom) {
		this.timeFrom = timeFrom;
	}
	
	public void setTimeTo(LocalTime timeTo) {
		this.timeTo = timeTo;
	}
	
	public String getLocation() {
		return location;
	}
	
	public void setLocation(String location) {
		this.location = location;
	}
	
}
