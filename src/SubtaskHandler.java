import com.google.gson.reflect.TypeToken;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class SubtaskHandler extends BaseHttpHandler implements HttpHandler {
    String method;
    String path;

    @Override
    public void handle(HttpExchange exchange) throws IOException {
//
//        System.out.println("handler object = " + this);
//        System.out.println("called by thread = " + Thread.currentThread());
//        System.out.println("I am called!");
//        System.out.println(exchange.getRequestHeaders().keySet());
//        System.out.println(exchange.getRequestHeaders().values());
////        exchange.sendResponseHeaders(200, response.length());
//        OutputStream os = exchange.getResponseBody();
////        os.write(response.getBytes());
//        os.close();

        try {
            method = exchange.getRequestMethod();
            path = exchange.getRequestURI().getPath();

            switch (method) {
                case "GET":
                    writeResponse(exchange, handleGetRequest(exchange) , 200);
                    break;
                case "POST":
                    handlePostRequest(exchange);
                    writeResponse(exchange, "Subtask successfully added", 201);
                    break;
                case "DELETE":
                    handleDeleteRequest(exchange);
                    writeResponse(exchange, "Task was deleted", 201);
                    break;
                default:
                    writeResponse(exchange, "Unknown method", 404);
            }
        } catch (Exception e) {
            writeResponse(exchange, "Internal Server Error", 500);
        }
    }

    private void handleDeleteRequest(HttpExchange exchange) throws IOException {
        String[] splitPath = path.split("/");
        if (splitPath.length == 2) {
            MANAGER.removeAllSubtasks();
        } else if (splitPath.length == 3) {
            MANAGER.removeSubtaskById(Integer.parseInt(splitPath[2]));
        }
    }

    private void handlePostRequest(HttpExchange exchange) throws IOException{
            String body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
            Subtask subtask = GSON.fromJson(body, Subtask.class);
            if (subtask.getId() == 0) {
                MANAGER.createSubtask(subtask);
            } else {
                MANAGER.editSubtask(subtask);
            }
    }

    static class SubtaskTypeToken extends TypeToken<List<Subtask>>{}

    private String handleGetRequest(HttpExchange exchange) {
        String[] splitPath = path.split("/");
        String respond = "";
        if (splitPath.length == 2) {
            respond = GSON.toJson(MANAGER.getAllSubtasks(), new SubtaskTypeToken().getType());
        } else if (splitPath.length == 3) {
            respond = GSON.toJson(MANAGER.getSubtaskById(Integer.parseInt(splitPath[2])));
        }
        return respond;
    }
}
