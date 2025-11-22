import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.Duration;
import java.util.List;

import com.google.gson.Gson;

public class TaskHandler extends BaseHttpHandler implements HttpHandler {
    String method;
    String path;
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            method = exchange.getRequestMethod();
            path = exchange.getRequestURI().getPath();
            switch (method) {
                case "GET":
                    writeResponse(exchange, handleGetRequest(exchange), 200);
                    break;
                case "POST":
                    handlePostRequest(exchange);
                    writeResponse(exchange, "Task successfully added" , 201);
                    break;
            case "DELETE":
                handleDeleteRequest(exchange);
                writeResponse(exchange, "Task successfully deleted", 201);
                     break;
                default:
                    writeResponse(exchange, "Unknown method", 404);
            }
        } catch (Exception e) {
           System.out.println("Error");
        }
    }

    private void handleDeleteRequest(HttpExchange exchange) throws IOException {
        String[] splithPath = path.split("/");
        if (splithPath.length == 2) {
            MANAGER.removeAllTasks();
        } else if (splithPath.length == 3) {
            MANAGER.removeTaskById(Integer.parseInt(splithPath[2]));
        }
    }

    private void handlePostRequest(HttpExchange exchange) throws IOException {
        String body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
        Task task = GSON.fromJson(body, Task.class);
        if (task.getId() == 0)
        {
            MANAGER.createTask(task);
        } else {
            MANAGER.editTask(task);
        }
    }

    static class TaskTypeToken extends TypeToken<List<Task>>{}

    private String handleGetRequest(HttpExchange exchange) {
        String[] splithPath = path.split("/");
        String respond = "";

        if (splithPath.length == 2) {
           respond = GSON.toJson(MANAGER.getAllTasks(), new TaskTypeToken().getType());
        } else if (splithPath.length == 3) {
           respond = GSON.toJson(MANAGER.getTaskById(Integer.parseInt(splithPath[2])));
        }
        return respond;
    }
}


