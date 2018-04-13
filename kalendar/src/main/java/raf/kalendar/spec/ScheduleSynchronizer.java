package raf.kalendar.spec;

import java.util.ArrayList;

import raf.kalendar.model.Schedule;

/**
 * Obavlja sinhronizaciju rasporeda sa kalendarom.
 * 
 * @author Tamara
 *
 */
public interface ScheduleSynchronizer {
	
	/**
	 * Vrsi sinhronizaciju zadate liste dogadjaja iz rasporeda
	 * @param list dogadjaji koji treba da se sinhronizuju
	 * @throws Exception u slucaju neuspesne sinhronizacije
	 */
	public void synchronize(ArrayList<Schedule> list) throws Exception;
	
}
