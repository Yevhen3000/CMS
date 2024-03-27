/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Server;

import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.io.OutputStream;
import com.sun.net.httpserver.HttpHandler;

/**
 *
 * @author Admin
 */

public class MyHttpHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String response = "Hello, Students!";

        // Response status and length
        exchange.sendResponseHeaders(200, response.length());

        // Output the response 
        try (OutputStream stream = exchange.getResponseBody()) {
            stream.write(response.getBytes());
        }
    }
}