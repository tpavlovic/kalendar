package raf.kalendar.impl;

import java.io.FileReader;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import raf.kalendar.model.Schedule;
import raf.kalendar.spec.ScheduleLoader;

/**
 * Ucitavanje rasporeda iz json fajla
 * 
 * @author Tamara
 *
 */
public class ScheduleLoaderJsonImpl implements ScheduleLoader {

	/**
	 * Ucitava niz iz zadatog json fajla
	 * 
	 * @param filePath putanja do fajla koji sadrzi raspored
	 * @return niz Schedule objekata
	 * @throws Exception u slucaju da je ucitavanje nije uspesno 
	 */
	@Override
	public ArrayList<Schedule> load(String filePath) throws Exception {
		ArrayList<Schedule> list = new ArrayList<>();
		
		JSONParser parser = new JSONParser();
		FileReader reader = new FileReader(filePath);
		
		JSONObject json = (JSONObject) parser.parse(reader);
		JSONArray events = (JSONArray) json.get("events");

		Iterator<JSONObject> iterator = events.iterator();
		while (iterator.hasNext()) {
			JSONObject scheduleJson = iterator.next();
			
			String subject = scheduleJson.get("Predmet").toString();
			String type = scheduleJson.get("Tip").toString();
			String teacher = scheduleJson.get("Nastavnik").toString();
			String groups = scheduleJson.get("Grupe").toString();
			String day = scheduleJson.get("Dan").toString();
			String timeInterval = scheduleJson.get("Termin").toString();
			String location = scheduleJson.get("Ucionica").toString();
			
			if(subject == null || type == null || teacher == null || groups == null || day == null || timeInterval == null || location == null) {
				reader.close();
				throw new Exception("Invalid json file");
			}
			
			String[] time = timeInterval.split("-");
			LocalTime timeFrom = getLocalTimeFromString(time[0]);
			LocalTime timeTo = getLocalTimeFromString(time[1]);
			DayOfWeek dayOfWeek = getDayOfWeekFromString(day);
			
			Schedule schedule = new Schedule(subject, type, teacher, groups, dayOfWeek, timeFrom, timeTo, location);
			list.add(schedule);
		}
		
		return list;
	}
	
	/**
	 * Pravi LocalTime objekat iz string koji sadrzi vreme u formatu SS:mm ili SS (S - sat, m - minut)
	 * @param str vreme
	 * @return LocalTime
	 */
	private LocalTime getLocalTimeFromString(String str) {
		String[] split = str.split(":");
		if(split.length == 1) {
			return LocalTime.of(Integer.parseInt(str), 0);
		}
		else {
			return LocalTime.of(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
		}
	}
	
	/**
	 * Vraca odgovarajuci DayOfWeek za zadati string
	 * @param day moze sadrzati vrednosti PON, UTO, SRE, ČET, PET, SUB ili NET
	 * @return DayOfWeek
	 */
	private DayOfWeek getDayOfWeekFromString(String day){
		final DayOfWeek dayOfWeek;
		switch (day) {
			case "PON":
				dayOfWeek = DayOfWeek.MONDAY;
				break;
			case "UTO":
				dayOfWeek = DayOfWeek.TUESDAY;
				break;
			case "SRE":
				dayOfWeek = DayOfWeek.WEDNESDAY;
				break;
			case "ČET":
				dayOfWeek = DayOfWeek.THURSDAY;
				break;
			case "PET":
				dayOfWeek = DayOfWeek.FRIDAY;
				break;
			case "SUB":
				dayOfWeek = DayOfWeek.SATURDAY;
				break;
			case "NED":
				dayOfWeek = DayOfWeek.SUNDAY;
				break;
			default:
				dayOfWeek = null;
				break;
		}
		
		return dayOfWeek;
	}

}
