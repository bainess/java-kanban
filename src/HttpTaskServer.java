import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.nio.charset.StandardCharsets;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class HttpTaskServer {
    private static final int PORT = 8080;

   public static void main(String[] args) throws IOException{
       HttpServer httpServer = HttpServer.create(new InetSocketAddress(PORT), 0);
       httpServer.createContext("/tasks", new TaskHandler());
       httpServer.createContext("/subtasks", new SubtaskHandler());
       httpServer.createContext("/epics", new EpicHandler());
       httpServer.createContext("/history", new HistoryHandler());
       httpServer.createContext("/prioritized", new PrioritizedHandler());

       httpServer.start();
    }


}
