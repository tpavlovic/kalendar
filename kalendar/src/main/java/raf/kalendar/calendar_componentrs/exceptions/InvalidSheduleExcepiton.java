package raf.kalendar.calendar_componentrs.exceptions;

import raf.kalendar.calendar_componentrs.model.Event;

/**
 * 
 * @author Tamara
 *
 */
public class InvalidSheduleExcepiton extends Exception {

	public InvalidSheduleExcepiton(Event event1, Event event2) {
		super("Invalid shedule." + event1.getTitle() + " and " + event2.getTitle() + " are overlaping");
	}

}
