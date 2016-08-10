package com.timdrivendevelopment.wsparrot;

import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import org.eclipse.jetty.server.Server;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class WsParrotWebServerTest {

    private WsParrotWebServer server;

    @Before
    public void startWebServer() {
        server = WsParrotFactory.newWebServer();
        server.start(9008);
    }

    @Test
    public void givenNoRouteDefined_whenGetRequest_shouldReturnNotFound() throws IOException {
        String content = Request.Get("http://localhost:9008").execute().returnContent().asString();
        assertThat(content, is("Not Found"));
    }

    @Test
    public void givenNoRouteDefined_whenPostRequest_shouldReturnNotFound() throws IOException {
        String content = Request.Post("http://localhost:9008").execute().returnContent().asString();
        assertThat(content, is("Not Found"));
    }

    @After
    public void stopWebServer() {
        server.stop();
    }

}
