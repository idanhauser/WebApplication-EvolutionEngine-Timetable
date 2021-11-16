package time.table.problem.objects;

import time.table.problem.LoadData;
import time.table.problem.jaxb.schema.generated.ETTTeacher;
import time.table.problem.jaxb.schema.generated.ETTTeaches;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Teacher implements Serializable {
    private static final long serialVersionUID = 100L;
    private String m_Name;
    private List<Subject> subjectsCertification;
    private int m_Id;
    private int m_WorkingHours;

    public Teacher() {
    }

    public Teacher(String i_Name, List<Subject> i_SubjectsCertification, int i_Id, int i_WorkingHours) {
        this.m_Name = i_Name;
        this.subjectsCertification = i_SubjectsCertification;
        this.m_Id = i_Id;
        this.m_WorkingHours = i_WorkingHours;
    }

    public Teacher(ETTTeacher ettTeacher) {
        subjectsCertification = new ArrayList<>();
        Map<Integer, Subject> subjects = LoadData.getSubjects();
        List<ETTTeaches> ettSubjectList = ettTeacher.getETTTeaching().getETTTeaches();

        m_Id = ettTeacher.getId();
        m_Name = ettTeacher.getETTName();
        m_WorkingHours = ettTeacher.getETTWorkingHours();
        if (ettSubjectList != null) {
            for (ETTTeaches ettSubject : ettSubjectList) {
                if (subjects.containsKey(ettSubject.getSubjectId())) {
                    subjectsCertification.add(LoadData.FindSubject(ettSubject.getSubjectId()));
                } else {
                    throw new IllegalArgumentException("A teacher cannot teach a subject that is not on the subjects list, please check the subjects list and try again");
                }
            }
        }

    }


    public int getWorkingHours() {
        return m_WorkingHours;
    }

    public String getName() {
        return m_Name;
    }

    public void setName(String name) {
        this.m_Name = name;
    }

    public int getId() {
        return m_Id;
    }

    public boolean isTeachingSubject(Subject i_subject) {
        return subjectsCertification.contains(i_subject);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Teacher)) return false;
        Teacher teacher = (Teacher) o;
        return m_Id == teacher.m_Id && m_WorkingHours == teacher.m_WorkingHours && Objects.equals(m_Name, teacher.m_Name) && Objects.equals(subjectsCertification, teacher.subjectsCertification);
    }

    @Override
    public int hashCode() {
        return Objects.hash(m_Name, subjectsCertification, m_Id, m_WorkingHours);
    }

    @Override
    public String toString() {
        return "Id=" + m_Id + ") " + m_Name + ", working hours: " + m_WorkingHours + ".";
    }
}
