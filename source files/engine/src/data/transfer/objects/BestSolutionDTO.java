package data.transfer.objects;

public class BestSolutionDTO {
    private final int  m_BestFitness;
    private final int  m_GenerationCount;

    public BestSolutionDTO(int i_BestFitness, int i_generationCount) {
        m_BestFitness = i_BestFitness;
        m_GenerationCount = i_generationCount;
    }
}
