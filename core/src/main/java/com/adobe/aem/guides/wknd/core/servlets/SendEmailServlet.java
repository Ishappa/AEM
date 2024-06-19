package com.adobe.aem.guides.wknd.core.servlets;
import com.day.cq.mailer.MessageGateway;
import com.day.cq.mailer.MessageGatewayService;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletPaths;
import org.apache.sling.xss.XSSAPI;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Servlet;
import javax.servlet.annotation.HttpMethodConstraint;
import java.io.IOException;

@Component(service = Servlet.class)
@SlingServletPaths(value ="/bin/sendmailtousers")
@HttpMethodConstraint(value = HttpConstants.METHOD_POST)
public class SendEmailServlet extends SlingAllMethodsServlet {

    private static final Logger LOG = LoggerFactory.getLogger(SendEmailServlet.class);

    //it will access SMTP directly
    @Reference
    private MessageGatewayService messageGatewayService;


    //security purpose(cross site scripting API)
    @Reference
    private XSSAPI xssApi;

    @Override
    protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {
        try {
            // Retrieve form data and encode using XSSAPI
            String name = xssApi.encodeForHTMLAttr(request.getParameter("name"));
            String email = xssApi.encodeForHTMLAttr(request.getParameter("email"));
            String subject = xssApi.encodeForHTML(request.getParameter("subject"));
            String message = xssApi.encodeForHTML(request.getParameter("message"));


            String recipientEmail = email;

            HtmlEmail emailObj = new HtmlEmail();
            emailObj.addTo(recipientEmail);
            emailObj.setSubject("Contact Form Submission ");

            // Set both HTML and plain text versions of the message
            String htmlMessage = "<html><body>"
                    + "<p>Thank you for reaching out to us. We have received your inquiry with the following details:</p>"
                    + "<ul>"
                    + "<li><strong>Subject:</strong> " + subject + "</li>"
                    + "<li><strong>Message:</strong> " + message + "</li>"
                    + "</ul>"
                    + "<p>We will get back to you very soon regarding your query. Please note that this is an auto-generated email, so there's no need to reply.</p>"
                    + "<p>Thank you!</p>"
                    + "</body></html>";

            emailObj.setHtmlMsg(htmlMessage);

            MessageGateway<Email> messageGateway = messageGatewayService.getGateway(emailObj.getClass());
            messageGateway.send(emailObj);

            LOG.info("Email sent successfully to: {}", recipientEmail);
            response.getWriter().write("Email sent successfully!");
        } catch (EmailException e) {
            LOG.error("Error sending email", e);
            response.setStatus(500);
            response.getWriter().write("Error sending email");
        }
    }
}
