package handlers;

import adapters.DurationAdapter;
import adapters.LocalDateTimeAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import controllers.InMemoryTaskManager;
import controllers.Manager;
import http.HttpTaskServer;
import controllers.model.Epic;
import controllers.util.Status;
import controllers.model.Subtask;
import controllers.model.Task;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SubtaskHandlerTest {
    Manager manager = new InMemoryTaskManager();
    HttpTaskServer taskServer = new HttpTaskServer(manager);
    Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .registerTypeAdapter(Duration.class, new DurationAdapter()).create();

    public SubtaskHandlerTest() throws IOException {
    }

    @BeforeEach
    public void seUp() {
        manager.removeAll();
        taskServer.start();
    }

    @AfterEach void stop() {taskServer.stop();}

    @Test
    public void shouldAddEpic() throws IOException, InterruptedException {
        Epic epic = new Epic("do", "complete tests");
        String taskJson = gson.toJson(epic);
        HttpClient client = HttpClient.newHttpClient();
        URI urlEpic = URI.create("http://localhost:8080/epics");
        HttpRequest epicRequest= HttpRequest.newBuilder()
                .uri(urlEpic)
                .POST(HttpRequest.BodyPublishers.ofString(taskJson))
                .header("Content-model.Type", "application/json").build();

        URI urlSubtask = URI.create("http://localhost:8080/subtasks");
        Subtask subtask = new Subtask("create", "test 1", Status.NEW, LocalDateTime.now(),
                Duration.ofMinutes(5), 1);
        String subtaskJson = gson.toJson(subtask);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(urlSubtask)
                .POST(HttpRequest.BodyPublishers.ofString(subtaskJson))
                .header("Content-model.Type", "application/json").build();

        client.send(epicRequest, HttpResponse.BodyHandlers.ofString());
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(201, response.statusCode());

        List<Subtask> taskFromManager = manager.getAllSubtasks();

        assertNotNull(taskFromManager, "No tasks");
        assertEquals(1, taskFromManager.size(), "Wrong number of tasks");
        assertEquals("create", taskFromManager.get(0).getTitle());
    }

    @Test
    public void shouldReturnAddedTask() throws IOException, InterruptedException {
        Epic epic = new Epic("do", "complete tests");
        String taskJson = gson.toJson(epic);
        HttpClient client = HttpClient.newHttpClient();
        URI urlEpic = URI.create("http://localhost:8080/epics");
        HttpRequest epicRequest= HttpRequest.newBuilder()
                .uri(urlEpic)
                .POST(HttpRequest.BodyPublishers.ofString(taskJson))
                .header("Content-model.Type", "application/json").build();

        URI urlSubtask = URI.create("http://localhost:8080/subtasks");
        Subtask subtask = new Subtask("create", "test 1", Status.NEW, LocalDateTime.now(),
                Duration.ofMinutes(5), 1);
        String subtaskJson = gson.toJson(subtask);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(urlSubtask)
                .POST(HttpRequest.BodyPublishers.ofString(subtaskJson))
                .header("Content-model.Type", "application/json").build();

        client.send(epicRequest, HttpResponse.BodyHandlers.ofString());
        client.send(request, HttpResponse.BodyHandlers.ofString());

        HttpRequest getRequest = HttpRequest.newBuilder().uri(urlSubtask).GET().build();
        HttpResponse<String> getResponse = client.send(getRequest, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, getResponse.statusCode());

        List<Subtask> taskFromManager = manager.getAllSubtasks();

        assertNotNull(taskFromManager, "No tasks");
        assertEquals(1, taskFromManager.size(), "Wrong number of tasks");
        assertEquals(subtask.getTitle(), taskFromManager.get(0).getTitle());
    }

    @Test
    public void shouldRemoveAddedTask() throws IOException, InterruptedException {
        Task task = new Task("complete", "complete tests", Status.NEW, LocalDateTime.now(), Duration.ofMinutes(5));
        String taskJson = gson.toJson(task);
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/subtasks");
        HttpRequest postRequest = HttpRequest.newBuilder().uri(url).POST(HttpRequest.BodyPublishers.ofString(taskJson)).build();

        client.send(postRequest, HttpResponse.BodyHandlers.ofString());
        HttpRequest deleteRequest = HttpRequest.newBuilder().uri(url).DELETE().build();
        HttpResponse<String> getResponse = client.send(deleteRequest, HttpResponse.BodyHandlers.ofString());
        assertEquals(201, getResponse.statusCode());

        List<Subtask> taskFromManager = manager.getAllSubtasks();

        assertNotNull(taskFromManager, "No tasks");
        assertEquals(0, taskFromManager.size(), "Wrong number of tasks");
    }

    @Test
    public void shouldRemoveTaskById() throws IOException, InterruptedException {
        Epic epic = new Epic("do", "complete tests");
        String taskJson = gson.toJson(epic);
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/subtasks");
        HttpRequest postRequest = HttpRequest.newBuilder().uri(url).POST(HttpRequest.BodyPublishers.ofString(taskJson)).build();

        client.send(postRequest, HttpResponse.BodyHandlers.ofString());
        URI urlDelete = URI.create("http://localhost:8080/subtasks/1");
        HttpRequest deleteRequest = HttpRequest.newBuilder().uri(urlDelete).DELETE().build();
        HttpResponse<String> getResponse = client.send(deleteRequest, HttpResponse.BodyHandlers.ofString());
        assertEquals(201, getResponse.statusCode());

        List<Task> taskFromManager = manager.getAllTasks();

        assertNotNull(taskFromManager, "No tasks");
        assertEquals(0, taskFromManager.size(), "Wrong number of tasks");
    }
}
