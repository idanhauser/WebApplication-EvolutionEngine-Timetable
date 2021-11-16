package WebUI.Servlets;

import evolution.engine.EvolutionEngine;
import time.table.problem.Quintet;
import time.table.problem.configurations.Crossover;
import time.table.problem.configurations.Selection;
import time.table.problem.configurations.mutation.Mutation;
import time.table.problem.configurations.mutation.MutationEType;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static WebUI.Constants.ENGINE;
import static WebUI.Constants.UNCHANGED;

public class ChangeEngineConfigurationServlet extends HttpServlet {


    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        try {
            EvolutionEngine<Quintet> engine = (EvolutionEngine<Quintet>) request.getSession().getAttribute(ENGINE);

            String crossover = UNCHANGED;
            String cuttingPoints = UNCHANGED;
            String selection = UNCHANGED;
            String aspectOrientedType = UNCHANGED;
            String flippingComponent = UNCHANGED;
            String probabilityMutation = UNCHANGED;
            String maxTupples = UNCHANGED;
            String totalTupples = UNCHANGED;
            String probabilitySelection = UNCHANGED;
            String elitism = UNCHANGED;
            // String elitismCheckbox = request.getParameter("elitismCB");
            //  String selectionCheckbox = request.getParameter("selectionCB");
            // String crossoverCheckbox = request.getParameter("crossoverCB");
            //String mutationsCheckbox = request.getParameter("mutationsCB");
            String sizerMutationCheckBox = request.getParameter("SizerMutationCB");
            String flippingMutationCheckBox = request.getParameter("FlippingMutationCB");

       /*     if (elitismCheckbox == null && selectionCheckbox == null && crossoverCheckbox == null && mutationsCheckbox == null) {
                throw new IOException("You have not changed any of the parameters.");
            }*/
         if (flippingMutationCheckBox == null && sizerMutationCheckBox == null) {
                throw new IOException("You have to choose at least one mutation.");
            }

            //Changing Elitism
            //if (elitismCheckbox != null) {
            if ((request.getParameter("Elitism") != null) && (!Objects.equals(request.getParameter("Elitism"), ""))) {
                elitism = request.getParameter("Elitism");
                ChangeElitismParameter(engine, elitism);

            } else {
                throw new IllegalArgumentException("User wanted to change Elitism's parameter, but didn't send any new parameters.");
            }

//            }


            //Changing Crossover
            //  if (crossoverCheckbox != null) {
            if ((request.getParameter("crossover") != null) && (!Objects.equals(request.getParameter("crossover"), ""))) {
                crossover = request.getParameter("crossover");
            }
            if ((request.getParameter("CuttingPoints") != null) && (!Objects.equals(request.getParameter("CuttingPoints"), ""))) {
                cuttingPoints = request.getParameter("CuttingPoints");
            }
            if ((request.getParameter("AspectOrientedType") != null) && (!Objects.equals(request.getParameter("AspectOrientedType"), ""))) {
                aspectOrientedType = request.getParameter("AspectOrientedType");
            }
            if (crossover.equals(UNCHANGED) && cuttingPoints.equals(UNCHANGED) && aspectOrientedType.equals(UNCHANGED)) {
                throw new IllegalArgumentException("User wanted to change Crossover's parameters, but didn't send any new parameters.");
            } else {
                ChangeCrossoverParameters(engine, crossover, cuttingPoints, aspectOrientedType);
            }
            //   }

            //Changing Selection
            //   if (selectionCheckbox != null) {
            if (!Objects.equals(request.getParameter("selection"), "")) {
                selection = request.getParameter("selection");
            }
            if ((request.getParameter("probabilitySelection") != null)&&(!Objects.equals(request.getParameter("probabilitySelection"), ""))) {
                probabilitySelection = request.getParameter("probabilitySelection");
            }
            if (selection.equals(UNCHANGED) && probabilitySelection.equals(UNCHANGED)) {
                throw new IllegalArgumentException("User wanted to change Selection's parameters, but didn't send any new parameters.");
            } else {
                ChangeSelectionParameters(engine, selection, probabilitySelection);

            }
            //   }


            //Changing Mutation
            Boolean sizerMutationChecked = false;
            Boolean flippingMutationChecked = false;
            //  if (mutationsCheckbox != null) {
            //sizer mutation
            if (sizerMutationCheckBox != null) {
                sizerMutationChecked = true;
                if (!Objects.equals(request.getParameter("TotalTupples"), "")) {
                    totalTupples = request.getParameter("TotalTupples");
                }
                if (!Objects.equals(request.getParameter("probabilityMutation"), "")) {
                    probabilityMutation = request.getParameter("probabilityMutation");
                }
            }

            //flipping mutation
            if (flippingMutationCheckBox != null) {
                flippingMutationChecked = true;
                if (!Objects.equals(request.getParameter("FlippingMutationComponent"), "")) {
                    flippingComponent = request.getParameter("FlippingMutationComponent");
                }

                if (!Objects.equals(request.getParameter("MaxTupples"), "")) {
                    maxTupples = request.getParameter("MaxTupples");
                }
                if (!Objects.equals(request.getParameter("probabilityMutation"), "")) {
                    probabilityMutation = request.getParameter("probabilityMutation");
                }
            }

            if (sizerMutationCheckBox == null && flippingMutationCheckBox == null) {
                throw new IllegalArgumentException("User wanted to change Mutation's parameters, but didn't send any new parameters.");
            }

            if (flippingComponent.equals(UNCHANGED) && probabilityMutation.equals(UNCHANGED) && maxTupples.equals(UNCHANGED) && totalTupples.equals(UNCHANGED)) {
                throw new IllegalArgumentException("User wanted to change Mutation's parameters, but didn't send any new parameters.");
            }
            ChangeMutationParameter(engine, sizerMutationChecked, flippingMutationChecked, flippingComponent, probabilityMutation, maxTupples, totalTupples);
            //  }

            response.setStatus(200);
            String message = "The new configuration was set successfully!";
            response.getOutputStream().println(message);
        } catch (Exception e) {
            response.getOutputStream().println(e.getMessage());
            response.setStatus(401);
        }

    }


    private void ChangeMutationParameter(EvolutionEngine<Quintet> io_Engine, Boolean i_SizerMutationChecked, Boolean i_FlippingMutationChecked, String i_FlippingComponent, String i_ProbabilityMutation, String i_MaxTupples, String i_TotalTupples) {
        List<Mutation> mutationsList = io_Engine.getEngineInfo().getMutations();
        List<Mutation> newMutationList = new ArrayList<>();
        Mutation selectedMutation;

        if (i_SizerMutationChecked) {
            for (int i = 0; i < mutationsList.size(); i++) {
                if (mutationsList.get(i).getEnumType() == MutationEType.Sizer) {
                    if (i_TotalTupples.equals(UNCHANGED))
                        i_TotalTupples = String.valueOf(mutationsList.get(i).getTupples());
                    if (i_ProbabilityMutation.equals(UNCHANGED))
                        i_ProbabilityMutation = String.valueOf(mutationsList.get(i).getProbability());
                }
            }
            newMutationList.add(new Mutation(MutationEType.Sizer, i_TotalTupples, "D", i_ProbabilityMutation));
        }
        if (i_FlippingMutationChecked) {
            for (int i = 0; i < mutationsList.size(); i++) {
                if (mutationsList.get(i).getEnumType() == MutationEType.Flipping) {
                    if (i_MaxTupples.equals(UNCHANGED))
                        i_MaxTupples = String.valueOf(mutationsList.get(i).getTupples());
                    if (i_FlippingComponent.equals(UNCHANGED))
                        i_FlippingComponent = String.valueOf(mutationsList.get(i).getComponent());
                    if (i_ProbabilityMutation.equals(UNCHANGED))
                        i_ProbabilityMutation = String.valueOf(mutationsList.get(i).getProbability());
                }
            }
            newMutationList.add(new Mutation(MutationEType.Flipping, i_MaxTupples, String.valueOf(i_FlippingComponent.charAt(0)), i_ProbabilityMutation));
        }

        io_Engine.getEngineInfo().setMutations(newMutationList);
    }


    private void ChangeElitismParameter(EvolutionEngine<Quintet> io_Engine, String i_Elitism) {
        Selection currentSelection = io_Engine.getEngineInfo().getSelection();
        if (!i_Elitism.equals(UNCHANGED)) {
            currentSelection.setElitismCount(i_Elitism, io_Engine.getEngineInfo().getInitialPopulation());
        }
    }

    private void ChangeSelectionParameters(EvolutionEngine<Quintet> io_Engine, String i_Selection, String i_ProbabilitySelection) {
        Selection currentSelection = io_Engine.getEngineInfo().getSelection();


        if (i_Selection.equals(UNCHANGED))
            i_Selection = String.valueOf(currentSelection.getEnumSelectType());
        if (i_ProbabilitySelection.equals(UNCHANGED))
            i_ProbabilitySelection = String.valueOf(currentSelection.getTopPercent());


        Selection newSelection = new Selection(io_Engine.getEngineInfo().getInitialPopulation(), i_Selection, String.valueOf(currentSelection.getElitismCount()), i_ProbabilitySelection);
        io_Engine.getEngineInfo().setSelection(newSelection);


    }

    private void ChangeCrossoverParameters(EvolutionEngine<Quintet> io_Engine, String i_Crossover, String
            i_CuttingPoints, String i_AspectOrientedType) throws Exception {

        Crossover currentCrossover = io_Engine.getEngineInfo().getCrossover();

        if (i_Crossover.equals(UNCHANGED))
            i_Crossover = String.valueOf(currentCrossover.getEnumCrossoverType());
        if (i_CuttingPoints.equals(UNCHANGED))
            i_CuttingPoints = String.valueOf(currentCrossover.getCuttingPoint());
        if (i_AspectOrientedType.equals(UNCHANGED)) {
            if (currentCrossover.getOrientationType() != null) {
                i_AspectOrientedType = String.valueOf(currentCrossover.getOrientationType());
            } else {
                i_AspectOrientedType = "TEACHER";//not for real.
            }
        }
        Crossover newCrossover = new Crossover(i_CuttingPoints, i_Crossover, i_AspectOrientedType);
        io_Engine.getEngineInfo().setCrossover(newCrossover);


      /*  if (!i_CuttingPoints.equals(UNCHANGED) && !i_Crossover.equals(UNCHANGED)) {
            i_AspectOrientedType = currentCrossover.getOrientationType();
            Crossover newCrossover = new Crossover(i_CuttingPoints, i_Crossover, i_AspectOrientedType);
            io_Engine.getEngineInfo().setCrossover(newCrossover);
        }
        if (!i_CuttingPoints.equals(UNCHANGED)) {
            currentCrossover.setCuttingPoint(Integer.parseInt(i_CuttingPoints));
        }
        if (i_Crossover.equals(UNCHANGED) && !i_AspectOrientedType.equals(UNCHANGED)) {
            if (io_Engine.getEngineInfo().getCrossover().getEnumCrossoverType() == Crossover.type.AspectOriented) {
                throw new IllegalArgumentException("Crossover error: You can't choose other aspect oriented type(Teacher/Class) because crossover right now is on DayTimeOriented.");
            }
        }*/
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
