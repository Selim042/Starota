package us.myles_selim.starota.webserver.webhooks;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class HttpHandlerWebhooks implements HttpHandler {

	private static final Gson GSON;
	private static final JsonParser PARSER = new JsonParser();

	static {
		GsonBuilder builder = new GsonBuilder();

		builder.registerTypeAdapter(WebhookClass.class, new JsonDeserializer<WebhookClass<?>>() {

			@Override
			public WebhookClass<?> deserialize(JsonElement json, Type typeOfT,
					JsonDeserializationContext context) throws JsonParseException {
				if (!json.isJsonObject())
					throw new JsonParseException(
							"expected a JsonObject, got " + json.getClass().getSimpleName());
				JsonObject jobj = json.getAsJsonObject();
				if (!jobj.has("data") || !jobj.get("data").isJsonObject())
					throw new JsonParseException("json must contain a JsonObject with key data");
				WebhookClass<?> wClass = new WebhookClass<>();
				wClass.type = jobj.get("type").getAsString();
				wClass.data = context.deserialize(jobj.get("data").getAsJsonObject(),
						WebhookData.getDataClassForType(wClass.type));
				return wClass;
			}
		});

		GSON = builder.create();
	}

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		try {
			InputStreamReader requestBody = new InputStreamReader(exchange.getRequestBody());
			// String input = "";
			// while (requestBody.available() != 0)
			// input += (char) requestBody.read();
			// System.out.println(input);

			WebhookClass<?>[] data = GSON.fromJson(PARSER.parse(requestBody), WebhookClass[].class);
			for (WebhookClass<?> hookC : data) {
				System.out.println("back out: " + GSON.toJson(hookC));
			}

			Headers requestHeaders = exchange.getRequestHeaders();

			String response = "";

			exchange.sendResponseHeaders(200, response.length());
			OutputStream output = exchange.getResponseBody();
			output.write(response.getBytes());
			output.close();
			System.out.println("----");
		} catch (Exception e) {
			e.printStackTrace();
			exchange.sendResponseHeaders(500, 0);
		}
	}

}
