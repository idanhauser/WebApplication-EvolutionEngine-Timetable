package WebUI.Servlets;

import WebUI.Constants;
import WebUI.Utils.ServletUtils;
import WebUI.users.Client;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static WebUI.Constants.USERNAME;

public class LoginServlet extends HttpServlet {

        private final String USERS_ENGINE_URL = "../UsersHomePage/usersHome.html";
        private final String LOGIN_URL = "../LoginPage/login.html";
        private final String LOGIN_ERROR_URL = "/pages/LoginPage/loginError.jsp";

        protected void processRequest(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException {
            response.setContentType("text/html;charset=UTF-8");
            String usernameFromParameter = request.getParameter(USERNAME);

            if(ServletUtils.getClientList(getServletContext()).isExist(usernameFromParameter)) // if exist
            {
                response.getOutputStream().println("this user name is already exist");
                response.setStatus(401);
            }
            else
            {
                Client newClient = new Client(usernameFromParameter);
                request.getSession(true).setAttribute(Constants.USERNAME, usernameFromParameter);
                ServletUtils.getClientList(getServletContext()).addClient(newClient);
                response.getOutputStream().println(USERS_ENGINE_URL);
            }
        }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
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
