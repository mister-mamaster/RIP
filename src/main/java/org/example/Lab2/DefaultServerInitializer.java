package org.example.Lab2;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import org.example.Lab2.parsers.ParamsParser;

import java.io.IOException;
import java.io.OutputStream;

public class DefaultServerInitializer implements ServerInitializer {
    @Override
    public void initialize(HttpServer server) {
        server.createContext("/ping", new PingHandler());
        server.setExecutor(null);
    }

    static class PingHandler implements HttpHandler {

        @Override
        public void handle(HttpExchange t) throws IOException {
            TextMessage response = new TextMessage();
            try {
                response.formResponse("ping ".repeat((Integer) new ParamsParser().parse(t).get("number")));
            } catch (NullPointerException e) {
                response.formResponse("ping");
                e.printStackTrace();
            }
            t.sendResponseHeaders(200, response.getTextOfResponse().length());
            OutputStream os = t.getResponseBody();
            response.writeResponse(os);
            os.close();
        }
    }
}
