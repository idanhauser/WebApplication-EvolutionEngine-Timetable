package WebUI.Utils;

import data.transfer.objects.EnginesTableDTO;
import evolution.engine.EvolutionEngine;
import time.table.problem.Quintet;

import java.util.*;

public class EnginesList {


    Map<String, List<EvolutionEngine<Quintet>>> allEnginesList;

    public EnginesList() {
        allEnginesList = new TreeMap<>();
    }


    public boolean isExist(String username) {
        return allEnginesList.containsKey(username);
    }

    public void addEngine(String username, EvolutionEngine<Quintet> i_newEngine) {
        // EnginesTableDTO temp;
        if (allEnginesList.containsKey(username)) {
            //temp = new EnginesTableDTO(i_newEngine.getProblemName(), i_newEngine.getIsRunning(), i_newEngine.getEngineInfo().getInitialPopulation(), i_newEngine.getDays(), i_newEngine.getHours(), i_newEngine.getTimeTable().getTeachers().size(), i_newEngine.getTimeTable().getStudyClasses().size(), i_newEngine.getTimeTable().getSubjects().size(), i_newEngine.getSoftRules(), i_newEngine.getHardRules(), i_newEngine.getPopulation().getFittestScore().getFitness());
            allEnginesList.get(username).add(i_newEngine);
        } else {
            //temp = new EnginesTableDTO(i_newEngine.getProblemName(), i_newEngine.getIsRunning(), i_newEngine.getEngineInfo().getInitialPopulation(), i_newEngine.getDays(), i_newEngine.getHours(), i_newEngine.getTimeTable().getTeachers().size(), i_newEngine.getTimeTable().getStudyClasses().size(), i_newEngine.getTimeTable().getSubjects().size(), i_newEngine.getSoftRules(), i_newEngine.getHardRules(), i_newEngine.getPopulation().getFittestScore().getFitness());
            allEnginesList.put(username, new ArrayList<>());
            allEnginesList.get(username).add(i_newEngine);

        }
    }

    public List<EvolutionEngine<Quintet>> getClient(String username) {
        return allEnginesList.get(username);
    }

    public Map<String, List<EnginesTableDTO>> getDTOList() {
        Map<String, List<EnginesTableDTO>> enginesToShowOnTable = new TreeMap<>();

        allEnginesList.entrySet().forEach(engineSet ->
        {
            enginesToShowOnTable.put(engineSet.getKey(), new ArrayList<>());
            engineSet.getValue().forEach(engine ->
            {
                EnginesTableDTO temp;
                if (engine.getBestSolutionInAllGens() != null) {
                    //temp = new EnginesTableDTO(engine.getProblemName(), engine.getIsRunning(), engine.getEngineInfo().getInitialPopulation(), engine.getDays(), engine.getHours(), engine.getTimeTable().getTeachers().size(), engine.getTimeTable().getStudyClasses().size(), engine.getTimeTable().getSubjects().size(), engine.getSoftRules(), engine.getHardRules(), engine.getBestSolutionInAllGens().getFitness());
                    temp = new EnginesTableDTO(engine.getProblemName(), engine.getIsRunning(), engine.getEngineInfo().getInitialPopulation(), engine.getDays(), engine.getHours(), engine.getTimeTable().getTeachers().size(), engine.getTimeTable().getStudyClasses().size(), engine.getTimeTable().getSubjects().size(), engine.getSoftRules(), engine.getHardRules(), engine.getMaxFitness());
                } else {
                    temp = new EnginesTableDTO(engine.getProblemName(), engine.getIsRunning(), engine.getEngineInfo().getInitialPopulation(), engine.getDays(), engine.getHours(), engine.getTimeTable().getTeachers().size(), engine.getTimeTable().getStudyClasses().size(), engine.getTimeTable().getSubjects().size(), engine.getSoftRules(), engine.getHardRules(), 0);

                }
                enginesToShowOnTable.get(engineSet.getKey()).add(temp);
            });
        });

        return enginesToShowOnTable;

    }


  /*      public void deleteClient(String userToDelete)
        {
            if(isExist(userToDelete))   usersList.remove(userToDelete);
       }
       */
}


