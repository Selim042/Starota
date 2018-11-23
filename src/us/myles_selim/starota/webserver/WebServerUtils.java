package us.myles_selim.starota.webserver;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;

import com.sun.net.httpserver.HttpExchange;

public class WebServerUtils {

	public static void notLoggedIn(HttpExchange exchange) {
		returnFile(exchange, new File("html", "notLoggedIn.html"));
	}

	public static void returnFile(HttpExchange exchange, File file) {
		try {
			BufferedReader in = new BufferedReader(new FileReader(file));
			String data = "";
			String line;
			while ((line = in.readLine()) != null)
				data += line;
			in.close();
			OutputStream out = exchange.getResponseBody();
			exchange.sendResponseHeaders(200, data.length());
			out.write(data.getBytes());
			out.close();
			exchange.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
