package raf.kalendar.calendar_componentrs.import_export_impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;

import raf.kalendar.calendar_componentrs.exceptions.InvalidSheduleExcepiton;
import raf.kalendar.calendar_componentrs.import_export_spec.SheduleImportExportJSON;
import raf.kalendar.calendar_componentrs.model.Event;
import raf.kalendar.calendar_componentrs.model.Schedule;

public class SheduleImportExportJsonImplementation implements SheduleImportExportJSON {

	public Schedule importShedule(File file) throws IOException {
		Gson gson = new GsonBuilder().setPrettyPrinting().registerTypeAdapter(Event.class,new InterfaceAdapter<Event>()).create();
		BufferedReader reader = new BufferedReader(new FileReader(file));
		Schedule schedule = gson.fromJson(reader, Schedule.class);
		reader.close();
		return schedule;
	}

	public void exportShedule(File file, Schedule schedule) throws InvalidSheduleExcepiton, IOException {
		schedule.checkSchedule();
		Gson gson = new GsonBuilder().setPrettyPrinting().registerTypeAdapter(Event.class,new InterfaceAdapter<Event>()).create();
		Writer writer = new FileWriter(file);
		gson.toJson(schedule, writer);
		writer.flush();
	}

}
