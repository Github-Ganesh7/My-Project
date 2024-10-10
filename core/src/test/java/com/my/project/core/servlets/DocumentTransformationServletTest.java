package com.my.project.core.servlets;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.servlet.ServletException;
import java.io.PrintWriter;
import java.io.IOException;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class DocumentTransformationServletTest {

    private DocumentTransformationServlet documentTransformationServlet;
    private SlingHttpServletRequest request;
    private SlingHttpServletResponse response;
    private PrintWriter writer;

    @BeforeEach
    void setUp() {
        documentTransformationServlet = new DocumentTransformationServlet();
        request = Mockito.mock(SlingHttpServletRequest.class);
        response = Mockito.mock(SlingHttpServletResponse.class);
        writer = Mockito.mock(PrintWriter.class);

        try {
            when(response.getWriter()).thenReturn(writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testDoGet_WithMissingParameters() throws ServletException, IOException {

        when(request.getParameter("name")).thenReturn(null);
        when(request.getParameter("type")).thenReturn("exampleType");
        when(request.getParameter("collection")).thenReturn("exampleCollection");
        when(request.getParameter("format")).thenReturn("exampleFormat");
        when(request.getParameter("sorting")).thenReturn("exampleSorting");
        when(request.getParameter("flags")).thenReturn("exampleFlags");
        documentTransformationServlet.doGet(request, response);
        verify(response).setStatus(422);
        verify(response).setContentType("application/json");
        verify(writer).write(contains("missingParameters"));
    }

    @Test
    void testDoGet_WithValidParameters() throws ServletException, IOException {

        when(request.getParameter("name")).thenReturn("ReferCoBios");
        when(request.getParameter("type")).thenReturn("bio");
        when(request.getParameter("collection")).thenReturn("/content/dam/collections/s/sA3ceCjfpbQ1v6ZdGM25/private_collection");
        when(request.getParameter("format")).thenReturn("ppt-horizontal");
        when(request.getParameter("sorting")).thenReturn("sorting");
        when(request.getParameter("flags")).thenReturn("flags");
        documentTransformationServlet.doGet(request, response);
        verify(response).setContentType("application/xml");
        verify(writer).write(anyString());
    }

    @Test
    void testSendBadRequestResponse() throws IOException {
        String missingParams = "name, type";
        documentTransformationServlet.sendBadRequestResponse(response, missingParams);
        verify(response).setStatus(422);
        verify(response).setContentType("application/json");
        verify(writer).write(contains("missingParameters"));
    }

    @Test
    void testSendXmlResponse() throws IOException {
        String xmlResponse = "<data><parameters><name>exampleName</name></parameters></data>";
        documentTransformationServlet.sendXmlResponse(response, xmlResponse);
        verify(response).setContentType("application/xml");
        verify(writer).write(xmlResponse);
    }
}
