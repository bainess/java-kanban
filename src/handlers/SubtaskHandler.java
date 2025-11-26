package handlers;

import com.google.gson.reflect.TypeToken;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import controllers.Manager;
import exceptions.SubtaskCreationException;
import exceptions.TaskCreationException;
import controllers.model.Subtask;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class SubtaskHandler extends BaseHttpHandler implements HttpHandler {
    private String method;
    private String path;

    public SubtaskHandler(Manager manager) {
        super(manager);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            method = exchange.getRequestMethod();
            path = exchange.getRequestURI().getPath();

            switch (method) {
                case "GET":
                    String getResponse = handleGetRequest(exchange);
                    if (getResponse.equals("null")) {
                        writeResponse(exchange, "No task found", 404);
                    } else {
                        writeResponse(exchange, getResponse, 200);
                    }
                    break;
                case "POST":
                    handlePostRequest(exchange);
                    writeResponse(exchange, "model.Subtask successfully added", 201);
                    break;
                case "DELETE":
                    handleDeleteRequest(exchange);
                    writeResponse(exchange, "model.Task was deleted", 201);
                    break;
                default:
                    writeResponse(exchange, "METHOD_NOT_ALLOWED", 405);
            }
        } catch (IOException e) {
            writeResponse(exchange, "Internal Server Error", 500);
        } catch (TaskCreationException e) {
            writeResponse(exchange, "Not acceptable", 406);
        } catch (SubtaskCreationException e) {
            writeResponse(exchange, "Not acceptable", 404);
        }
    }

    private void handleDeleteRequest(HttpExchange exchange) throws IOException {
        String[] splitPath = path.split("/");
        if (splitPath.length == 2) {
            manager.removeAllSubtasks();
        } else if (splitPath.length == 3) {
            manager.removeSubtaskById(Integer.parseInt(splitPath[2]));
        }
    }

    private void handlePostRequest(HttpExchange exchange) throws IOException, TaskCreationException, SubtaskCreationException {
            String body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
            Subtask subtask = gson.fromJson(body, Subtask.class);
            if (subtask.getId() == 0) {
                manager.createSubtask(subtask);
            } else {
                manager.editSubtask(subtask);
            }
    }

    static class SubtaskTypeToken extends TypeToken<List<Subtask>> {
    }

    private String handleGetRequest(HttpExchange exchange) {
        String[] splitPath = path.split("/");
        String respond = "";
        if (splitPath.length == 2) {
            respond = gson.toJson(manager.getAllSubtasks(), new SubtaskTypeToken().getType());
        } else if (splitPath.length == 3) {
            respond = gson.toJson(manager.getSubtaskById(Integer.parseInt(splitPath[2])));
        }
        return respond;
    }
}
