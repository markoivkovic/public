package biz.goeuro;

import java.io.IOException;
import javax.json.JsonArray;

public class GoEuroTest {

	private static final String apiEndpoint = "http://api.goeuro.com/api/v2/position/suggest/en/";

	public static void main(String[] args) {
		if (Helper.cityExists(args)) {
			GoEuroTest goEuroTest = new GoEuroTest();
			goEuroTest.run(args[0]);
		} else {
			System.out.println("Please do not forget to specify the city!");
		}
	}

	private void run(String city) {
		try {
			createCsvFileFromJson(Helper.getSuggestionsArrayFromLocationApi(apiEndpoint + city));
		} catch (IOException e) {
			System.out.println("Error accessing Location JSON API!");
		}
	}

	private void createCsvFileFromJson(JsonArray suggestions) throws IOException {
		if (suggestions == null || suggestions.isEmpty()) {
			System.out.println("No suggestions exist for given city!");
			return;
		}
		
		CsvWriter csvWriter = new CsvWriter();
		try {
			csvWriter.write2File(suggestions);
			System.out.println("Finished creating suggestions.csv!");
		} catch (IOException e) {
			System.out.println("Error creating or writing file!");
		}

		
	}
}
