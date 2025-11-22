import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;

public class BaseHttpHandler {
    protected final static Manager MANAGER = new Managers().getDefault();
    protected final static Gson GSON = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .registerTypeAdapter(Duration.class, new DurationAdapter())
            .create();

//        protected void sendText(HttpExchange h, String text) throws IOException {
//            byte[] resp = text.getBytes(StandardCharsets.UTF_8);
//            h.getResponseHeaders().add("Content-Type", "application/json;charset=utf-8");
//            h.sendResponseHeaders(200, resp.length);
//            h.getResponseBody().write(resp);
//            h.close();
//    }

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
