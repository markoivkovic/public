package biz.goeuro;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import javax.json.JsonArray;
import javax.json.JsonObject;

public class CsvWriter {

	void write2File(JsonArray suggestions) throws IOException {
		int size = suggestions.size();
		BufferedWriter writer = new BufferedWriter(new FileWriter("suggestions.csv"));
		writer.append("id,name,type,latitude,longitude");
		writer.append('\n');
		String id, name, type, latitude, longitude;
		JsonObject suggestion;

		for (int i = 0; i < size; i++) {
			suggestion = suggestions.getJsonObject(i);
			id = String.valueOf(suggestion.getInt("_id"));
			name = suggestion.getString("name");
			type = suggestion.getString("type");
			JsonObject geoPosition = suggestion.getJsonObject("geo_position");
			latitude = String.valueOf(geoPosition.get("latitude"));
			longitude = String.valueOf(geoPosition.get("longitude"));
			writer.append(String.format("%1s,%2s,%3s,%4s,%5s", id, name, type, latitude, longitude));
			writer.append('\n');
		}
		writer.flush();
		writer.close();
	}
}
