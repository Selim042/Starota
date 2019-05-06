package us.myles_selim.starota.webserver.webhooks.other;

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
import com.google.gson.JsonSyntaxException;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import discord4j.core.object.entity.Guild;
import us.myles_selim.starota.Starota;
import us.myles_selim.starota.enums.EnumDonorPerm;
import us.myles_selim.starota.misc.utils.RolePermHelper;
import us.myles_selim.starota.webserver.webhooks.EnumWebhookType;
import us.myles_selim.starota.webserver.webhooks.WebhookClass;
import us.myles_selim.starota.webserver.webhooks.WebhookData;
import us.myles_selim.starota.webserver.webhooks.WebhookQuest;
import us.myles_selim.starota.wrappers.StarotaServer;

@SuppressWarnings("restriction")
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
				if (!jobj.has("message") || !jobj.get("message").isJsonObject())
					throw new JsonParseException("json must contain a JsonObject with key message");
				WebhookClass<WebhookData> wClass = new WebhookClass<>();
				wClass.type = EnumWebhookType.valueOf(EnumWebhookType.class,
						jobj.get("type").getAsString().toUpperCase());

				if (wClass.type.equals(EnumWebhookType.QUEST)) {
					WebhookQuest task = new WebhookQuest();
					task.data = jobj.get("message").toString();
					wClass.message = task;
				} else
					wClass.message = context.deserialize(jobj.get("message").getAsJsonObject(),
							wClass.type.getDataClass());

				return wClass;
			}
		});

		GSON = builder.create();
	}

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		try {
			String url = exchange.getRequestURI().toString();
			String secret = null;
			int hookIndex = url.indexOf("webhooks/");
			if (url.matches(".*?/webhooks/\\d{18}?/.+/?")) {
				// System.out.println(url);
				secret = url.substring(hookIndex + 28, url.length() - (url.endsWith("/") ? 1 : 0));
				System.out.println(secret);
			}
			if (secret == null && !url.matches(".*?/webhooks/\\d{18}?/?")) {
				exchange.sendResponseHeaders(400, 0);
				OutputStream output = exchange.getResponseBody();
				output.close();
				return;
			}
			// System.out.println(url);
			// boolean endingSlash = url.endsWith("/");
			// long guildId = Long.parseLong(url.substring(url.length() -
			// (endingSlash ? 19 : 18),
			// endingSlash ? url.length() - 1 : url.length()));
			long guildId = Long.parseLong(url.substring(hookIndex + 9, hookIndex + 27));
			Guild guild;
			if (Starota.FULLY_STARTED)
				guild = Starota.getGuild(guildId);
			else
				guild = null;
			if (guild == null || !RolePermHelper.getDonorPerms(guild).contains(EnumDonorPerm.WEBHOOKS)) {
				String response = "{\"error\":\"server does not exist or does not have webhooks enabled\"}";
				exchange.sendResponseHeaders(500, response.length());
				OutputStream output = exchange.getResponseBody();
				output.write(response.getBytes());
				output.close();
				return;
			}
			InputStreamReader requestBody = new InputStreamReader(exchange.getRequestBody());
			WebhookClass<?>[] data;
			try {
				data = GSON.fromJson(PARSER.parse(requestBody), WebhookClass[].class);
			} catch (JsonSyntaxException e) {
				String response = "invalid json, " + e.getMessage();
				exchange.sendResponseHeaders(200, response.length());
				OutputStream output = exchange.getResponseBody();
				output.write(response.getBytes());
				output.close();
				return;
			}
			StarotaServer server = StarotaServer.getServer(guild);
			String serverSecret = server.getWebhookSecret();
			for (WebhookClass<?> hookC : data) {
				if (hookC.secret == null)
					hookC.secret = secret;
				if (hookC.type != null && (serverSecret == null || hookC.secret.equals(serverSecret))) {
					// TODO: fire webhook events in my own system
					// Starota.getClient().getDispatcher().dispatch(new
					// WebhookEvent(guild, hookC));
				}
			}

			String response = "";
			exchange.sendResponseHeaders(200, response.length());
			OutputStream output = exchange.getResponseBody();
			output.write(response.getBytes());
			output.close();
		} catch (Exception e) {
			e.printStackTrace();
			exchange.sendResponseHeaders(500, 0);
		}
	}

}
