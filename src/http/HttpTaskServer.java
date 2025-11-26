package http;

import com.sun.net.httpserver.HttpServer;
import controllers.InMemoryTaskManager;
import controllers.Manager;
import handlers.*;

import java.io.IOException;
import java.net.InetSocketAddress;

public class HttpTaskServer {
    private static final int PORT = 8080;
    private final HttpServer httpServer;

    public HttpTaskServer(Manager manager) throws IOException {
        this.httpServer = HttpServer.create(new InetSocketAddress(PORT), 0);
        this.httpServer.createContext("/tasks", new TaskHandler(manager));
        this.httpServer.createContext("/subtasks", new SubtaskHandler(manager));
        this.httpServer.createContext("/epics", new EpicHandler(manager));
        this.httpServer.createContext("/history", new HistoryHandler(manager));
        this.httpServer.createContext("/prioritized", new PrioritizedHandler(manager));
    }

    public void start() {
        this.httpServer.start();
    }

    public void stop() {
        this.httpServer.stop(1);
    }

   public static void main(String[] args) throws IOException {
        Manager manager = new InMemoryTaskManager();
        new HttpTaskServer(manager).start();
    }
}
