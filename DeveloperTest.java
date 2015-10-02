import com.jcabi.http.request.JdkRequest;
import com.jcabi.http.response.JsonResponse;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import javax.json.JsonArray;
import javax.json.JsonObject;

public class DeveloperTest {

	private static final String apiEndpoint = "http://api.goeuro.com/api/v2/position/suggest/en/";
	
	//TODO replace throws with Try-Catch, to better inform the user if there are any problems in 
	// accessing the API
	public static void main(String[] args) throws IOException {
		if (args.length > 0 && !args[0].isEmpty()) {
			String city = args[0];
			DeveloperTest developerTest = new DeveloperTest();
			JsonArray suggestionsArray = new JdkRequest(apiEndpoint + city).fetch().as(JsonResponse.class).json()
					.readArray();
			developerTest.createCsvFileFromJson(suggestionsArray);
		} else {
			System.out.println("Please do not forget to specify the city!");
		}
	}
	
	//TODO replace throws with Try-Catch, to better inform the user if there are any problems in 
	// creating the CSV file
	private void createCsvFileFromJson(JsonArray suggestions) throws IOException {
		if (suggestions.isEmpty()) {
			System.out.println("No suggestions exist for given city!");
			return;
		}
		int size = suggestions.size();
		BufferedWriter writer = new BufferedWriter(new FileWriter("suggestions.csv"));
		writer.append("id,name,type,latitude,longitude");
		writer.append('\n');

		for (int i = 0; i < size; i++) {
			JsonObject suggestion = suggestions.getJsonObject(i);
			String id = String.valueOf(suggestion.getInt("_id"));
			String name = suggestion.getString("name");
			String type = suggestion.getString("type");
			JsonObject geoPosition = suggestion.getJsonObject("geo_position");
			String latitude = String.valueOf(geoPosition.get("latitude"));
			String longitude = String.valueOf(geoPosition.get("longitude"));
			String suggestionItem = String.format("%1s,%2s,%3s,%4s,%5s", id, name, type, latitude, longitude);
			writer.append(suggestionItem);
			writer.append('\n');
		}
		writer.flush();
		writer.close();
		System.out.println("Finished creating suggestions.csv");
	}
}
