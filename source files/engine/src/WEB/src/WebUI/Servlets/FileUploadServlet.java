package WebUI.Servlets;/*
package WebUI.Servlets;

//taken from: http://www.servletworld.com/servlet-tutorials/servlet3/multipartconfig-file-upload-example.html
// and http://docs.oracle.com/javaee/6/tutorial/doc/glraq.html

import WebUI.Utils.ServletUtils;
import WebUI.Utils.SessionUtils;
import WebUI.users.Client;
import WebUI.users.ClientsList;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

@WebServlet("/uploadJquery")
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 1024 * 1024 * 5, maxRequestSize = 1024 * 1024 * 5 * 5)
public class FileUploadServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect("fileupload/form.html");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html");
        try {
              Client client;
              Part part = request.getPart("fake-key-1");
           InputStream inputStream = part.getInputStream();
            ClientsList clientList = ServletUtils.getClientList(getServletContext());
            client = clientList.getClient(SessionUtils.getUsername(request));
            client.addNewEngine(inputStream, request);
           //            User user = engine.findUser(username);
//            ReadingANewFileInputObejct readingANewFileInputObejct =
//                    new ReadingANewFileInputObejct(inputStream, engine, user);
//            ServletUtils.getClient(request.getServletContext()).ReadingANewFile(readingANewFileInputObejct);
//            String message = "the file has been upload successfully";
//            response.getOutputStream().println(message);
        }
        catch (Exception e)
        {
            response.setStatus(401);
            response.getOutputStream().println(e.getMessage());
        }
    }


    private String readFromInputStream(InputStream inputStream) {
        return new Scanner(inputStream).useDelimiter("\\Z").next();
    }
}*/
