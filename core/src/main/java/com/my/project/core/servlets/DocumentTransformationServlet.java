package com.my.project.core.servlets;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.propertytypes.ServiceDescription;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(service = {Servlet.class},
        property = {
                "sling.servlet.paths=/bin/documenttransformationservlet",
                "sling.servlet.methods=GET"
        })
@ServiceDescription("Document Transformation Servlet")
/*
*
* This class is to Create a XML if all the parameters available from the query string
* or
* Create a Json response for the missing parameters.
*
* */
public class DocumentTransformationServlet extends SlingAllMethodsServlet {

    private static final Logger LOG = LoggerFactory.getLogger(DocumentTransformationServlet.class.getName());
    private static final int SC_UNPROCESSABLE_ENTITY = 422;

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
            throws ServletException, IOException {
        LOG.debug("Document Transformation Servlet Started");
        Map<String, String> requiredParams = extractParameters(request);
        String missingParams = validateParameters(requiredParams);
        if (!missingParams.isEmpty()) {
            sendBadRequestResponse(response, missingParams);
            return;
        }
        String xmlResponse = createXmlResponse(requiredParams);
        sendXmlResponse(response, xmlResponse);
    }

/*
* This method is to Extract Parameters from request
* @param SlingHttpServletRequest request
* */
    private Map<String, String> extractParameters(SlingHttpServletRequest request) {
        Map<String, String> params = new HashMap<>();
        params.put("name", request.getParameter("name"));
        params.put("type", request.getParameter("type"));
        params.put("collection", request.getParameter("collection"));
        params.put("format", request.getParameter("format"));
        params.put("sorting", request.getParameter("sorting"));
        params.put("flags", request.getParameter("flags"));
        LOG.debug("Extracted Params from Request :{}", params);
        return params;
    }

/*
* This Method is to Validate parameters from the Request
* @param Map<String, String> params
* */
    private String validateParameters(Map<String, String> params) {
        StringBuilder missingParams = new StringBuilder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (entry.getValue() == null || entry.getValue().isEmpty()) {
                if (missingParams.length() > 0) missingParams.append(", ");
                missingParams.append(entry.getKey());
                LOG.debug("Missing Params :{}", missingParams);
            }
        }
        return missingParams.toString();
    }

/*
* This Method is to Send Bad Request Response in Json
* @param SlingHttpServletResponse response
* @param String missingParams
* */
    public void sendBadRequestResponse(SlingHttpServletResponse response, String missingParams) throws IOException {
        response.setStatus(SC_UNPROCESSABLE_ENTITY);
        response.setContentType("application/json");
        try {
            response.getWriter().write(new JSONObject().put("missingParameters", missingParams).toString());
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        LOG.error("Sent Bad Request Response json for Missing Parameters");
    }

/*
* This Methid is to Send XML Response
* @param SlingHttpServletResponse response
* @param String xmlResponse
* */
    public void sendXmlResponse(SlingHttpServletResponse response, String xmlResponse) throws IOException {
        response.setContentType("application/xml");
        response.getWriter().write(xmlResponse);
        LOG.debug("Sent Complete XML response");
    }

/*
* This method is to Create XML Response
* @param Map<String, String> params
*/
    private String createXmlResponse(Map<String, String> params) {
        StringBuilder mainXml = new StringBuilder();
        mainXml.append("<data>")
                .append(createParametersXml(params))
                .append(createResultsXml())
                .append("</data>");
        LOG.debug("Created Main XML response");
        return mainXml.toString();
    }

/*
* This Method is to Create XML for Parameters
* @param params
* */
    private String createParametersXml(Map<String, String> params) {
        StringBuilder paramsXml = new StringBuilder();
        paramsXml.append("<parameters>")
                .append("<name>").append(params.get("name")).append("</name>")
                .append("<type>").append(params.get("type")).append("</type>")
                .append("<collection>").append(params.get("collection")).append("</collection>")
                .append("<format>").append(params.get("format")).append("</format>")
                .append("<sorting>").append(createSortingXml(params.get("sorting"))).append("</sorting>")
                .append("<flags>").append(params.get("flags")).append("</flags>")
                .append("</parameters>");
        LOG.debug("Created Parameters XML response");
        return paramsXml.toString();
    }
/*
* This method is to Sort
* @param String sortingParam
* */
    private String createSortingXml(String sortingParam) {
        StringBuilder sortingXml = new StringBuilder();
        String[] sortingEntries = sortingParam.split(",");
        for (String entry : sortingEntries) {
            sortingXml.append("<").append(entry.trim()).append(" />");
        }
        LOG.debug("Params Sorting Done");
        return sortingXml.toString();
    }

/*
* This Method is to create XML for Results
* */
    private String createResultsXml() {
        StringBuilder resultsXml = new StringBuilder();
        resultsXml.append("<results>");
        for (int i = 0; i < 3; i++) { // Simulating 3 members
            resultsXml.append("<result><!-- data for member ").append(i + 1).append(" goes here --></result>");
        }
        resultsXml.append("</results>");
        LOG.debug("Created Results XML response");
        return resultsXml.toString();
    }
}
