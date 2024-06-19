package com.adobe.aem.guides.wknd.core.servlets;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletPaths;
import org.osgi.service.component.annotations.Component;

import javax.jcr.Credentials;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.SimpleCredentials;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.annotation.HttpMethodConstraint;
import java.io.IOException;

//SDWA --> SendingDataWithAuthorization
@Component(service = Servlet.class,immediate = true)
@SlingServletPaths("/bin/inspireAuth")
@HttpMethodConstraint(HttpConstants.METHOD_GET)
public class loginUserValidationServlet extends SlingSafeMethodsServlet {
    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        Credentials credentials=new SimpleCredentials(username,password.toCharArray());
        Session session = request.getResourceResolver().adaptTo(Session.class);
        try {
            Session login = session.getRepository().login(credentials);
            if(login!=null && login.isLive()){
                login.logout();
                response.getWriter().write("User Authenticated successfully and data is sending");
            }
            else {
                response.getWriter().write("Invalid User credentials, Cannot send the required data");
            }
        } catch (RepositoryException e) {
            response.getWriter().write("Invalid User credentials, Cannot send the required data");
        }
    }
}