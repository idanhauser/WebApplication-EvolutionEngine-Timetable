package data.transfer.objects;

import java.util.Objects;

public class HistoryDataDTO {
    private int m_FittestScore = 0;

    public HistoryDataDTO(int i_FittestScore) {
        this.m_FittestScore = i_FittestScore;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HistoryDataDTO)) return false;
        HistoryDataDTO that = (HistoryDataDTO) o;
        return m_FittestScore == that.m_FittestScore;
    }

    @Override
    public int hashCode() {
        return Objects.hash(m_FittestScore);
    }
}
