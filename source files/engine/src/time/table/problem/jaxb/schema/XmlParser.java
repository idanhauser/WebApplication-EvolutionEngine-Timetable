package time.table.problem.jaxb.schema;

import time.table.problem.configurations.mutation.Mutation;
import time.table.problem.configurations.EvolutionEngineInfo;
import time.table.problem.configurations.rules.RuleIdEnum;
import time.table.problem.jaxb.schema.generated.*;
import time.table.problem.configurations.rules.Rule;
import time.table.problem.objects.StudyClass;
import time.table.problem.objects.Subject;
import time.table.problem.objects.Teacher;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.*;

public class XmlParser {
    private static String s_JAXB_XML_PACKAGE_NAME = "time.table.problem.jaxb.schema.generated";
    //private static String s_FILE_NAME = "engine/src/time/table/problem/resources/EX1-small.xml";
    private static InputStream s_InputStream;
    private ETTDescriptor m_DataReder;

    public XmlParser(InputStream i_InputStream) throws Exception {
        s_InputStream = i_InputStream;
        m_DataReder = initObject();
    }

    //for testing
    public XmlParser(String i_file) {

        try {
            s_InputStream = new FileInputStream(new File(i_file));
            m_DataReder = initObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static ETTDescriptor initObject() throws Exception {
        ETTDescriptor obj = null;
        //InputStream inputStream;
        try {
            //inputStream = new FileInputStream(new File(s_FILE_NAME));
            obj = deserializeFrom(s_InputStream);
        } catch (JAXBException e) {
            throw new Exception("Path is illegal, could not find the file: " + e.getMessage() + System.lineSeparator() + "Please try again. ");
        }
        return obj;
    }

    private static ETTDescriptor deserializeFrom(InputStream in) throws JAXBException {
        JAXBContext jc = JAXBContext.newInstance(s_JAXB_XML_PACKAGE_NAME);
        Unmarshaller u = jc.createUnmarshaller();
        return (ETTDescriptor) u.unmarshal(in);
    }

    public static <T> void checkNumbering(Map<Integer, T> teams) {
        int counter = 1;
        for (int i = 0; i < teams.size(); i++) {
            if (teams.containsKey(counter)) {
                counter++;
            } else {
                throw new IllegalArgumentException("The numbering of the object are illegal, should be '1, 2, 3, 4,..., n'");
            }
        }
    }

    public Map<Integer, StudyClass> GetClasses() {
        List<ETTClass> ettClassList = m_DataReder.getETTTimeTable().getETTClasses().getETTClass();
        Map<Integer, StudyClass> classes = new TreeMap<>();

        for (ETTClass ETTClass : ettClassList) {
            StudyClass newClass = new StudyClass(ETTClass);
            classes.put(newClass.getId(), newClass);
        }
        return classes;
    }

    public Map<Integer, Subject> LoadSubjects() throws IllegalArgumentException {
        List<ETTSubject> ettSubjectList = m_DataReder.getETTTimeTable().getETTSubjects().getETTSubject();
        Map<Integer, Subject> subjects = new TreeMap<>();

        for (ETTSubject ETTSubjects : ettSubjectList) {
            Subject newSubject = new Subject(ETTSubjects);
            subjects.put(newSubject.getId(), newSubject);
        }
        try {
            checkNumbering(subjects);
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("a problem generating the all the subjects because :" + ex.getMessage());
        }

        return subjects;
    }

    public Map<Integer, Teacher> LoadTeachers() {
        List<ETTTeacher> ettTeacherList = m_DataReder.getETTTimeTable().getETTTeachers().getETTTeacher();
        Map<Integer, Teacher> teachers = new TreeMap<>();

        for (ETTTeacher ETTTeacher : ettTeacherList) {
            Teacher newTeacher = new Teacher(ETTTeacher);
            teachers.put(newTeacher.getId(), newTeacher);
        }
        try {
            checkNumbering(teachers);
            checkHourPreferences(teachers);
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("a problem generating the all the teachers because :" + ex.getMessage());
        }
        return teachers;

    }

    private void checkHourPreferences(Map<Integer, Teacher> teachers) {
        int dayMultHours = m_DataReder.getETTTimeTable().getDays() * m_DataReder.getETTTimeTable().getHours();
        for (Teacher teacher : teachers.values()) {
            if (teacher.getWorkingHours() > dayMultHours) {
                throw new IllegalArgumentException("The teacher "
                        + teacher.getName() + " has working hours preference "
                        + teacher.getWorkingHours() + " is higher than days*hours which is "
                        + dayMultHours);
            }
        }
    }

    public Map<Enum, Rule> LoadRules() {
        List<ETTRule> ettRuleList = m_DataReder.getETTTimeTable().getETTRules().getETTRule();
        Map<Enum, Rule> rules = new HashMap();

        for (ETTRule ettRule : ettRuleList) {
            Rule newRule = new Rule(ettRule, m_DataReder.getETTTimeTable().getETTRules().getHardRulesWeight());
            if (newRule.GetRuleId() == RuleIdEnum.Sequentiality) {
                newRule.SetConfiguration(ettRule.getETTConfiguration());
            }
            if (rules.containsKey(newRule.GetRuleId())) {
                throw new IllegalArgumentException("The Rule " + newRule.GetRuleId().name() + " is appearing twice, please check for rule duplicates and try again.");
            }


            rules.put(newRule.GetRuleId(), newRule);
        }

        return rules;
    }

    /*public List<Mutation> LoadMutations() {
        List<ETTMutation> ettMutationList = m_DataReder.getETTEvolutionEngine().getETTMutations().getETTMutation();
        List<Mutation> mutations = new ArrayList();
        for (ETTMutation EttMutation : ettMutationList) {
            Mutation newMutation = new Mutation(EttMutation);
            mutations.add(newMutation);
        }
        return mutations;
    }*/

    /*public EvolutionEngineInfo LoadEngineInfo() throws Exception {
      //   List<Mutation> mutations = LoadMutations();
        List<Mutation> mutations =null;
        EvolutionEngineInfo EEInfo = new EvolutionEngineInfo(m_DataReder.getETTEvolutionEngine(), mutations, LoadRules());
        return EEInfo;
    }*/

    public ETTDescriptor getdataReder() {
        return m_DataReder;
    }


    public int LoadDays() {
        return m_DataReder.getETTTimeTable().getDays();
    }

    public int LoadHours() {
        return m_DataReder.getETTTimeTable().getHours();
    }
}
