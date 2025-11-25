import com.google.gson.reflect.TypeToken;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class TaskHandler extends BaseHttpHandler implements HttpHandler {
    String method;
    String path;

    public TaskHandler(Manager manager) {
        super(manager);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            method = exchange.getRequestMethod();
            path = exchange.getRequestURI().getPath();
            switch (method) {
                case "GET":
                    String response = handleGetRequest(exchange);
                    if (response.equals("null")) {
                        writeResponse(exchange, "No epic was found", 404);
                    } else {
                        writeResponse(exchange, response, 200);
                    }
                    break;
                case "POST":
                    handlePostRequest(exchange);
                    writeResponse(exchange, "Task successfully added", 201);
                    break;
            case "DELETE":
                handleDeleteRequest(exchange);
                writeResponse(exchange, "Task successfully deleted", 201);
                     break;
                default:
                    writeResponse(exchange, "Unknown method", 404);
            }
        } catch (IOException e) {
           System.out.println("Error");
           throw new IOException(e);
        } catch (TaskCreationException e) {
            writeResponse(exchange, "Task cannot be added at the time", 406);
        }
    }

    private void handleDeleteRequest(HttpExchange exchange) throws IOException {
        String[] splitPath = path.split("/");
        if (splitPath.length == 2) {
            manager.removeAllTasks();
        } else if (splitPath.length == 3) {
            manager.removeTaskById(Integer.parseInt(splitPath[2]));
        }
    }

    private void handlePostRequest(HttpExchange exchange) throws IOException, TaskCreationException {
        String body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
        Task task = gson.fromJson(body, Task.class);
        if (task.getId() == 0) {
            manager.createTask(task);
        } else {
            manager.editTask(task);
        }
    }

    static class TaskTypeToken extends TypeToken<List<Task>> {
    }

    private String handleGetRequest(HttpExchange exchange) {
        String[] splitPath = path.split("/");
        String respond = "";

        if (splitPath.length == 2) {
           respond = gson.toJson(manager.getAllTasks(), new TaskTypeToken().getType());
        } else if (splitPath.length == 3) {
           respond = gson.toJson(manager.getTaskById(Integer.parseInt(splitPath[2])));
        }
        return respond;
    }
}


