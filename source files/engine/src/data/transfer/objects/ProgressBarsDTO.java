package data.transfer.objects;

public class ProgressBarsDTO {
    private float m_ExitTime;
    private int m_ExitGeneration;
    private int m_ExitFitness;

    private float m_CurrentTime;
    private long m_CurrentGeneration;
    private int m_CurrentFitness;

    public ProgressBarsDTO(float m_ExitTime, int m_ExitGeneration, int m_ExitFitness, float m_CurrentTime, long m_CurrentGeneration, int m_CurrentFitness) {
        this.m_ExitTime = m_ExitTime;
        this.m_ExitGeneration = m_ExitGeneration;
        this.m_ExitFitness = m_ExitFitness;
        this.m_CurrentTime = m_CurrentTime;
        this.m_CurrentGeneration = m_CurrentGeneration;
        this.m_CurrentFitness = m_CurrentFitness;
    }
}
