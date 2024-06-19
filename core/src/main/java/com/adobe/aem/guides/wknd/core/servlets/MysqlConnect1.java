package com.adobe.aem.guides.wknd.core.servlets;

import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;
import java.sql.*;

@Component(service = { Servlet.class }, property = {
        Constants.SERVICE_DESCRIPTION + "=Simple MySQL Servlet",
        "sling.servlet.methods=" + HttpConstants.METHOD_GET,
        "sling.servlet.paths=" + "/bin/mysqlservlet"
})
public class MysqlConnect1 extends SlingAllMethodsServlet {

//    private static final Logger logger = LoggerFactory.getLogger(MysqlConnect1.class);
//    private static final String DB_URL = "jdbc:mysql://localhost:3306/aem";
//    private static final String USER = "root";
//    private static final String PASS = "root";
//
//    @Override
//    protected void doGet(final org.apache.sling.api.SlingHttpServletRequest req,
//                         final org.apache.sling.api.SlingHttpServletResponse response) throws ServletException, IOException {
//        Connection conn = null;
//        PreparedStatement stmt = null;
//        try {
//            Class.forName("com.mysql.jdbc.Driver");
//            conn = DriverManager.getConnection(DB_URL, USER, PASS);
//            String sql = "SELECT * FROM users";
//            stmt = conn.prepareStatement(sql);
//
//            ResultSet rs = stmt.executeQuery();
//            while (rs.next()) {
//                // Process ResultSet data
//                String data = rs.getString("name");
//                response.getWriter().write(data);
//            }
//            rs.close();
//        } catch (SQLException | ClassNotFoundException e) {
//            logger.error("Error while querying MySQL database", e);
//        } finally {
//            try {
//                if (stmt != null) stmt.close();
//                if (conn != null) conn.close();
//            } catch (SQLException e) {
//                logger.error("Error closing resources", e);
//            }
//        }
//    }
}
