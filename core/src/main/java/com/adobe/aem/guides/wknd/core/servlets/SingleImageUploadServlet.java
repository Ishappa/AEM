package com.adobe.aem.guides.wknd.core.servlets;

import com.day.cq.dam.api.Asset;
import com.day.cq.dam.api.AssetManager;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

@Component(service = Servlet.class, property = {
        Constants.SERVICE_DESCRIPTION + "=Upload the image to dam",
        "sling.servlet.methods=" + HttpConstants.METHOD_GET, "sling.servlet.paths=" + "/bin/SingleImageUploadServlet" })
public class SingleImageUploadServlet extends SlingSafeMethodsServlet {

    private static final long serialVersionUID = 1L;
    private static final Logger log = LoggerFactory.getLogger(SingleImageUploadServlet.class);

    protected void doGet(final SlingHttpServletRequest req,
                         final SlingHttpServletResponse resp) throws ServletException, IOException {


        InputStream is = null;
        String mimeType = "";
        String imgName = req.getParameter("imgName");
        String src = req.getParameter("src");

        com.adobe.granite.asset.api.AssetManager manager =
                                req.getResourceResolver().adaptTo(com.adobe.granite.asset.api.AssetManager.class);

        try {
           URL Url = new URL(src);
            URLConnection uCon = Url.openConnection();
            is = uCon.getInputStream();
            mimeType = uCon.getContentType();
            String fileExt = StringUtils.EMPTY;
            resp.setContentType("text/plain");

            fileExt = mimeType.replaceAll("image/", "");
            if (manager.assetExists("/content/dam/e-commerce/" + imgName + "." + fileExt)) {

                resp.getWriter().write(imgName + "." + fileExt+" is already exist please try to add different Image or give diff Name");
            }
            else {

                AssetManager assetManager = req.getResourceResolver().adaptTo(AssetManager.class);
                Asset imageAsset = assetManager.createAsset("/content/dam/e-commerce/" + imgName + "." + fileExt, is, mimeType, true);
                resp.setContentType("text/plain");
//                resp.getWriter().write("Image Uploaded = " + imageAsset.getName() + "  to this path =" + imageAsset.getPath());
                resp.getWriter().write(imgName + "." + fileExt+" is Uploaded");
            }
        }
        catch (Exception e) {
            log.error("error  occured while uploading the asset {}",e.getMessage());
        }
        finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                log.error("error  occured {}",e.getMessage());
            }
        }
    }

}
