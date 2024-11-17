package org.example.Lab2;

import com.sun.net.httpserver.HttpServer;

public interface ServerInitializer {
    void initialize(HttpServer server);
}
