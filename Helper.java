package biz.goeuro;

import java.io.IOException;

import javax.json.JsonArray;

import com.jcabi.http.request.JdkRequest;
import com.jcabi.http.response.JsonResponse;

public class Helper {

	static boolean cityExists(String[] args) {
		if (args.length > 0 && !args[0].isEmpty()) {
			return true;
		}
		return false;
	}

	static JsonArray getSuggestionsArrayFromLocationApi(String apiEndpoint) throws IOException {
		return new JdkRequest(apiEndpoint).fetch().as(JsonResponse.class).json().readArray();
	}
}
