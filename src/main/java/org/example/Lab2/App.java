package org.example.Lab2;



import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class App 
{
    public static void main( String[] args ) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8095), 0);
        ServerInitializer initializer = new ThirdLabInitializer();
        initializer.initialize(server);
        server.start();
        System.out.println("Server is ready!");
    }
}
