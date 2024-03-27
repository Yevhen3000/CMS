/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Server;

import cms.Config;
import com.sun.net.httpserver.HttpContext;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import com.sun.net.httpserver.HttpHandler;

    
/**
 * @author  Yevhen Kuropiatnyk
 * @email   evgeniy.kuropyatnik@gmail.com
 * @student sba23066
 */

public class HttpServer {

    private boolean serverIsActive = false;
    private com.sun.net.httpserver.HttpServer server;
    private InetAddress localAddress;
    
    private Config config;
    
    public HttpServer(Config appConfig) {
        config = appConfig;
    }

    /*
    * Create HttpServer which is listening to the given port on the given IP address
    */
    public void start() {
        
        try {
            localAddress = InetAddress.getByName(config.http_server_ip);
            server = com.sun.net.httpserver.HttpServer.create(new InetSocketAddress(localAddress, config.http_server_port), 0);
            HttpContext context = server.createContext("/", new MyHttpHandler());
            server.start();
            serverIsActive = true;
        } catch (Exception e) {
            System.out.println("Error server: " +  e.getMessage());
        }
    }
    
    public void stop() {
        if (serverIsActive) {
            try {
                server.stop(0);
            } catch (Exception e) {
                System.out.println("Error server: " +  e.getMessage());
            }
        }
    }
    
}
