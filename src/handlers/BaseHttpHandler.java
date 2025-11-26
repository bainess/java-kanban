package handlers;

import adapters.DurationAdapter;
import adapters.LocalDateTimeAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import controllers.Manager;

import java.io.IOException;
import java.io.OutputStream;
import java.time.Duration;
import java.time.LocalDateTime;

public class BaseHttpHandler {
    protected final Manager manager;
    protected final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .registerTypeAdapter(Duration.class, new DurationAdapter())
            .create();
    protected String path;
    protected String method;

    public BaseHttpHandler(Manager manager) {
        this.manager = manager;
    }

    protected void writeResponse(HttpExchange exchange, String response, int code) throws IOException {
        exchange.getResponseHeaders();
        exchange.sendResponseHeaders(code, 0);
        if (!response.isBlank()) {
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes());
            }
        }
    }
}
