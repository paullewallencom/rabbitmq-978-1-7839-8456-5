package org.packt.rabbitmq.book.samples.chapter3;

import java.util.Scanner;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;

public class ComponentFinder {

	private final static Logger LOGGER = LoggerFactory
			.getLogger(ComponentFinder.class);

	private static final String API_ROOT = "http://localhost:15672/api";

	public static void main(String[] args) {

		Scanner scanner = null;
		try {
			scanner = new Scanner(System.in);
			System.out
					.println("Enter component type in plural form (e.g. queues, exchanges) ");
			String type = scanner.nextLine();
			System.out.println("Enter vhost (leave empty for default vhost) ");
			String vhost = scanner.nextLine();
			System.out
					.println("Enter name pattern (leave empty for match-all pattern)");
			String pattern = scanner.nextLine();

			Client client = Client.create();
			String path;
			if (vhost.trim().isEmpty()) {
				path = API_ROOT + "/" + type + "?columns=name";
			} else {
				path = API_ROOT + "/" + type + "/" + vhost + "?columns=name";
			}

			WebResource resource = client.resource(path);
			resource.header("Content-Type", "application/json;charset=UTF-8");
			resource.addFilter(new HTTPBasicAuthFilter("guest", "guest"
					.getBytes()));
			String result = resource.get(String.class);
			JSONArray jsonResult = new JSONArray(result);
			LOGGER.debug("Result: \n" + jsonResult.toString(4));

			filterResult(jsonResult, pattern);
		} finally {
			if (scanner != null) {
				scanner.close();
			}
		}
	}

	private static void filterResult(JSONArray jsonResult, String pattern) {
		// filter the result based on the pattern
		for (int index = 0; index < jsonResult.length(); index++) {
			JSONObject componentInfo = (JSONObject) jsonResult.get(index);
			String componentName = (String) componentInfo.get("name");
			if (Pattern.matches(pattern, componentName)) {
				LOGGER.info("Matched component: " + componentName);
				// do something else with component
			}
		}
	}
}
