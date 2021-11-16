package WebUI.Servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//@WebServlet("/uploadJquery")
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 1024 * 1024 * 5, maxRequestSize = 1024 * 1024 * 5 * 5)
public class LoadBinFileServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect("fileupload/form.html");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        response.setContentType("text/html");
//        try {
//            ConcreteEngine engine = ServletUtils.getEngine(request.getServletContext());
//            ConcreteEngine tmp = new ConcreteEngine();
//            Part part = request.getPart("file");
//            InputStream inputStream = part.getInputStream();
//            ReadingFileInputObject readingFileInputObject =
//                    new ReadingFileInputObject(inputStream, tmp);
//            tmp = ServletUtils.getClient(request.getServletContext()).LoadDataFromFile(readingFileInputObject);
//            for(Stock stock : tmp.getStockList())
//                engine.getStockList().add(stock);
//            for(User user : tmp.getUsers().values())
//                engine.getUsersFromFile().put(user.getUserName(), user);
//            String message = "the file has been upload successfully";
//            response.getOutputStream().println(message);
//        }
//        catch (Exception e)
//        {
//            response.setStatus(401);
//            response.getOutputStream().println(e.getMessage());
//        }
    }

}
