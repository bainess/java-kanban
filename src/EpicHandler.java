import com.google.gson.reflect.TypeToken;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class EpicHandler extends BaseHttpHandler implements HttpHandler {
    String method;
    String path;

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try{
            method = exchange.getRequestMethod();
            path = exchange.getRequestURI().getPath();

            switch (method) {
                case "GET":
                    writeResponse(exchange, handleGetRequest(exchange), 200);
                    break;
                case "POST":
                    handlePostRequest(exchange);
                    writeResponse(exchange, "Epic successfully added", 201);
                    break;
                case "DELETE":
                    handleDeleteRequest(exchange);
                    break;
                default:
                    writeResponse(exchange, "Unknown method for epic", 500);
            }
        } catch (IOException e) {
            writeResponse(exchange, "Internal server error", 500);
        }
    }

    private void handleDeleteRequest(HttpExchange exchange)  throws IOException{
        String[] splitPath = path.split("/");
        if (splitPath.length == 2) {
            MANAGER.removeAllEpics();
        } else if (splitPath.length == 3) {
            MANAGER.removeEpicById(Integer.parseInt(splitPath[2]));
        }
    }

    private void handlePostRequest(HttpExchange exchange) throws IOException {
        String body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
        Epic epic = GSON.fromJson(body, Epic.class);
        if (epic.getId() == 0) {
            MANAGER.createEpic(epic);
        } else {
            MANAGER.editEpic(epic);
        }
    }

    static class EpicTypeToken extends TypeToken<List<Epic>>{}

    private String handleGetRequest(HttpExchange exchange) {
        String[] splitPath = path.split("/");
        String respond = "";
        if (splitPath.length == 2) {
            respond = GSON.toJson(MANAGER.getAllEpic(), new EpicTypeToken().getType());
        } else if (splitPath.length == 3) {
            respond = GSON.toJson(MANAGER.getEpicById(Integer.parseInt(splitPath[2])));
        }
        return respond;
    }
}
