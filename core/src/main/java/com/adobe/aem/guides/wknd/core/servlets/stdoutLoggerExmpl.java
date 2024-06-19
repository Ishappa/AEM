
package com.adobe.aem.guides.wknd.core.servlets;

import com.drew.lang.annotations.NotNull;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletPaths;
import org.osgi.service.component.annotations.Component;
import javax.servlet.Servlet;
import javax.servlet.annotation.HttpMethodConstraint;
import java.io.IOException;

@Component(service = Servlet.class)
@SlingServletPaths(value = "/bin/StdoutExmpl")
@HttpMethodConstraint(HttpConstants.METHOD_GET)
public class stdoutLoggerExmpl extends SlingAllMethodsServlet {
    @Override
    protected void doGet(@NotNull SlingHttpServletRequest request, @NotNull SlingHttpServletResponse response) throws IOException {
        System.out.println("This is a message printed through System.out.println().");
        int num = 10;
        System.out.println("The value of num is: " + num);
        response.getWriter().write("Hello Servlet");
    }
}




