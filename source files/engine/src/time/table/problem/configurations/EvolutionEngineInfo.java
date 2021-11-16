package time.table.problem.configurations;

import evolution.engine.EvolutionEngineInformation;
import time.table.problem.configurations.mutation.Mutation;
import time.table.problem.configurations.mutation.MutationEType;
import time.table.problem.configurations.rules.Rule;
import time.table.problem.configurations.rules.RuleIdEnum;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EvolutionEngineInfo implements EvolutionEngineInformation, Serializable {
    private static final long serialVersionUID = 100L;
    private int m_InitialPopulation;
    private Crossover m_Crossover;
    private Selection m_Selection;
    private List<Mutation> m_Mutations;

    private Map<Enum, Rule> m_Rules;

//for ex1/ex2
    /*public EvolutionEngineInfo(ETTEvolutionEngine i_EvolutionEngine, List<Mutation> i_Mutations, Map<Enum, Rule> i_Rules) throws Exception {
        m_Rules = i_Rules;
        m_Mutations = i_Mutations;
        m_InitialPopulation = i_EvolutionEngine.getETTInitialPopulation().getSize();
        m_Crossover = new Crossover(i_EvolutionEngine.getETTCrossover());
        m_Selection = new Selection(i_EvolutionEngine.getETTSelection(), m_InitialPopulation);
    }*/

    //for ex3
    public EvolutionEngineInfo(String i_InitPop, String i_Crossover, String i_CuttingPoints, String i_Selection, String i_AspectOrientedType, String i_FlippingComponent, String i_ProbabilitySelection, String i_ProbabilityMutation, String i_Elitism, String i_MaxTupples, String i_TotalTupples, String i_SizerMutation, String i_FlippingMutation, Map<Enum, Rule> i_Rules)
            throws Exception {
        m_Mutations = new ArrayList<>();
        m_Rules = i_Rules;
        m_InitialPopulation = Integer.parseInt(i_InitPop);
        if (i_SizerMutation.equals("0")) {
            m_Mutations.add(new Mutation(MutationEType.Sizer, i_TotalTupples, "D", i_ProbabilityMutation));
        }
        if (i_FlippingMutation.equals("0")) {
            m_Mutations.add(new Mutation(MutationEType.Flipping, i_MaxTupples, String.valueOf(i_FlippingComponent.charAt(0)), i_ProbabilityMutation));
        }
        m_Crossover = new Crossover(i_CuttingPoints, i_Crossover, i_AspectOrientedType);
        m_Selection = new Selection(m_InitialPopulation, i_Selection, i_Elitism, i_ProbabilitySelection);
    }


    public Selection getSelection() {
        return m_Selection;
    }

    public void setSelection(Selection i_Selection) {
        this.m_Selection = i_Selection;
    }

    public Crossover getCrossover() {
        return m_Crossover;
    }

    public void setCrossover(Crossover i_Crossover) {
        this.m_Crossover = i_Crossover;
    }

    public int getInitialPopulation() {
        return m_InitialPopulation;
    }

    public List<Mutation> getMutations() {
        return m_Mutations;
    }

    @Override
    public void setMutations(List<Mutation> i_newMutations) {
        this.m_Mutations = i_newMutations;
    }

    public Map<Enum, Rule> GetRules() {
        return m_Rules;
    }

    @Override
    public int getMaxEval() {
        return RuleIdEnum.getMaxEval();
    }
}
