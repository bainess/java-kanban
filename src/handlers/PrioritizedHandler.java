package handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import controllers.Manager;

import java.io.IOException;

public class PrioritizedHandler extends BaseHttpHandler implements HttpHandler {
    public PrioritizedHandler(Manager manager) {
        super(manager);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        switch (method) {
            case "GET":
                writeResponse(exchange, handleGetRequest(exchange), 200);
                break;
            default:
                writeResponse(exchange, "METHOD_NOT_ALLOWED", 405);
        }
    }

    private String handleGetRequest(HttpExchange exchange) {
        return gson.toJson(manager.getPrioritizedTasks());
    }
}
