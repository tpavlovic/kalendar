package raf.kalendar.calendar_componentrs.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import raf.kalendar.calendar_componentrs.exceptions.InvalidSheduleExcepiton;


public class Schedule {

	private String title;
	private String description;
	private List<Event> events = new ArrayList<Event>();
	
	public Schedule() {
		
	}

	public Schedule(String title, String description, List<Event> events) {
		super();
		this.title = title;
		this.description = description;
		this.events = events;
	}

	public void sortSchedule() {
		events.sort((Event event1, Event event2) -> event1.getStartActivity().compareTo(event2.getStartActivity()));
	}

	public void checkSchedule() throws InvalidSheduleExcepiton {
		for (int i = 0; i < events.size(); i++) {
			for (int j = i + 1; j < events.size(); j++) {
				if (!events.get(i).getClass().isAnnotationPresent(Background.class) && !events.get(i).getTitle().equals(events.get(j).getTitle())
						&& events.get(i).isOverlapWithOther(events.get(j))) {
					throw new InvalidSheduleExcepiton(events.get(i), events.get(j));
				}
			}
		}
	}
	
	@Override
	public boolean equals(Object other) {
		
		if(!(other instanceof Schedule)) {
			return false;
		}
		
		Schedule otherEvent=(Schedule)other;
		
		return this.title.equals(otherEvent.getTitle());	
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Event> getEvents() {
		return events;
	}

	public void setEvents(List<Event> events) {
		this.events = events;
	}

	public boolean add(Event arg0) {
		return events.add(arg0);
	}
	
	
	
	

}
