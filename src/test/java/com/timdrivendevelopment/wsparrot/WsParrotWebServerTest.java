package com.timdrivendevelopment.wsparrot;

import org.apache.http.client.fluent.Request;
import org.apache.http.message.BasicNameValuePair;
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

    @Test
    public void givenTestRouteDefined_whenGetRequest_shouldReturnTestResponse() throws IOException {
        server.addRoute("/test", "Test Response");
        String content = Request.Get("http://localhost:9008/test").execute().returnContent().asString();
        assertThat(content, is("Test Response"));
    }

    @Test
    public void givenTestRouteDefinedAsPostRequest_whenGetRequest_shouldReturnTestResponse() throws IOException {
        Request.Post("http://localhost:9008/parrotthis").bodyForm(
                new BasicNameValuePair("uri", "/test"),
                new BasicNameValuePair("response", "Test Response")).execute().discardContent();
        String content = Request.Get("http://localhost:9008/test").execute().returnContent().asString();
        assertThat(content, is("Test Response"));
    }

    @After
    public void stopWebServer() {
        server.stop();
    }

}
