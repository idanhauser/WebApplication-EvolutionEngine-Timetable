package WebUI.Servlets;

import WebUI.Utils.ServletUtils;
import WebUI.Utils.SessionUtils;
import WebUI.users.Client;
import WebUI.users.ClientsList;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.PriorityQueue;
import java.util.Queue;

public class UserMessagesServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        String userName = SessionUtils.getUsername(request);
        try (PrintWriter out = response.getWriter()) {
            ClientsList clientsList = ServletUtils.getClientList(getServletContext());
            Client user = clientsList.getClient(userName);
            Gson gson = new Gson();
            Queue<String> messages = new PriorityQueue<>();
            while(!user.getMessages().isEmpty())
                messages.add(user.getMessages().poll());
            String json = gson.toJson(messages);
            out.println(json);
            out.flush();
        } catch (Exception e) {
            response.setStatus(401);
            response.getOutputStream().println(e.getMessage());
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
