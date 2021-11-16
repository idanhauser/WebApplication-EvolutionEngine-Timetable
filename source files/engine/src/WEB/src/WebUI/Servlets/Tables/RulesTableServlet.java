package WebUI.Servlets.Tables;

import WebUI.Constants;
import WebUI.Utils.EnginesList;
import WebUI.Utils.ServletUtils;
import WebUI.Utils.SessionUtils;
import com.google.gson.Gson;
import data.transfer.objects.RulesDTO;
import evolution.engine.EvolutionEngine;
import time.table.problem.Quintet;
import time.table.problem.configurations.rules.Rule;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "RulesTableServlet", urlPatterns = {"/RulesTable"})
public class RulesTableServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.setContentType("application/json");
        List<RulesDTO> rulesDtosList = new ArrayList<>();
        try (PrintWriter out = response.getWriter()) {
            Gson gson = new Gson();
            Map<Rule, Float> rulesGrade;
            EvolutionEngine<Quintet> engine = (EvolutionEngine<Quintet>) request.getSession().getAttribute(Constants.ENGINE);
            if (engine.getBestSolutionInAllGens() != null) {
                rulesGrade = engine.getBestSolutionInAllGens().getRulesGrade();
                rulesGrade.forEach((key, value) -> {
                    rulesDtosList.add(new RulesDTO(key.getType(), key.GetRuleId(), value));
                });
            } else {
                Map<Enum, Rule> rules = engine.getTimeTable().getRules();
                rules.forEach((key, value) -> {
                    rulesDtosList.add(new RulesDTO(value.GetType(), value.GetRuleId()));
                });
            }
            String json = gson.toJson(rulesDtosList);
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
