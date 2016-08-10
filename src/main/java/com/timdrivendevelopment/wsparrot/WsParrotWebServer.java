package com.timdrivendevelopment.wsparrot;

import org.eclipse.jetty.server.Server;

public class WsParrotWebServer {

    private Server server;

    public static void main(String[] args) {
        System.out.println("I'm working!!");
        WsParrotFactory.newWebServer().start(9008);
    }

    public void start(int port) {
        server = new Server(port);
        server.setHandler(new AllRequestHandler());
        try {
            server.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        try {
            server.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
