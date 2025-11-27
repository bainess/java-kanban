package handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import controllers.Manager;

import java.io.IOException;

public class HistoryHandler extends BaseHttpHandler implements HttpHandler {
    private String method;
    private String path;

    public HistoryHandler(Manager manager) {
        super(manager);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            method = exchange.getRequestMethod();
            path = exchange.getRequestURI().getPath();

            switch (method) {
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
        return gson.toJson(manager.showHistory());
    }
}
