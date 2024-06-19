package com.adobe.aem.guides.wknd.core.servlets;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletPaths;
import org.osgi.service.component.annotations.Component;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.annotation.HttpMethodConstraint;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Component(service = Servlet.class)
@SlingServletPaths(value = "/bin/shuredataMv5")
@HttpMethodConstraint(HttpConstants.METHOD_GET)
public class Shure_DataMv5_Page extends SlingSafeMethodsServlet {
    @Override
    protected void doGet(SlingHttpServletRequest req, SlingHttpServletResponse res) throws IOException {
        String endPoint = "https://shureshop.in/graphql";
//        String url_key ="mv5-digital-condenser-microphone";

//        String graphqlQuery = "{\n" +
//                "  \"query\": \"query getProducts { products(filter: { url_key: { eq: \\\"mv5-digital-condenser-microphone\\\" } }) { total_count items { id sku name type_id stock_status special_price only_x_left_in_stock price { minimalPrice { amount { value currency } } } thumbnail { url path label } } }}\"\n" +
//                "}";
        String graphqlQuery = "{\n" +
                "  \"query\": \"query getProducts1 { products(filter: { url_key: { eq: \\\"mv5-digital-condenser-microphone\\\" } }) { total_count items { name sku attributes { attribute_options { label value } } ... on ConfigurableProduct { variants { product { name price { minimalPrice { amount { value currency } } regularPrice { amount { value currency } } } thumbnail { url path label } short_description { html } media_gallery_entries{ id file position video_content{ video_url } } } } } } }}\"\n" +
                "}";



        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(endPoint);
        httpPost.setHeader("Content-Type", "application/json");
        httpPost.setHeader("authority", "shureshop.in");
        httpPost.setHeader("scheme", "https");
        httpPost.setEntity(new StringEntity(graphqlQuery));
        HttpResponse httpResponse = httpClient.execute(httpPost);
        HttpEntity entity = httpResponse.getEntity();

        if (entity != null) {

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent(), "UTF-8"))) {
                StringBuilder response = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                res.getWriter().write(String.valueOf(response));
            } finally {

                EntityUtils.consume(entity);
            }
        }
    }
}






//query getProducts($url_key:String!) {
//    products(filter: { url_key: { eq: $url_key } }) {
//        total_count
//        items {
//            id
//                    sku
//            name
//                    type_id
//            stock_status
//                    special_price
//            only_x_left_in_stock
//            price {
//                minimalPrice {
//                    amount {
//                        value
//                                currency
//                    }
//                }
//            }
//            thumbnail {
//                url
//                        path
//                label
//            }
//        }
//    }
//}


//"url_key":"mv5-digital-condenser-microphone"