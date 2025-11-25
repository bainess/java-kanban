import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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

public class EpicHandlerTest {
    Manager manager = new InMemoryTaskManager();
    HttpTaskServer taskServer = new HttpTaskServer(manager);
    Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .registerTypeAdapter(Duration.class, new DurationAdapter()).create();

    public EpicHandlerTest() throws IOException {
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
        URI url = URI.create("http://localhost:8080/epics");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .POST(HttpRequest.BodyPublishers.ofString(taskJson))
                .header("Content-Type", "application/json")
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(201, response.statusCode());

        List<Epic> taskFromManager = manager.getAllEpic();

        assertNotNull(taskFromManager, "No tasks");
        assertEquals(1, taskFromManager.size(), "Wrong number of tasks");
        assertEquals("do", taskFromManager.get(0).getTitle());
    }

    @Test
    public void shouldReturnAddedTask() throws IOException, InterruptedException {
        Epic epic = new Epic("do", "complete tests");
        String taskJson = gson.toJson(epic);
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/epics");
        HttpRequest postRequest = HttpRequest.newBuilder().uri(url).POST(HttpRequest.BodyPublishers.ofString(taskJson)).build();

        client.send(postRequest, HttpResponse.BodyHandlers.ofString());
        HttpRequest getRequest = HttpRequest.newBuilder().uri(url).GET().build();
        HttpResponse<String> getResponse = client.send(getRequest, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, getResponse.statusCode());

        List<Epic> taskFromManager = manager.getAllEpic();

        assertNotNull(taskFromManager, "No tasks");
        assertEquals(1, taskFromManager.size(), "Wrong number of tasks");
        assertEquals("do", taskFromManager.get(0).getTitle());
    }

    @Test
    public void shouldRemoveAddedTask() throws IOException, InterruptedException {
        Task task = new Task("complete", "complete tests", Status.NEW, LocalDateTime.now(), Duration.ofMinutes(5));
        String taskJson = gson.toJson(task);
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/epics");
        HttpRequest postRequest = HttpRequest.newBuilder().uri(url).POST(HttpRequest.BodyPublishers.ofString(taskJson)).build();

        client.send(postRequest, HttpResponse.BodyHandlers.ofString());
        HttpRequest deleteRequest = HttpRequest.newBuilder().uri(url).DELETE().build();
        HttpResponse<String> getResponse = client.send(deleteRequest, HttpResponse.BodyHandlers.ofString());
        assertEquals(201, getResponse.statusCode());

        List<Epic> taskFromManager = manager.getAllEpic();

        assertNotNull(taskFromManager, "No tasks");
        assertEquals(0, taskFromManager.size(), "Wrong number of tasks");
    }

    @Test
    public void shouldRemoveTaskById() throws IOException, InterruptedException {
        Epic epic = new Epic("do", "complete tests");
        String taskJson = gson.toJson(epic);
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/epics");
        HttpRequest postRequest = HttpRequest.newBuilder().uri(url).POST(HttpRequest.BodyPublishers.ofString(taskJson)).build();

        client.send(postRequest, HttpResponse.BodyHandlers.ofString());
        URI urlDelete = URI.create("http://localhost:8080/epics/1");
        HttpRequest deleteRequest = HttpRequest.newBuilder().uri(urlDelete).DELETE().build();
        HttpResponse<String> getResponse = client.send(deleteRequest, HttpResponse.BodyHandlers.ofString());
        assertEquals(201, getResponse.statusCode());

        List<Epic> taskFromManager = manager.getAllEpic();

        assertNotNull(taskFromManager, "No tasks");
        assertEquals(0, taskFromManager.size(), "Wrong number of tasks");
    }
}
