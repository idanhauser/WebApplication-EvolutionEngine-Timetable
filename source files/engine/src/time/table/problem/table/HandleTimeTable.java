package time.table.problem.table;

import evolution.engine.EvolutionEngine;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;
import time.table.problem.Quintet;
import time.table.problem.VisualTimeTable;
import time.table.problem.objects.StudyClass;
import time.table.problem.objects.Teacher;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;


public class HandleTimeTable
{
    List<Quintet>[][] RawQuintetList;
    String selection1;
    String selection2;
    Filter TeacherFilter;
    Filter ClassFilter;
    Filter RawFilter;
    int DAY_OF_WEEK = 6;

    private int maxDays;
    private int maxHours;
    private List<String> m_Teachers;
    private List<String> m_StudyClasses;

    public HandleTimeTable(EvolutionEngine<Quintet> engine, String selection1, String selection2)
    {
        maxHours = engine.getHours();
        maxDays = engine.getDays();
        this.selection1 = selection1;
        this.selection2 = selection2;
        CreateTeachersAndClassesNameList(engine.getTimeTable().getTeachers(),engine.getTimeTable().getStudyClasses());
        initFilters();
        if(engine.getDTOEngine().getBestResult() != null)
        RawQuintetList = new VisualTimeTable(engine.getDTOEngine().getBestResult(), maxDays, maxHours).ArrangeByHoursDays();
        else RawQuintetList = null;
    }

    private void initFilters() {
        TeacherFilter = q -> q.stream().filter(t -> t.getTeacher().getName().equals(selection2)).findFirst().map(t -> cellFormation(t,false)).orElse(null);
        ClassFilter = q -> q.stream().filter(t -> t.getStudyClass().getName().equals(selection2)).findFirst().map(t -> cellFormation(t,false)).orElse(null);
        //RawFilter = q -> cellFormation(q.get(0));
    }

    public String[][] GetStringArray()
    {
        if(RawQuintetList == null)
            return null;

        if (selection1.equals("Raw")) return RawStringArrayCreation();
        if (selection1.equals("Teachers")) return StringArrayCreation(TeacherFilter);
        if (selection1.equals("Classes")) return StringArrayCreation(ClassFilter);

        return null;
    }

    //classes or teachers:
    private String[][] StringArrayCreation(Filter FilterBy)
    {
        String[][] retArr = new String[maxDays + 1][maxHours + 1];

        for (int i = 1; i <= maxHours; i++) {
            for (int j = 1; j <= maxDays; j++) {
                if (RawQuintetList[i][j] != null) {
                    retArr[j][i] = FilterBy.filter(RawQuintetList[i][j]);
                }
            }
        }
        return retArr;
    }
    //raw:
    private String[][] RawStringArrayCreation() {

        int size = getRawArrSize();
        String[][] retArr = new String[DAY_OF_WEEK + 1][size+1];

        for (int i = 1, hour = 0; i <= maxHours; i++) {
            for (int j = 1, day = 0; j <= maxDays; j++) {
                if (RawQuintetList[i][j] != null)
                {
                    for (Iterator<Quintet> itr = RawQuintetList[i][j].iterator(); itr.hasNext(); )
                    {
                        String sFormat = cellFormation(itr.next(),true);
                        retArr[day][hour] = sFormat;
                        day++;
                        if (day == DAY_OF_WEEK) {day = 0; hour++;}
                    }
                }
            }
        }

        return retArr;
    }

    private int getRawArrSize()
    {
        int size = 0;
        for (int i = 1; i <= maxHours; i++) {
            for (int j = 1; j <= maxDays; j++) {
                if(RawQuintetList[i][j] != null)
                    size += RawQuintetList[i][j].stream().count();
            }
        }
        return size/DAY_OF_WEEK +1;
    }


    String cellFormation(Quintet quintet, boolean withDaysHours) {
        StringBuilder resStr = new StringBuilder();
        resStr.append(quintet.getTeacher().getName());
        resStr.append(System.lineSeparator());
        resStr.append(quintet.getStudyClass().getName());
        resStr.append(System.lineSeparator());
        resStr.append(quintet.getSubject().getName());

        if(withDaysHours == true)
        {
            resStr.append(System.lineSeparator());
            resStr.append("day " + quintet.getDay());
            resStr.append(" hour " + quintet.getHour());
        }

        return resStr.toString();
    }

    private void CreateTeachersAndClassesNameList(Map<Integer, Teacher> teachers, Map<Integer, StudyClass> studyClasses) {
        m_Teachers = new ArrayList<>();
        teachers.forEach((key1, value1) -> m_Teachers.add(value1.getName()));
        m_StudyClasses = new ArrayList<>();
        studyClasses.forEach((key, value) -> m_StudyClasses.add(value.getName()));
    }
}
