package com.timdrivendevelopment.wsparrot;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class WsParrotWebServer {

    private Server server;
    private Map<String, String> routes = new HashMap<>();

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

    public void addRoute(String route, String response) {
        routes.put(route, response);
    }

    private class AllRequestHandler extends AbstractHandler {
        @Override
        public void handle(String target, Request request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException, ServletException {
            System.out.println("Target: " + target);
            if ("/parrotthis".equals(target) && "POST".equals(httpServletRequest.getMethod())) {
                routes.put(
                        httpServletRequest.getParameter("uri"),
                        httpServletRequest.getParameter("response")
                );
                httpServletResponse.setContentType("text/html;charset=utf-8");
                httpServletResponse.setStatus(HttpServletResponse.SC_OK);
                request.setHandled(true);
                httpServletResponse.getWriter().print(routes);
            } else if ("/parrotthis".equals(target) && "GET".equals(httpServletRequest.getMethod())) {
                httpServletResponse.setContentType("text/html;charset=utf-8");
                httpServletResponse.setStatus(HttpServletResponse.SC_OK);
                request.setHandled(true);
                httpServletResponse.getWriter().print(routes);
            } else {
                httpServletResponse.setContentType("text/html;charset=utf-8");
                httpServletResponse.setStatus(HttpServletResponse.SC_OK);
                request.setHandled(true);
                httpServletResponse.getWriter().print(routes.getOrDefault(target, "Not Found"));
            }
        }
    }
}
