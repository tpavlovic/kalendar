package raf.kalendar.spec;

import java.util.ArrayList;

import raf.kalendar.model.Schedule;


/**
 * 
 * Zaduzen za ucitavanje rasporeda
 * JSON fajl treba da sadzi niz events koji sadrze kljuceve: Predmet,Tip, Nastavnik,Grupe,Dan,TErmin
 * 
 * @author Tamara
 *
 */
public interface ScheduleLoader {
	
	
	/**
	 * Ucitava niz iz zadatog fajla
	 * 
	 * @param filePath putanja do fajla koji sadrzi raspored
	 * @return niz Schedule objekata
	 * @throws Exception u slucaju da je ucitavanje nije uspesno 
	 */
	public ArrayList<Schedule> load(String filePath) throws Exception;
	
}
