package data.transfer.objects;

import time.table.problem.configurations.rules.Rule;
import time.table.problem.configurations.rules.RuleIdEnum;

import java.util.Objects;

public class RulesDTO {
    private Rule.RuleTypeEnum m_Type;
    private RuleIdEnum m_RuleName;
    private float m_Grade;

    public RulesDTO(Rule.RuleTypeEnum i_Type, RuleIdEnum i_RuleName, float i_Grade) {
        this.m_Type = i_Type;
        this.m_RuleName = i_RuleName;
        this.m_Grade = i_Grade;
    }
    public RulesDTO(Rule.RuleTypeEnum i_Type, RuleIdEnum i_RuleName) {
        this.m_Type = i_Type;
        this.m_RuleName = i_RuleName;
        this.m_Grade = 0;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RulesDTO)) return false;
        RulesDTO rulesDTO = (RulesDTO) o;
        return Float.compare(rulesDTO.m_Grade, m_Grade) == 0 && m_Type == rulesDTO.m_Type && m_RuleName == rulesDTO.m_RuleName;
    }

    @Override
    public int hashCode() {
        return Objects.hash(m_Type, m_RuleName, m_Grade);
    }
}
