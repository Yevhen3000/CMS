/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Server;

import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.io.OutputStream;
import com.sun.net.httpserver.HttpHandler;
import java.util.Scanner;

/**
 * @author  Yevhen Kuropiatnyk
 * @email   evgeniy.kuropyatnik@gmail.com
 * @student sba23066
 */

public class MyHttpHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
//        String response = "Login form here:"; 
//        // Or REST API functionality implementation
//        
//        // Response status and length
//        exchange.sendResponseHeaders(200, response.length());
//
//        // Output the response 
//        try (OutputStream stream = exchange.getResponseBody()) {
//            stream.write(response.getBytes());
//        }

      // Get the request method (GET, POST, etc.)
            String requestMethod = exchange.getRequestMethod();
            
            // Get the request URI
            String requestURI = exchange.getRequestURI().toString();
            
            // Read request data (if any)
            Scanner scanner = new Scanner(exchange.getRequestBody());
            StringBuilder requestBody = new StringBuilder();
            while (scanner.hasNextLine()) {
                requestBody.append(scanner.nextLine());
            }
            scanner.close();
            
            //Request Method: GET
            //Request URI: /home  OR /home?menuitem=1
            //Request Body: 
            
            // Prepare the response
            String response = "Request Method: " + requestMethod + "\n"
                            + "Request URI: " + requestURI + "\n"
                            + "Request Body: " + requestBody.toString() + "\n";
            
            // Set response headers
            exchange.getResponseHeaders().set("Content-Type", "text/plain");
            exchange.sendResponseHeaders(200, response.getBytes().length);
            
            // Write response body
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();

    }
}