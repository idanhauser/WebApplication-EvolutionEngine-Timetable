package WebUI.Servlets;

import WebUI.Constants;
import WebUI.Utils.ServletUtils;
import WebUI.Utils.SessionUtils;
import WebUI.users.Client;
import WebUI.users.ClientsList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class getSpecificEnginePageServlet extends HttpServlet {

    private final String SINGLE_ENGINE_PAGE = "../SingleEnginePage/singleEngine.html";
    private final String USERS_ENGINE_URL = "../UsersHomePage/usersHome.html";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String userName = SessionUtils.getUsername(request);
        ClientsList clientList = ServletUtils.getClientList(getServletContext());
        Client client = clientList.getClient(userName);
        String problem = request.getParameter("engine");

        client.getEnginesList().forEach((e)->
        {
            if(e.getProblemName().equals(problem))
            {
                 request.getSession(true).setAttribute(Constants.ENGINE, e);
            }
        });

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
