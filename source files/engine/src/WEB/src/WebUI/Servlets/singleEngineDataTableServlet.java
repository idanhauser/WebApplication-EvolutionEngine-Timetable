package WebUI.Servlets;

import WebUI.Utils.SessionUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class /*getSpecificStockPageServlet*/singleEngineDataTableServlet extends HttpServlet {

    private final String SINGLE_STOCK_PAGE = "../SingleEnginePage/singleEngine.html";
    private final String USERS_STOCKS_URL = "../UsersHomePage/singleEngine.html";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String userName = SessionUtils.getUsername(request);
        //ConcreteEngine engine = ServletUtils.getEngine(getServletContext());

//        try {
//            String symbol = request.getParameter("stocksymbol");
//            request.getSession().setAttribute(SYMBOL, symbol);
//            if(engine.isAdmin(userName))
//                response.sendRedirect(SINGLE_STOCK_PAGE_ADMIN);
//            else response.sendRedirect(SINGLE_STOCK_PAGE);
//        }
//        catch (Exception e)
//        {
//            if(engine.isAdmin(userName))
//                response.sendRedirect(USERS_STOCKS_URL_ADMIN);
//            else
//                response.sendRedirect(USERS_STOCKS_URL);
//            response.setStatus(401);
//            response.getOutputStream().println(e.getMessage());
//        }
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
