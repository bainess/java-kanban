import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

public class HistoryHandler extends BaseHttpHandler implements HttpHandler {
    String method;
    String path;

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            method = exchange.getRequestMethod();
            path = exchange.getRequestURI().getPath();

            switch(method) {
                case "GET":
                    writeResponse(exchange, handleGetRequest(exchange), 200);
                    break;
                default:
                    writeResponse(exchange, "Internal Service Error", 500);

            }
        } catch (IOException e) {
            writeResponse(exchange, "Internal Service Error", 500);
        }
    }

    private String handleGetRequest(HttpExchange exchange) {
        return GSON.toJson(MANAGER.showHistory());
    }
}
