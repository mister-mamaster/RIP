package org.example.Lab2;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import org.example.Lab2.DB.DBUtil;
import org.example.Lab2.model.User;
import org.example.Lab2.parsers.AuthParser;
import org.example.Lab2.parsers.RegistrationParser;
import org.example.Lab2.session.SessionManager;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Objects;

public class ThirdLabInitializer extends DefaultServerInitializer {

    private final SessionManager sessionManager = new SessionManager();

    @Override
    public void initialize(HttpServer server) {
        super.initialize(server);
        server.createContext("/auth", new AuthHandler(this.sessionManager));
        server.createContext("/regist", new RegistHandler());
        server.createContext("/welcome", new WelcomeHandler(this.sessionManager));
    }

    static class RegistHandler implements HttpHandler {

        @Override
        public void handle(HttpExchange t) throws IOException {
            var userData = new RegistrationParser().parse(t.getRequestBody());
            var nUser = new User(userData.username, userData.email, userData.password, userData.login);
            nUser.save();
            t.sendResponseHeaders(201, 0);
            OutputStream os = t.getResponseBody();
            os.close();
        }
    }

    static class AuthHandler implements HttpHandler {

        private final SessionManager sessionManager;

        public AuthHandler(SessionManager sessionManager) {
            this.sessionManager = sessionManager;
        }

        @Override
        public void handle(HttpExchange t) throws IOException {
            try {
                RegistrationData data = new AuthParser().parse(t.getRequestBody());
                var user = DBUtil.getUserByLogin(data.login);
                if (!Objects.equals(user.getPassword(), data.password)) throw new RuntimeException("Invalid password");
                var sessionId = sessionManager.createSession(user).getId();
                t.getResponseHeaders().put("Set-cookie", Collections.singletonList("sessionId=" + sessionId));
                t.sendResponseHeaders(200, -1);
                t.close();
            } catch (RuntimeException | SQLException e) {
                t.sendResponseHeaders(400, 0);
                t.close();
            }
        }
    }

    static class WelcomeHandler implements HttpHandler {

        private final SessionManager sessionManager;
        private static final ObjectMapper mapper = new ObjectMapper();
        private static final ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());

        public WelcomeHandler(SessionManager sessionManager) {
            this.sessionManager = sessionManager;
        }

        @Override
        public void handle(HttpExchange t) throws IOException {
            try {
                if(!t.getRequestHeaders().containsKey("Cookie")) {
                    throw new RuntimeException();
                }
                var user = sessionManager.getUser(t.getRequestHeaders().get("Cookie").get(0).replaceFirst("sessionId=", ""));
                t.getResponseHeaders().put("Content-Type", Collections.singletonList("application/json"));
                OutputStream os = t.getResponseBody();
                ObjectNode json = mapper.createObjectNode();
                json.put("userName", user.getName());
                t.sendResponseHeaders(200, mapper.writeValueAsBytes(json).length);
                os.write(mapper.writeValueAsBytes(json));
                os.close();
                t.close();
            } catch (RuntimeException e) {
                t.sendResponseHeaders(401, 0);
            }
        }
    }
}
