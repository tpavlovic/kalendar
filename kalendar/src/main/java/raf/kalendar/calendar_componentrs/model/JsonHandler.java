package raf.kalendar.calendar_componentrs.model;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class JsonHandler {

	private List<String> types = new ArrayList<String>();
	private Object[][] data = new Object[100][100];

	public List<String> getObjectFromFile(String path) throws org.json.simple.parser.ParseException {
		try {

			JSONParser parser = new JSONParser();
			JSONObject jsonObject;
			jsonObject = (JSONObject) parser.parse(new FileReader(path));

			JSONArray temp_events = (JSONArray) jsonObject.get("events");

			Iterator<?> iterator = temp_events.iterator();
			while (iterator.hasNext()) {
				JSONObject jsonObj = (JSONObject) iterator.next();
				for (Object key : jsonObj.keySet()) {
					if (!types.contains((String) key))
						types.add((String) key);
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return types;
	}

	public Object[][] getDataFromFile(String path) throws org.json.simple.parser.ParseException {
		int count = 0;
		try {

			JSONParser parser = new JSONParser();
			JSONObject jsonObject;
			jsonObject = (JSONObject) parser.parse(new FileReader(path));

			JSONArray temp_events = (JSONArray) jsonObject.get("events");
			Iterator<?> iterator = temp_events.iterator();
			while (iterator.hasNext()) {
				JSONObject jsonObj = (JSONObject) iterator.next();
				for (int i = 0; i < types.size(); i++) {
					data[count][i] = jsonObj.get(types.get(i));
				}
				count++;
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return data;
	}

	public List<String> getTypes() {
		return types;
	}

}
