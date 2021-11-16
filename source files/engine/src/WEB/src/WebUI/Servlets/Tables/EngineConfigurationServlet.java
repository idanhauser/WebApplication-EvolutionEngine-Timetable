package WebUI.Servlets.Tables;

import WebUI.Constants;
import com.google.gson.Gson;
import data.transfer.objects.DTOEngineInformation;
import data.transfer.objects.EngineParametersDTO;
import evolution.engine.EvolutionEngine;
import evolution.engine.EvolutionEngineInformation;
import time.table.problem.Quintet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "EngineConfigurationServlet", urlPatterns = {"/EngineConfiguration"})
public class EngineConfigurationServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.setContentType("application/json");
        try (PrintWriter out = response.getWriter()) {
            Gson gson = new Gson();
            EvolutionEngine<Quintet> engine = (EvolutionEngine<Quintet>) request.getSession().getAttribute(Constants.ENGINE);
            EvolutionEngineInformation engineDto = engine.getEngineInfo();
            EngineParametersDTO engineParametersDTO;
            engineParametersDTO = new EngineParametersDTO(engineDto.getCrossover(), engineDto.getSelection(), engineDto.getMutations());
            String json = gson.toJson(engineParametersDTO);
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
