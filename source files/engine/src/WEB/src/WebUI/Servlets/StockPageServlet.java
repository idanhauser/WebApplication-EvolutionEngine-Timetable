package WebUI.Servlets;

import WebUI.Utils.SessionUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static WebUI.Constants.SYMBOL;

public class StockPageServlet extends HttpServlet {

    private final String STOCKPAGE_URL = "../SingleEnginePage/singleEngine.html";
    private final String USERS_STOCKS_URL = "../SingleEnginePage/singleEngine.html";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        String symbol = (String) request.getSession().getAttribute(SYMBOL);
        String userName = SessionUtils.getUsername(request);
//        if (symbol == null) {
//            response.setStatus(409);
//            if(ServletUtils.getEngine(getServletContext()).isAdmin(userName))
//                response.getOutputStream().println(USERS_STOCKS_URL_ADMIN);
//            else response.getOutputStream().println(USERS_STOCKS_URL);
//        } else {
//            try(PrintWriter out = response.getWriter()) {
//                ConcreteEngine engine = ServletUtils.getEngine(getServletContext());
//                Stock stock = engine.findStockBySymbol(symbol);
//                Gson gson = new Gson();
//                UsersStockData stockData = new UsersStockData(stock,
//                        engine.findUser(userName).findUserItem(symbol) == null ?
//                                0 : engine.findUser(userName).findUserItem(symbol).getAmount());
//                String json = gson.toJson(stockData);
//                out.println(json);
//                out.flush();
//            }
//            catch (Exception e)
//            {
//                response.setStatus(401);
//                response.getOutputStream().println(e.getMessage());
//            }
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
