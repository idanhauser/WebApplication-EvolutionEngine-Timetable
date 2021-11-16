package WebUI.Servlets;

import WebUI.Utils.ServletUtils;
import WebUI.Utils.SessionUtils;
import WebUI.users.Client;
import WebUI.users.ClientsList;
import evolution.engine.EvolutionEngine;
import time.table.problem.Quintet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static WebUI.Constants.ENGINE;

public class PauseEngineServlet extends HttpServlet {
    private void processRequest(HttpServletRequest request, HttpServletResponse response) {

        EvolutionEngine<Quintet> engine = (EvolutionEngine<Quintet>) request.getSession().getAttribute(ENGINE);
        Pause(engine);
    }
    public synchronized void Pause(EvolutionEngine engine) {
        engine.Pause();
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
