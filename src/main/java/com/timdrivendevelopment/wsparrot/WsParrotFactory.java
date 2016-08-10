package com.timdrivendevelopment.wsparrot;

public class WsParrotFactory {
    public static WsParrotWebServer newWebServer() {
        return new WsParrotWebServer();
    }
}
