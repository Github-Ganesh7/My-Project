package com.my.project.core.servlets;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.servlet.ServletException;
import java.io.IOException;
import java.io.PrintWriter;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class HelloWorldServletTest {

    private HelloWorldServlet helloWorldServlet;
    private SlingHttpServletRequest request;
    private SlingHttpServletResponse response;
    private PrintWriter writer;

    @BeforeEach
    void setUp() throws IOException {
        helloWorldServlet = new HelloWorldServlet();
        request = Mockito.mock(SlingHttpServletRequest.class);
        response = Mockito.mock(SlingHttpServletResponse.class);
        writer = Mockito.mock(PrintWriter.class);

        // Set up the response to return the mocked PrintWriter
        when(response.getWriter()).thenReturn(writer);
    }

    @Test
    void testDoGet() throws ServletException, IOException {
        // Call the method under test
        helloWorldServlet.doGet(request, response);

        // Verify that the response content type is set
        verify(response).setContentType("text/html");

        // Verify that the PrintWriter's println method was called with the expected HTML content
        verify(writer).println("<html><body>");
        verify(writer).println("<h1>Hello World!!!</h1>");
    }
}

