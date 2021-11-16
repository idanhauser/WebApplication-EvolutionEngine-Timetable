package data.transfer.objects;

import evolution.engine.EvolutionEngineInformation;

import java.util.Objects;

public class EnginesTableDTO {
    private final String m_ProblemName;
    private final boolean isPause;
    private final int m_InitialPopulation;
    private final int m_Days;
    private final int m_Hours;
    private final int m_TeachersLen;
    private final int m_StudyClassesLen;
    private final int m_SubjectsLen;
    private final int m_SoftRules;
    private final int m_HardRules;
    private final int m_FittestScore;


    public EnginesTableDTO(String m_ProblemName, boolean isPause, int m_InitialPopulation, int m_Days, int m_Hours, int m_TeachersLen, int m_StudyClassesLen, int m_SubjectsLen, int m_SoftRules, int m_HardRules, int m_FittestScore) {
        this.m_ProblemName = m_ProblemName;
        this.isPause = isPause;
        this.m_InitialPopulation = m_InitialPopulation;
        this.m_Days = m_Days;
        this.m_Hours = m_Hours;
        this.m_TeachersLen = m_TeachersLen;
        this.m_StudyClassesLen = m_StudyClassesLen;
        this.m_SubjectsLen = m_SubjectsLen;
        this.m_SoftRules = m_SoftRules;
        this.m_HardRules = m_HardRules;
        this.m_FittestScore = m_FittestScore;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EnginesTableDTO)) return false;
        EnginesTableDTO that = (EnginesTableDTO) o;
        return isPause == that.isPause && m_InitialPopulation == that.m_InitialPopulation && m_Days == that.m_Days && m_Hours == that.m_Hours && m_TeachersLen == that.m_TeachersLen && m_StudyClassesLen == that.m_StudyClassesLen && m_SubjectsLen == that.m_SubjectsLen && m_SoftRules == that.m_SoftRules && m_HardRules == that.m_HardRules && m_FittestScore == that.m_FittestScore && m_ProblemName.equals(that.m_ProblemName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(m_ProblemName, isPause, m_InitialPopulation, m_Days, m_Hours, m_TeachersLen, m_StudyClassesLen, m_SubjectsLen, m_SoftRules, m_HardRules, m_FittestScore);
    }
}
