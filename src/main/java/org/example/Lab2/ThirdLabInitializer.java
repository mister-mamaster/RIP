package org.example.Lab2;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;

public class ThirdLabInitializer extends DefaultServerInitializer {

    @Override
    public void initialize(HttpServer server) {
        super.initialize(server);
        server.createContext("/auth", new AuthHandler());
    }

    static class AuthHandler implements HttpHandler {

        @Override
        public void handle(HttpExchange t) throws IOException {



//            TextMessage response = new TextMessage();
//            try {
//                response.formResponse("ping ".repeat((Integer) ParamsParser.parse(t).get("number")));
//            } catch (NullPointerException e) {
//                response.formResponse("ping");
//                e.printStackTrace();
//            }
//            t.sendResponseHeaders(200, response.getTextOfResponse().length());
//            OutputStream os = t.getResponseBody();
//            response.writeResponse(os);
//            os.close();
        }
    }
}
