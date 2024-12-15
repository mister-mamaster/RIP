package org.example.Lab2;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import org.example.Lab2.DB.DBUtil;
import org.example.Lab2.model.User;
import org.example.Lab2.parsers.AuthParser;
import org.example.Lab2.parsers.RegistrationParser;
import org.example.Lab2.session.SessionManager;

import java.io.IOException;
import java.sql.SQLException;

public class ThirdLabInitializer extends DefaultServerInitializer {

    private final SessionManager sessionManager = new SessionManager();

    @Override
    public void initialize(HttpServer server) {
        super.initialize(server);
        server.createContext("/auth", new AuthHandler(this.sessionManager));
        server.createContext("/regist", new RegistHandler());
    }

    static class RegistHandler implements HttpHandler {

        @Override
        public void handle(HttpExchange t) throws IOException {
            var userData = new RegistrationParser().parse(t.getRequestBody());
            var nUser = new User(userData.username, userData.email, userData.password, userData.login);
            nUser.save();
            t.sendResponseHeaders(200, 0);
        }
    }

    static class AuthHandler implements HttpHandler {

        private SessionManager sessionManager;

        public AuthHandler(SessionManager sessionManager) {
            this.sessionManager = sessionManager;
        }

        @Override
        public void handle(HttpExchange t) throws IOException {
            try {
                var User = DBUtil.getUserByLogin(new AuthParser().parse(t.getRequestBody()).login);
                var session = sessionManager.createSession().getId();
                t.sendResponseHeaders(201, 0);
                t.setAttribute("SessionId", session);
            } catch (RuntimeException | SQLException e) {
                t.sendResponseHeaders(400, 0);
            }

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
