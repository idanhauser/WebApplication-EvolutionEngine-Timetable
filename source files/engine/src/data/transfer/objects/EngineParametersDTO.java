package data.transfer.objects;

import time.table.problem.configurations.Crossover;
import time.table.problem.configurations.Selection;
import time.table.problem.configurations.mutation.Mutation;

import java.util.List;

public class EngineParametersDTO {
    private Crossover m_Crossover;
    private Selection m_Selection;
    private List<Mutation> m_Mutations;


    public EngineParametersDTO(Crossover m_Crossover, Selection m_Selection, List<Mutation> m_Mutations) {
        this.m_Crossover = m_Crossover;
        this.m_Selection = m_Selection;
        this.m_Mutations = m_Mutations;
    }
}
