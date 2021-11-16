package WebUI.users;

import WebUI.Utils.ServletUtils;
import evolution.engine.EvolutionEngine;
import time.table.problem.LoadData;
import time.table.problem.Quintet;
import WebUI.Utils.SystemData;
import time.table.problem.configurations.EvolutionEngineInfo;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.io.Serializable;
import java.util.*;

public class Client implements Serializable {
    private String m_Username;
    private List<EvolutionEngine<Quintet>> m_EngineList;
    private Queue<String> m_Messages;
    private String m_initPop;
    private String m_crossover;
    private String m_cuttingPoints;
    private String m_selection;
    private String m_aspectOrientedType;
    private String m_FlippingComponent;
    private String m_probabilitySelection;
    private String m_ProbabilityMutation;
    private String m_Elitism;
    private String m_MaxTupples;
    private String m_TotalTupples;
    private String m_GenerationCondition;
    private String m_FitnessCondition;
    private String m_TimeCondition;
    private String m_FlippingMutation;
    private String m_SizerMutation;

    public Client(String userName) {
        this.m_Username = userName;
        m_EngineList = new ArrayList<>();
        this.m_Messages = new PriorityQueue<>();
        this.m_Messages.add("Init messages");
    }

    //ctor for tests
    public Client(String userName, int test) throws Exception {

        this.m_Username = userName;
        m_EngineList = new ArrayList<>();
        LoadData xmlFile = null;
        this.m_Messages = new PriorityQueue<>();


        //for test only:
        xmlFile = new LoadData("/Users/idanhauser/IdeaProjects/EvolutinaryTimeTable3/engine/src/time/table/problem/resources/ex3/EX3-big.xml");

        SystemData problemData = new SystemData(xmlFile);


   //     EvolutionEngineInfo engineData = new EvolutionEngineInfo(, "500", "DayTimeOriented", "4", "RouletteWheel", "Teacher", "Sizer", "Teacher", "0.3", "0.4", "2", "20", , , problemData.getRules());

       // EvolutionEngine<Quintet> engine = new EvolutionEngine<>(problemData.getFactory(), engineData, 1, problemData.getDTOEngine(), null, 1, 1, 1, 1, 1, "test_test_test", problemData.getTimeTable());

        System.out.println(test);
        //m_EngineList.add(engine);

    }

    //the real version:
    public void addNewEngine(InputStream XmlFile, HttpServletRequest request, ServletContext servletContext, String i_ProblemName) throws Exception {
        LoadData XML = new LoadData(XmlFile);
        SystemData problemData = new SystemData(XML);

        m_initPop = request.getParameter("initialPopulation");
        m_crossover = request.getParameter("crossover");
        m_cuttingPoints = request.getParameter("CuttingPoints");
        m_selection = request.getParameter("selection");
        if (!Objects.equals(request.getParameter("AspectOrientedType"), "")) {
            m_aspectOrientedType = request.getParameter("AspectOrientedType");
        } else {
            m_aspectOrientedType = "TEACHER";
        }

        if (!Objects.equals(request.getParameter("FlippingMutationComponent"), "")) {
            m_FlippingComponent = request.getParameter("FlippingMutationComponent");
        } else {
            m_FlippingComponent = "Day";
        }
        if (!Objects.equals(request.getParameter("probabilitySelection"), "")) {
            m_probabilitySelection = request.getParameter("probabilitySelection");
        } else {
            m_probabilitySelection = "0";
        }
        m_ProbabilityMutation = request.getParameter("probabilityMutation");
        m_Elitism = request.getParameter("Elitism");
        m_MaxTupples = request.getParameter("MaxTupples");
        m_TotalTupples = request.getParameter("TotalTupples");

        m_FlippingMutation = request.getParameter("FlippingMutationCB");
        m_SizerMutation = request.getParameter("SizerMutationCB");
        if (!Objects.equals(request.getParameter("GenerationCondition"), "0")) {
            m_GenerationCondition = request.getParameter("GenerationCondition");
        } else {
            m_GenerationCondition = String.valueOf(Integer.MAX_VALUE);
        }

        if (!Objects.equals(request.getParameter("FitnessCondition"), "0")) {
            m_FitnessCondition = request.getParameter("FitnessCondition");
            if (m_FitnessCondition.equals("")) {
                m_FitnessCondition = "100";
            }
        } else {
            m_FitnessCondition = "100";
        }
        if (!Objects.equals(request.getParameter("TimeCondition"), "0")) {
            m_TimeCondition = request.getParameter("TimeCondition");
        } else {
            m_TimeCondition = String.valueOf(Integer.MAX_VALUE);
        }


        i_ProblemName = m_EngineList.size()+ " " + i_ProblemName ;
        EvolutionEngineInfo engineData = new EvolutionEngineInfo(m_initPop, m_crossover, m_cuttingPoints, m_selection, m_aspectOrientedType, m_FlippingComponent, m_probabilitySelection, m_ProbabilityMutation, m_Elitism, m_MaxTupples, m_TotalTupples, m_SizerMutation, m_FlippingMutation, problemData.getRules());
        EvolutionEngine<Quintet> engine = new EvolutionEngine<>(problemData.getFactory(), engineData, problemData.getTimeTable().getGeneLength(), problemData.getDTOEngine(), null, Integer.parseInt(m_GenerationCondition), Integer.parseInt(m_FitnessCondition), Integer.parseInt(m_TimeCondition), 5, Integer.parseInt(m_TimeCondition), i_ProblemName, problemData.getTimeTable());
        m_EngineList.add(engine);

        //Adding engine to all engines list
        ServletUtils.getAllEnginesList(servletContext).addEngine(m_Username, engine);
    }

    //
    public String getUsername() {
        return m_Username;
    }

    public List<EvolutionEngine<Quintet>> getEnginesList() {
        return m_EngineList;
    }

    public void AddMessage(String message) {
        this.m_Messages.add(message);
    }

    public Queue<String> getMessages() {
        return m_Messages;
    }

    public void setMessages(Queue<String> messages) {
        this.m_Messages = messages;
    }

}
