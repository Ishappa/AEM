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
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletPaths;
import org.osgi.service.component.annotations.Component;

import javax.json.Json;
import javax.json.JsonObject;
import javax.servlet.Servlet;
import javax.servlet.annotation.HttpMethodConstraint;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Component(service = Servlet.class)
@SlingServletPaths("/bin/shureMicroFilter")
@HttpMethodConstraint(HttpConstants.METHOD_POST)

public class Shure_Microphones_Filter extends SlingAllMethodsServlet {
    @Override
    protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {
        String endPoint = "https://shureshop.in/graphql";
        String inParameterValue = request.getParameter("in");
        String sortAttribute = request.getParameter("sortAttribute");
        String sortDirection = request.getParameter("sortDirection");
        String minPrice = request.getParameter("minPrice");
        String maxPrice = request.getParameter("maxPrice");
        String color = request.getParameter("color");

       String graphqlQuery = buildGraphQLQuery(inParameterValue,
sortAttribute, sortDirection, minPrice, maxPrice, color);
        JsonObject jsonObject = Json.createObjectBuilder()
                .add("query", graphqlQuery )
                .add("operationName", "getProducts")
                .build();

        try(CloseableHttpClient httpClient = HttpClients.createDefault()){
            HttpPost httpPost = new HttpPost(endPoint);
            httpPost.setHeader("Content-Type","application/json");
            httpPost.setHeader("authority","shureshop.in");
            httpPost.setHeader("scheme","https");
            httpPost.setEntity(new StringEntity(jsonObject.toString()));
            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity entity = httpResponse.getEntity();

            if (entity!=null){
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent(), "UTF-8"))){
                StringBuilder builderResponse = new StringBuilder();
                String line;

                while ((line=reader.readLine())!=null){
                    builderResponse.append(line);
                }
                response.getWriter().write(builderResponse.toString());
                }
                finally {
                    EntityUtils.consume(entity);
                }
            }
        }
    }

//    private String buildGraphQlQuery(String inParameterValue, String sortAttribute,String sortDirection,String minPrice, String maxPrice, String color){
//
//        StringBuilder filterData = new StringBuilder();
//
//        // inParameterValue is present or not
//        if (inParameterValue!=null && !inParameterValue.isEmpty()){
//            filterData.append("{attribute:\"category_id\", condition: {in:[\"").append(inParameterValue).append("\"]}},");
//        }
//
//        //color is present or not
//        if (color!=null && !color.isEmpty()){
//            filterData.append("{attribute:\"color\", condition: {in:[\"").append(color).append("\"]}},");
//        }
//
//        //removing the comma
//        if (filterData.length()>0){
//            filterData.deleteCharAt(filterData.length()-1);
//        }
//
//        //final not selected any filet default it will take empty custome
//        String filterContent;
//        if (filterData.length()>0){
//            filterContent = "custom_filter: ["+filterData.toString()+"]";
//        }else {
//            filterContent = "custom_filter:[]";
//        }
//
//        //min and max price checking
//        if (minPrice!=null && !minPrice.isEmpty() && maxPrice!= null && !maxPrice.isEmpty()){
//            filterContent += ", min_price: { gteq: \"" + minPrice + "\" }, max_price: { lteq: \"" + maxPrice + "\" }";
//        }
//
//        String sortPart = (sortAttribute != null && !sortAttribute.isEmpty() && sortDirection!= null && !sortDirection.isEmpty()) ?"sort: { custom_sort: [ { attribute: \"" + sortAttribute + "\", direction: "+ sortDirection + " } ] },":"";
//
//        return "query getProducts {" +
//                " products(" +
//                " " + sortPart +
//                " filter: {" +
//                " category_id: { eq: \"9\" }," +
//                " " + filterContent +
//                " }," +
//                " pageSize: 30" + // Adding the pageSize parameter
//                " ) {" +
//                " total_count" +
//                " items {" +
//                " id" +
//                " sku" +
//                " name" +
//                " type_id" +
//                " stock_status" +
//                " special_price" +
//                " only_x_left_in_stock" +
//                " price {" +
//                " minimalPrice {" +
//                " amount {" +
//                " value" +
//                " currency" +
//                " }" +
//                " }" +
//                " }" +
//                " thumbnail {" +
//                " url" +
//                " path" +
//                " label" +
//                " }" +
//                " }" +
//                " }" +
//                "}";
//
//    }
private String buildGraphQLQuery(String inParameterValue, String
        sortAttribute, String sortDirection, String minPrice, String maxPrice, String
                                         colour) {
    StringBuilder filterPartBuilder = new StringBuilder();
// Check if inParameterValue is present
    if (inParameterValue != null && !inParameterValue.isEmpty()) {
        filterPartBuilder.append("{ attribute: \"category_id\", condition: { in: [\"").append(inParameterValue).append("\"] } },");
    }

// Check if colour is present
    if (colour != null && !colour.isEmpty()) {
        filterPartBuilder.append("{ attribute: \"color\", condition: { in: [\"").append(colour).append("\"] } },");
    }
// Remove trailing comma if any
    if (filterPartBuilder.length() > 0) {
        filterPartBuilder.deleteCharAt(filterPartBuilder.length() - 1);
    }
// Construct final filterPart
    String filterPart;
    if (filterPartBuilder.length() > 0) {
        filterPart = "custom_filter: [" + filterPartBuilder.toString() + "]";
    } else {
        filterPart = "custom_filter: []";
    }
// Check if both minPrice and maxPrice are present
    if (minPrice != null && !minPrice.isEmpty() && maxPrice != null &&
            !maxPrice.isEmpty()) {
        filterPart += ", min_price: { gteq: \"" + minPrice + "\" }, max_price: { lteq: \"" + maxPrice + "\" }";
    }
    String sortPart = (sortAttribute != null && !sortAttribute.isEmpty() &&
            sortDirection != null && !sortDirection.isEmpty()) ?

            "sort: { custom_sort: [ { attribute: \"" + sortAttribute + "\", direction: "

                    + sortDirection + " } ] }," :

            "";
    return "query getProducts {" +
            "  products(" +
            "    " + sortPart +
            "    filter: {" +
            "      category_id: { eq: \"9\" }," +
            "      " + filterPart +
            "    }," +
            "    pageSize: 30" +
            "  ) {" +
            "    total_count" +
            "    items {" +
            "      id" +
            "      sku" +
            "      name" +
            "      type_id" +
            "      stock_status" +
            "      special_price" +
            "      only_x_left_in_stock" +
            "      price {" +
            "        minimalPrice {" +
            "          amount {" +
            "            value" +
            "            currency" +
            "          }" +
            "        }" +
            "        regularPrice {" +
            "          amount {" +
            "            value" +
            "            currency" +
            "          }" +
            "        }" +
            "      }" +
            "      thumbnail {" +
            "        url" +
            "        path" +
            "        label" +
            "      }" +
            "    }" +
            "  }" +
            "}";
}
}
