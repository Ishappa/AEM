package com.adobe.aem.guides.wknd.core.services;

import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthenticationException;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.sling.commons.scheduler.ScheduleOptions;
import org.apache.sling.commons.scheduler.Scheduler;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Component(service = Runnable.class, immediate = true)
@Designate(ocd = ScheduleServiceImpl.ScheduleConfigService.class)
public class ScheduleServiceImpl implements Runnable {
    private static final Logger LOG = LoggerFactory.getLogger(ScheduleServiceImpl.class);
    private String timeCronExpre;
    private String servletUrl;
    private String schedulerName = "ScheduledServletCaller";

    @Reference
    private Scheduler scheduler;

    @Activate
    @Modified
    protected void activate(ScheduleConfigService config) {
        this.timeCronExpre = config.Schedulee();
        this.servletUrl = config.servletUrl();
        LOG.info("Schedule Service activated with cron expression: {}", timeCronExpre);

        ScheduleOptions options = scheduler.EXPR(this.timeCronExpre);
        options.name(schedulerName);
        options.canRunConcurrently(false);

        boolean success = scheduler.schedule(this, options);
        if (success) {
            LOG.info("Scheduler job successfully registered");
        } else {
            LOG.error("Failed to register scheduler job");
        }
    }

    @Deactivate
    protected void deactivate() {
        scheduler.unschedule(schedulerName);
        LOG.info("Scheduler job unscheduled");
    }

    @Override
    public void run() {

            callingServlet();

    }

    private void callingServlet() {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost request = new HttpPost(servletUrl);
            UsernamePasswordCredentials creds = new UsernamePasswordCredentials("admin", "admin");
            request.addHeader(new BasicScheme().authenticate(creds, request, null));

            HttpResponse response = httpClient.execute(request);
            int responseCode = response.getStatusLine().getStatusCode();
            if (responseCode == 200) {
                BufferedReader in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                String inputLine;
                StringBuilder responseBody = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    responseBody.append(inputLine);
                }
                in.close();
                LOG.info("Servlet response: {}", responseBody.toString());
            } else {
                LOG.error("Failed to call servlet. Response code: {}", responseCode);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @ObjectClassDefinition(name = "Schedule Service Configuration", description = "Pass the Cron Expression for Schedule the Task")
    public @interface ScheduleConfigService {

        @AttributeDefinition(
                name = "Schedule the Service",
                type = AttributeType.STRING,
                description = "Expression for Schedule"
        )
        String Schedulee() default "";

        @AttributeDefinition(
                name = "Servlet URL",
                type = AttributeType.STRING,
                description = "URL of the servlet to call"
        )
        String servletUrl();
    }
}
