package evolution.engine;

import data.transfer.objects.DTOEngineInformation;
import data.transfer.objects.GeneticPoolInformation;
import data.transfer.objects.UIAdapter;
import evolution.engine.conditions.FitnessCondition;
import evolution.engine.conditions.GenerationCondition;
import evolution.engine.conditions.TimeCondition;
import javafx.util.Pair;
import time.table.problem.configurations.rules.Rule;
import time.table.problem.objects.TimeTable;

import java.io.Serializable;
import java.util.*;

public class EvolutionEngine<T> implements Serializable, Runnable {


    private static final long serialVersionUID = 100L;
    private final String m_ProblemName;
    private final DTOEngineInformation<T> m_DTOEngine;
    private final EvolutionEngineInformation m_EngineInfo;
    private int m_maxFitness = 0;
    private TimeTable m_TimeTableInfo;
    private Individual bestSolutionInAllGens;
    private int m_ElitismCount;
    private Population<T> m_Population;
    private Map<Integer, Pair<Integer, Population>> m_OldPopulationList;
    private double m_Probability;
    private int m_Days;
    private int m_Hours;
    private List<Integer> m_maxFitnessList;
    private int m_SoftRules;
    private int m_HardRules;

    private boolean isPause = false;
    private boolean isRunning = false;
    private boolean EndFlag = false;

    private UIAdapter m_Adapter;
    private int m_ProcessesGenerationToShow;

    //Exit Conditions:
    private int m_GenerationCount;

    //Exit Conditions:
    private long m_MinutesToStop;

    private FitnessCondition m_fitnessCondition;
    private GenerationCondition m_generationCondition;
    private TimeCondition m_timeCondition;

    //for EX1
    public EvolutionEngine(Factory<T> i_Factory, EvolutionEngineInformation i_EngineInfo, int i_geneListLength, DTOEngineInformation<T> i_DTOEngine, int i_Days, int i_Hours, String i_ProblemName) throws InstantiationException, IllegalAccessException {
        m_ProblemName = i_ProblemName;
        m_Population = new Population<>(i_Factory, i_EngineInfo, i_geneListLength);
        m_OldPopulationList = new TreeMap<>();
        m_EngineInfo = i_EngineInfo;
        m_GenerationCount = 0;
        m_DTOEngine = i_DTOEngine;
        m_ElitismCount = m_EngineInfo.getSelection().getElitismCount();
    }

    //for EX2/3
    public EvolutionEngine(Factory<T> i_Factory, EvolutionEngineInformation i_EngineInfo, int i_geneListLength, DTOEngineInformation<T> i_DTOEngine, UIAdapter i_Adapter,
                           int i_ExitGeneration, int i_ExitFitness, int i_ExitTime, int i_ProcessesGenerationToShow, long i_MinutesToStop, String i_ProblemName, TimeTable i_TimeTableInfo) {
        m_Population = new Population<>(i_Factory, i_EngineInfo, i_geneListLength);
        m_ProblemName = i_ProblemName;
        m_TimeTableInfo = i_TimeTableInfo;
        m_OldPopulationList = new TreeMap<>();
        m_EngineInfo = i_EngineInfo;
        m_Days = i_TimeTableInfo.getDays();
        m_Hours = i_TimeTableInfo.getHours();
        m_GenerationCount = 0;

        m_ProcessesGenerationToShow = i_ProcessesGenerationToShow;
        m_generationCondition = new GenerationCondition(i_ExitGeneration);
        m_fitnessCondition = new FitnessCondition(i_ExitFitness);
        m_timeCondition = new TimeCondition(i_ExitTime);
        m_ElitismCount = m_EngineInfo.getSelection().getElitismCount();

        if (m_EngineInfo.getMutations().get(0) != null) {
            m_Probability = m_EngineInfo.getMutations().get(0).getProbability();
        }
        //for Ex2:
        m_Adapter = i_Adapter;
        //For Ex3:
        m_maxFitnessList=new ArrayList<>();
        m_maxFitnessList.add(0);
        m_TimeTableInfo = i_TimeTableInfo;
        CountRules(m_EngineInfo.GetRules());
        if (i_DTOEngine == null) {
            m_DTOEngine = new DTOEngineInformation<T>(m_EngineInfo.getInitialPopulation(), m_EngineInfo.getCrossover(), m_EngineInfo.getSelection(), m_EngineInfo.getMutations(), m_Population.getBestFitnessScore(), m_Population.getBestFitnessScore());
        } else {
            m_DTOEngine = i_DTOEngine;
        }
    }

    public EvolutionEngineInformation getEngineInfo() {
        return m_EngineInfo;
    }

    public int getDays() {
        return m_Days;
    }

    public int getHours() {
        return m_Hours;
    }

    public int getSoftRules() {
        return m_SoftRules;
    }

    public int getHardRules() {
        return m_HardRules;
    }

    public TimeTable getTimeTable() {
        return m_TimeTableInfo;
    }

    public Population<T> getPopulation() {
        return m_Population;
    }

    private void CountRules(Map<Enum, Rule> i_Rules) {
        m_HardRules = 0;
        m_SoftRules = 0;

        for (Rule rule : i_Rules.values()) {
            if (rule.getType() == Rule.RuleTypeEnum.Soft) {
                m_SoftRules++;
            } else {
                m_HardRules++;
            }
        }
    }

    public Map<Integer, Pair<Integer, Population>> getOldPopulationList() {
        return m_OldPopulationList;
    }

    public List<Integer> getMaxFitnessList() {
        return m_maxFitnessList;
    }

    public void run() {

        m_timeCondition.StartTimer();
        isRunning = true;

        try {
            m_Population.calculateFitness();
        } catch (Exception e) {
            System.out.println("Could not continue with the process: " + e.getMessage());
        }

        Individual bestSolutionInCurrentGen = m_Population.getFittestScore();
        bestSolutionInAllGens = m_Population.getFittestScore();

        m_DTOEngine.setBestResult(bestSolutionInAllGens.getListOfGenes());
        //m_DTOEngine.setBestFitness(bestSolutionInAllGens.getFitness());
        m_DTOEngine.setBestFitness(0);

        //UpdateAdapter();
        int bestGen = 0; //todo delete?

        while (ToContinue()) {
            isPause();
            m_DTOEngine.setGenerationCount(m_GenerationCount);
            ++m_GenerationCount;
            if (m_GenerationCount % m_ProcessesGenerationToShow == 0) {
                m_OldPopulationList.put(m_GenerationCount, new Pair<>(m_GenerationCount, m_Population));
                m_DTOEngine.setOldPopulationList(m_OldPopulationList);
                m_maxFitnessList.add(m_maxFitness);
                //System.out.println("DEBUGGER: gen: " + m_GenerationCount + " fitness: " + m_Population.getBestFitnessScore());
            }
            try {
                //Selection:
                m_Population.copyAndSetIndividualList(m_EngineInfo.getSelection().doSelection(m_Population.getIndividuals()));
                //CrossOver:
                m_Population.setIndividuals(m_EngineInfo.getCrossover().runCrossover(m_Population.getIndividuals(),
                        m_EngineInfo.getInitialPopulation() - m_EngineInfo.getSelection().getElitismCount()));
                //Mutation
                m_EngineInfo.getMutations().forEach((mutation) -> mutation.doMutation(m_Population.getIndividuals(), m_Days, m_Hours));
                //Adding back the elitism solutions.
                if (m_EngineInfo.getSelection().getElitismCount() > 0) {
                    m_Population.getIndividuals().addAll(m_EngineInfo.getSelection().get_ElitismList());
                }
            } catch (Exception ex) {
               // System.out.println("There was some problem in EvolutionEngine: " + ex.getMessage());
            }
            try {
                m_Population.calculateFitness();
            } catch (Exception e) {
                System.out.println("Could not continue with the process: " + e.getMessage());
            }
            bestSolutionInCurrentGen = m_Population.getFittestScore();

            if (bestSolutionInCurrentGen.getFitness() > bestSolutionInAllGens.getFitness()) {


                bestGen = m_GenerationCount + 1;
                bestSolutionInAllGens = bestSolutionInCurrentGen;
                m_DTOEngine.setBestFitness(bestSolutionInCurrentGen.getFitness());
                m_DTOEngine.setBestFitnessint(m_DTOEngine.getBestFitness().get());
                m_DTOEngine.setBestResult(bestSolutionInAllGens.getListOfGenes());
                m_DTOEngine.setBestGen(bestGen);


                m_maxFitness = Math.max(m_DTOEngine.getBestFitness().get(), bestSolutionInCurrentGen.getFitness());
                m_maxFitness = Math.max(m_maxFitness, bestSolutionInAllGens.getFitness());
                m_maxFitnessList.add(m_maxFitness);

            }
            m_maxFitness = Math.max(m_maxFitness, bestSolutionInCurrentGen.getFitness());

            // UpdateAdapter();
        }
        isRunning = false;
        EndFlag = true;
        //m_Adapter.onFinish();
    }

    public Individual getBestSolutionInAllGens() {
        return bestSolutionInAllGens;
    }

    private void isPause() {
        if (isPause) {
            synchronized (this) {
                try {
                    if (m_timeCondition.isTimeConditionIsOn()) {
                        m_timeCondition.pauseTimer();
                    }
                    isRunning = false;
                    this.wait();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void Pause() {
        isPause = true;
    }

    public void Resume() {
        synchronized (this) {
            isPause = false;
            isRunning = true;
            if (m_timeCondition.isTimeConditionIsOn()) {
                m_timeCondition.resumeTimer();
            }
            notifyAll();
        }
    }

    private void UpdateAdapter() {
        //   m_Adapter.UpdatePopulationAndFitness(m_OldPopulationList.size(), bestSolutionInAllGens.getFitness());
        //m_Adapter.UpdateProgressbar(m_GenerationCount, bestSolutionInAllGens.getFitness(), m_timeCondition.getDuration().getSeconds() / 60f);
    }

    private boolean ToContinue() {
        return !m_generationCondition.isEnded(m_GenerationCount) && !m_fitnessCondition.isEnded(bestSolutionInAllGens.getFitness()) && !m_timeCondition.isEnded();
    }

    public GeneticPoolInformation<T> requestGeneticsPool() throws Exception {
        GeneticPoolInformation<T> geneticPoolDto = new GeneticPoolInformation<>();
        if (m_OldPopulationList.size() != 0) {
            geneticPoolDto.setGensToShow(m_ProcessesGenerationToShow);
            geneticPoolDto.setHistoryOfGenerations(m_OldPopulationList);
        } else {
            throw new Exception("There is no data, You have to run the engine or load the data first.");
        }

        return geneticPoolDto;
    }


    public void reset() {
        m_OldPopulationList = new TreeMap<>();
        m_GenerationCount = 0;
        m_Population.reset();
        isPause = false;
        isRunning = false;
        m_DTOEngine.setGenerationCount(0);
        m_DTOEngine.setBestFitnessint(0);
    }

    public int getMaxFitness() {
        return m_maxFitness;
    }

    public long getGenerationCount() {
        return m_GenerationCount;
    }

    public void setConditions(int i_Generation, int i_Fitness, float i_Time) {
        m_generationCondition.setExitGeneration(i_Generation);
        m_fitnessCondition.setExitFitness(i_Fitness);
        m_timeCondition.setExitTime(i_Time);
    }

    public int getElitismCount() {
        return m_EngineInfo.getSelection().getElitismCount();
    }

    public void setElitismCount(String i_Elitism, int i_PopulationSize) {
        int elitismInt = Integer.parseInt(i_Elitism);
        if (elitismInt < 0 || elitismInt >= i_PopulationSize) {
            throw new IllegalArgumentException("Elite count must be non-negative and less than population size.");
        } else {
            m_ElitismCount = elitismInt;
            m_EngineInfo.getSelection().setElitismCount(String.valueOf(elitismInt), i_PopulationSize);
        }
    }

    public DTOEngineInformation<T> getDTOEngine() {
        return m_DTOEngine;
    }

//    public void setProbability(double i_Probability) {
//        if (i_Probability < 0 || i_Probability > 1) {
//            throw new IllegalArgumentException("The probability must be range of 0 to 1");
//        } else {
//            m_Probability = i_Probability;
//            m_EngineInfo.getMutations().forEach(mutation -> mutation.setProbability(m_Probability));
//        }
//    }


    public void setPredefinedTournamentEqualizer(float i_Probability) {
        if (i_Probability < 0 || i_Probability > 1) {
            throw new IllegalArgumentException("Predefined tournament equalizer should be between 0 to 1");
        } else {
            m_EngineInfo.getSelection().setPredefinedTournamentEqualizer(i_Probability);
        }
    }

    public String getProblemName() {
        return m_ProblemName;
    }

    public Boolean getIsPause() {
        return isPause;
    }

    public Boolean getIsRunning() {
        return isRunning;
    }

    public FitnessCondition getFitnessCondition() {
        return m_fitnessCondition;
    }

    public boolean EndFlagStatus() {
        boolean res = EndFlag;
        EndFlag = false;
        return res;
    }

    public GenerationCondition getGenerationCondition() {
        return m_generationCondition;
    }

    public TimeCondition getTimeCondition() {
        return m_timeCondition;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EvolutionEngine)) return false;
        EvolutionEngine<?> that = (EvolutionEngine<?>) o;
        return m_ElitismCount == that.m_ElitismCount && Double.compare(that.m_Probability, m_Probability) == 0 && m_Days == that.m_Days && m_Hours == that.m_Hours && m_SoftRules == that.m_SoftRules && m_HardRules == that.m_HardRules && isPause == that.isPause && m_ProcessesGenerationToShow == that.m_ProcessesGenerationToShow && m_GenerationCount == that.m_GenerationCount && m_MinutesToStop == that.m_MinutesToStop && m_ProblemName.equals(that.m_ProblemName) && m_DTOEngine.equals(that.m_DTOEngine) && m_EngineInfo.equals(that.m_EngineInfo) && m_TimeTableInfo.equals(that.m_TimeTableInfo) && bestSolutionInAllGens.equals(that.bestSolutionInAllGens) && m_Population.equals(that.m_Population) && m_OldPopulationList.equals(that.m_OldPopulationList) && m_Adapter.equals(that.m_Adapter) && m_fitnessCondition.equals(that.m_fitnessCondition) && m_generationCondition.equals(that.m_generationCondition) && m_timeCondition.equals(that.m_timeCondition);
    }

    @Override
    public int hashCode() {
        return Objects.hash(m_ProblemName, m_DTOEngine, m_EngineInfo, m_TimeTableInfo, bestSolutionInAllGens, m_ElitismCount, m_Population, m_OldPopulationList, m_Probability, m_Days, m_Hours, m_SoftRules, m_HardRules, isPause, m_Adapter, m_ProcessesGenerationToShow, m_GenerationCount, m_MinutesToStop, m_fitnessCondition, m_generationCondition, m_timeCondition);
    }
}



