package WebUI.Utils;

import data.transfer.objects.DTOEngineInformation;
import evolution.engine.EvolutionEngine;
import time.table.problem.LoadData;
import time.table.problem.Quintet;
import time.table.problem.QuintetFactory;
import time.table.problem.configurations.EvolutionEngineInfo;
import time.table.problem.configurations.rules.Rule;
import time.table.problem.objects.TimeTable;

import java.io.*;
import java.util.Map;

public class SystemData {

    private TimeTable m_TimeTable;
    private QuintetFactory m_Factory;
    private DTOEngineInformation<Quintet> m_DTOEngine;
    private EvolutionEngineInfo m_EngineInfo;
    private EvolutionEngine<Quintet> m_EvolutionEngine = null;
    private Map<Enum, Rule> m_Rules;


    public SystemData(LoadData i_data) {
        m_TimeTable = new TimeTable(i_data);
        m_Rules = i_data.getRules();
        m_Factory = new QuintetFactory(m_TimeTable);
        //m_EngineInfo = LoadData.getEngineInfo();
        //m_DTOEngine = new DTOEngineInformation<>(m_EngineInfo);
    }
/*
    public void SaveSystemToFile(String pathFile) throws IOException {
        FileOutputStream fos = new FileOutputStream(pathFile);
        ObjectOutputStream outputStream = new ObjectOutputStream(fos);
        outputStream.writeObject(m_TimeTable);
        outputStream.writeObject(m_Factory);
        outputStream.writeObject(m_EngineInfo);
        outputStream.close();
    }

    public void LoadFileToSystem(String pathFile) throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(pathFile);
        ObjectInputStream inputStream = new ObjectInputStream(fis);
        m_TimeTable = null;
        m_TimeTable = (TimeTable) inputStream.readObject();

        m_Factory = null;
        m_Factory = (QuintetFactory) inputStream.readObject();
        m_EngineInfo = null;
        m_EngineInfo = (EvolutionEngineInfo) inputStream.readObject();
        inputStream.close();
    }*/

    public void setEvolutionEngine(EvolutionEngine<Quintet> i_EvolutionEngine) {
        //   m_EvolutionEngine = i_EvolutionEngine;
    }

    public TimeTable getTimeTable() {
        return m_TimeTable;
    }

    public QuintetFactory getFactory() {
        return m_Factory;
    }

    public DTOEngineInformation<Quintet> getDTOEngine() {
        return m_DTOEngine;
    }

/*    public EvolutionEngineInfo getEngineInfo() {
        return m_EngineInfo;
    }*/

    public Map<Enum, Rule> getRules() {
        return m_Rules;
    }
}
