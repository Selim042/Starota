package us.myles_selim.starota.webserver;

import java.io.IOException;
import java.io.OutputStream;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

@SuppressWarnings("restriction")
public class HttpHandlerSettings implements HttpHandler {

	@Override
	public void handle(HttpExchange ex) throws IOException {
		String path = ex.getRequestURI().toString();
//		long serverId = 
		String reply = "path";
		try {
			OutputStream response = ex.getResponseBody();
			ex.sendResponseHeaders(200, reply.length());
			response.write(reply.getBytes());
			response.close();
		} catch (IOException e) {}
	}

}
