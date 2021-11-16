var refreshRate = 2000; //milli seconds


var USER_MESSAGES_URL = buildUrlWithContextPath("userMessages")
var USER_LIST_URL = buildUrlWithContextPath("usersList")
var LOGIN_PAGE_URL = buildUrlWithContextPath("/pages/LoginPage/Login")
var START_ENGINE_URL = buildUrlWithContextPath("pages/SingleEnginePage/startEngine/")
var STOP_ENGINE_URL = buildUrlWithContextPath("pages/SingleEnginePage/stopEngine/")
var PAUSE_ENGINE_URL = buildUrlWithContextPath("pages/SingleEnginePage/pauseEngine/")
var RESUME_ENGINE_URL = buildUrlWithContextPath("pages/SingleEnginePage/resumeEngine/")
var ENGINE_NAME_URL = buildUrlWithContextPath("engineName")
var ENGINE_END_URL = buildUrlWithContextPath("IsEngineEnd")
var ENGINE_IS_RUNNING_URL = buildUrlWithContextPath("isRunning")
var BEST_SOLUTION_GRAPH_URL = buildUrlWithContextPath("BestSolutionGraph")
var PROGRESS_BARS_URL = buildUrlWithContextPath("ProgressBars")

var BEST_SOLUTION_URL = buildUrlWithContextPath("BestSolution")
var BEST_SOLUTION_TABLE_URL = buildUrlWithContextPath("BestSolutionTable")
var RULES_TABLE_URL = buildUrlWithContextPath("RulesTable")
var TEACHERS_TABLE_URL = buildUrlWithContextPath("TeachersTable")
var SUBJECTS_TABLE_URL = buildUrlWithContextPath("SubjectsTable")
var CLASSES_TABLE_URL = buildUrlWithContextPath("ClassesTable")
var Engine_CONFIGURATION_URL = buildUrlWithContextPath("EngineConfiguration")
var amountOfStockAvailable;
var graph;


window.onload = function () {
    checkLoginStatus();
};

function checkLoginStatus() {
    $.ajax
    ({
        url: LOGIN_PAGE_URL,
        data: {
            action: "status"
        },
        type: 'GET',
        success: statusCallback
    });
}

function TableChecked(selectedOption) {
    if (selectedOption.value === "Teachers") {

        document.getElementById("ClassesDiv").style.display = "none";
        document.getElementById("TeachersDiv").style.display = "block";
    } else if (selectedOption.value === "Classes") {

        document.getElementById("TeachersDiv").style.display = "none";
        document.getElementById("ClassesDiv").style.display = "block";
    } else {

        document.getElementById("TeachersDiv").style.display = "none";
        document.getElementById("ClassesDiv").style.display = "none";
    }
}

function setShowAsClassesOptions() {
    $.ajax({
        url: CLASSES_TABLE_URL,
        success: function (ClassesList) {
            select = document.getElementById('ClassesType');
            $.each(ClassesList || [], function (index, studyClass) {
                var opt = document.createElement('option');
                opt.value = studyClass.m_Name;
                opt.innerHTML = studyClass.m_Name;
                select.appendChild(opt);
            });
        }
    });
}

function setShowAsTeacherOptions() {
    $.ajax({
        url: TEACHERS_TABLE_URL,
        success: function (TeachersList) {
            select = document.getElementById('TeachersType');
            $.each(TeachersList || [], function (index, teacher) {
                var opt = document.createElement('option');
                opt.value = teacher.m_Name;
                opt.innerHTML = teacher.m_Name;
                select.appendChild(opt);
            });
        }
    });
}

function onElitism() {

    var checkBox = document.getElementById("elitismCB");
    var disabled = document.getElementById("Elitism").disabled;
    // If the checkbox is checked, display the output text
    if (checkBox.checked === true) {
        document.getElementById("Elitism").disabled = false;
    } else {
        document.getElementById("Elitism").disabled = true;
    }
}

function onSelection() {

    var checkBox = document.getElementById("selectionCB");
    var disabled = document.getElementById("selection").disabled;
    // If the checkbox is checked, display the output text
    if (checkBox.checked === true) {
        document.getElementById("selection").disabled = false;
        document.getElementById("probabilitySelection").disabled = false;

    } else {
        document.getElementById("selection").disabled = true;
        document.getElementById("probabilitySelection").disabled = true;

    }
}

function SelectionChecked(selectedSelection) {

    if (selectedSelection.value === "Truncation" || selectedSelection.value === "Tournament") {
        document.getElementById("probabilitySelectionDiv").style.display = "block";
        document.getElementById("probabilitySelection").style.display = "block";
        document.getElementById("probabilitySelection").disabled = false;

    } else {
        document.getElementById("probabilitySelectionDiv").style.display = "none";
        document.getElementById("probabilitySelection").disabled = true;

    }
}


function onCrossover() {

    var checkBox = document.getElementById("crossoverCB");
    // If the checkbox is checked, display the output text
    if (checkBox.checked === true) {
        document.getElementById("crossover").disabled = false;
        document.getElementById("AspectOrientedType").disabled = false;
        document.getElementById("CuttingPointsDiv").style.display = "block";

    } else {
        document.getElementById("crossover").disabled = true;
        document.getElementById("AspectOrientedType").disabled = true;
        document.getElementById("CuttingPointsDiv").style.display = "none";

    }
}

function CrossoverSelected(selectedCrossover) {
    if (selectedCrossover.value === "AspectOriented") {
        document.getElementById("AspectOrientedDiv").style.display = "block";
        document.getElementById("AspectOrientedType").disabled = false;
    } else {
        document.getElementById("AspectOrientedDiv").style.display = "none";
    }
}

//Mutations:
function onMutations() {
    var checkBox = document.getElementById("mutationsCB");
    // If the checkbox is checked, display the output text
    if (checkBox.checked === true) {
        document.getElementById("SizerMutationCB").disabled = false;
        document.getElementById("FlippingMutationCB").disabled = false;
        document.getElementById("probabilityMutationDiv").style.display = "block";


    } else {
        document.getElementById("SizerMutationCB").disabled = true;
        document.getElementById("FlippingMutationCB").disabled = true;
        document.getElementById("probabilityMutationDiv").style.display = "none";

    }
}

function onSizerMutation() {
    var sizerCheckBox = document.getElementById("SizerMutationCB");
    var flippingCheckBox = document.getElementById("FlippingMutationCB");
    // If the checkbox is checked, display the output text
    if (sizerCheckBox.checked === true) {
        document.getElementById("SizerMutationDiv").style.display = "block";
    } else {
        document.getElementById("SizerMutationDiv").style.display = "none";
    }
    if (sizerCheckBox.checked === true || flippingCheckBox.checked === true) {
        document.getElementById("probabilityMutation").disabled = false;
    } else {
        document.getElementById("probabilityMutation").disabled = true;
    }
}


function onFlippingMutation() {
    var sizerCheckBox = document.getElementById("SizerMutationCB");
    var flippingCheckBox = document.getElementById("FlippingMutationCB");
    // If the checkbox is checked, display the output text
    if (flippingCheckBox.checked === true) {
        document.getElementById("FlippingMutationDiv").style.display = "block";
    } else {
        document.getElementById("FlippingMutationDiv").style.display = "none";
    }
    if (sizerCheckBox.checked === true || flippingCheckBox.checked === true) {
        document.getElementById("probabilityMutation").disabled = false;
    } else {
        document.getElementById("probabilityMutation").disabled = true;
    }
}


//On Change Engine Settings Action
function closeChangeSettingsDialog() {
    document.getElementById("ChangeSettings-dialog").open = false;
    document.getElementById("ChangeSettings-form").reset();
    $("#ChangeSettings-dialog-error").text("")
    document.getElementById("FitnessGraph").style.display = "block";
    document.getElementById("ChangeEngineConfButton").style.display = "block";
    document.getElementById("ContinueEngineButton").style.display = "block";
    document.getElementById("StopEngineButton").style.display = "block";

    ajaxEngineConfiguration();
}

function openChangeSettingsDialog() {
    document.getElementById("ChangeSettings-form").reset();
    document.getElementById("ChangeSettings-dialog").open = true;
    document.getElementById("StopEngineButton").style.display = "none";
    ajaxEngineConfiguration();

}

function ajaxEngineConfiguration() {
    $.ajax({
        url: Engine_CONFIGURATION_URL,
        success: function (engineConf) {
            refreshEngineConfiguration(engineConf);
        }
    });
}

function refreshEngineConfiguration(engineConf) {

    //Elitism:
    document.getElementById("Elitism").value = engineConf.m_Selection.m_ElitismCount;
    // document.getElementById("Elitism").disabled = true;
    if (engineConf.m_Selection.m_ElitismCount !== "") {
        //      document.getElementById("elitismCB").checked = true;
        document.getElementById("Elitism").disabled = false;
    }

    //Selection:
    if (engineConf.m_Selection.m_EnumSelectType === "RouletteWheel") {
        document.getElementById("selection").selectedIndex = 0;

    } else if (engineConf.m_Selection.m_EnumSelectType === "Truncation") {
        document.getElementById("selection").selectedIndex = 1;
        document.getElementById("probabilitySelectionDiv").style.display = "block";

    } else {
        document.getElementById("selection").selectedIndex = 2;
        document.getElementById("probabilitySelectionDiv").style.display = "block";

    }
    document.getElementById("selection").disabled = false;
    // document.getElementById("selection").disabled = true;
    // document.getElementById("selectionCB").checked = true;


    if (engineConf.m_Selection.m_EnumSelectType !== "RouletteWheel") {
        document.getElementById("probabilitySelection").value = engineConf.m_Selection.m_TopPercent;
        document.getElementById("probabilitySelection").disabled = false;

    } else {
        document.getElementById("probabilitySelectionDiv").style.display = "none";

    }

    //Crossover:
    if (engineConf.m_Crossover.m_EnumCrossoverType === "DayTimeOriented") {
        document.getElementById("crossover").selectedIndex = 0;
        //   document.getElementById("crossoverCB").checked = true;
        document.getElementById("crossover").selectedIndex = 0;
        document.getElementById("crossover").disabled = false;

    } else if (engineConf.m_Crossover.m_EnumCrossoverType === "AspectOriented") {
        //  document.getElementById("crossoverCB").checked = true;
        document.getElementById("AspectOrientedDiv").style.display = "block";
        document.getElementById("CuttingPointsDiv").style.display = "block";
        document.getElementById("crossover").selectedIndex = 1;
        document.getElementById("crossover").disabled = false;
        if (engineConf.m_Crossover.m_OrientationType === "Teacher") {
            document.getElementById("AspectOrientedType").selectedIndex = 0;
            document.getElementById("AspectOrientedType").disabled = false;

        } else {
            document.getElementById("AspectOrientedType").selectedIndex = 1;
            document.getElementById("AspectOrientedType").disabled = false;
        }

        document.getElementById("crossover").disabled = false;
    }

    document.getElementById("CuttingPoints").value = engineConf.m_Crossover.m_CuttingPoint;
    document.getElementById("CuttingPoints").disabled = false;


    for (let i = 0; i < engineConf.m_Mutations.length; i++) {
        if (engineConf.m_Mutations[i].m_EnumType === "Sizer") {
            document.getElementById("SizerMutationDiv").style.display = "block";
            document.getElementById("SizerMutationCB").checked = true;
            document.getElementById("TotalTupples").value = engineConf.m_Mutations[i].m_NumberOfTupples;
            // document.getElementById("mutationsCB").checked = true;

            document.getElementById("TotalTupples").disabled = false;
        } else {
            //   document.getElementById("mutationsCB").checked = true;
            document.getElementById("FlippingMutationDiv").style.display = "block";
            document.getElementById("FlippingMutationCB").checked = true;
            document.getElementById("MaxTupples").value = engineConf.m_Mutations[i].m_NumberOfTupples;
            if (engineConf.m_Mutations[i].m_Component === 'D') {
                document.getElementById("FlippingMutationComponent").selectedIndex = 0;
            } else if (engineConf.m_Mutations[i].m_Component === 'H') {
                document.getElementById("FlippingMutationComponent").selectedIndex = 1;
            } else if (engineConf.m_Mutations[i].m_Component === 'S') {
                document.getElementById("FlippingMutationComponent").selectedIndex = 2;
            } else {
                document.getElementById("FlippingMutationComponent").selectedIndex = 3;
            }
        }
        document.getElementById("FlippingMutationCB").disabled = false;
        document.getElementById("SizerMutationCB").disabled = false;
    }
    document.getElementById("probabilityMutation").value = engineConf.m_Mutations[0].m_Probability;
    document.getElementById("probabilityMutation").disabled = false;
    document.getElementById("probabilityMutationDiv").style.display = "block";

    // document.getElementById("SizerMutationCB").disabled = true;
    //document.getElementById("FlippingMutationCB").disabled = true;


}


function setPageRelevantButtons() {

    $.ajax({
        url: ENGINE_IS_RUNNING_URL,
        type: 'GET',
        success: function (isRunning) {

            if (isRunning == true) {
                document.getElementById("StartEngineButton").style.display = "none";
                document.getElementById("ContinueEngineButton").style.display = "none";
                document.getElementById("StopEngineButton").style.display = "block";
                document.getElementById("PauseEngineButton").style.display = "block";
                document.getElementById("ChangeEngineConfButton").style.display = "none";
            }
        },
    });
}

function changeEngineConfiguration() {
    $("#ChangeSettings-form").submit(function () {
        $.ajax({
            url: this.action,
            data: $(this).serialize(),
            type: 'POST',
            success: function (c) {
                console.log("SETTINGS CHANGED");
                closeChangeSettingsDialog()
                $.growl.notice({
                    title: "New configuration was set successfully",
                    message: ""
                });
            },
            error: function (c) {
                $("#ChangeSettings-dialog-error").text(c.responseText)
                console.log(c);
            }
        });
        return false;
    });
}

function onStartEngine() {
    document.getElementById("StartEngineButton").style.display = "none";
    document.getElementById("ContinueEngineButton").style.display = "none";
    document.getElementById("StopEngineButton").style.display = "block";
    document.getElementById("PauseEngineButton").style.display = "block";
    document.getElementById("ChangeEngineConfButton").style.display = "none";

    //Starting Engine
    $.ajax({
        url: START_ENGINE_URL,
        type: 'POST',
        success: function (c) {

            $.growl.notice({
                title: "Engine started!",
                message: "You will receive a notification when its done."
            });
        },
        error: function (c) {
            $.growl.error({title: "Engine can't start!", message: c.responseText})
        }
    });
    return false;
}


function onContinueEngine() {
    document.getElementById("StartEngineButton").style.display = "none";
    document.getElementById("ContinueEngineButton").style.display = "none";
    document.getElementById("StopEngineButton").style.display = "block";
    document.getElementById("PauseEngineButton").style.display = "block";
    document.getElementById("ChangeEngineConfButton").style.display = "none";


    //Resuming Engine
    $.ajax({
        url: RESUME_ENGINE_URL,
        type: 'POST',
        success: function (c) {

            $.growl.notice({
                title: "Engine is running!",
                message: "May the best fitness will ne with you"
            });
        },
        error: function (c) {
            $.growl.error({title: "There was a problem resuming the engine!", message: c.responseText})
        }
    });
    return false;

}

function onPauseEngine() {
    document.getElementById("StartEngineButton").style.display = "none";
    document.getElementById("ContinueEngineButton").style.display = "block";
    document.getElementById("StopEngineButton").style.display = "block";
    document.getElementById("PauseEngineButton").style.display = "none";
    document.getElementById("ChangeEngineConfButton").style.display = "block";


    //Pausing Engine
    $.ajax({
        url: PAUSE_ENGINE_URL,
        type: 'POST',
        success: function (c) {

            $.growl.notice({
                title: "Engine Paused!",
                message: "You can change engine configuration."
            });
        },
        error: function (c) {
            $.growl.error({title: "There was a problem pausing the engine!", message: c.responseText})
        }
    });
    return false;
}

function onStopEngine() {
    document.getElementById("StartEngineButton").style.display = "block";
    document.getElementById("ChangeEngineConfButton").style.display = "none";
    document.getElementById("ContinueEngineButton").style.display = "none";
    document.getElementById("StopEngineButton").style.display = "none";
    document.getElementById("PauseEngineButton").style.display = "none";

    //Starting Engine
    $.ajax({
        url: STOP_ENGINE_URL,
        type: 'POST',
        success: function (c) {

            $.growl.notice({
                title: "Engine was Stopped!",
                message: "You can look at the best solution the engine found."
            });
        },
        error: function (c) {
            $.growl.error({title: "There was a problem stopping the engine!", message: c.responseText})
        }
    });
    return false;
}

function onChangeEngine() {
    document.getElementById("StartEngineButton").style.display = "none";
    document.getElementById("ContinueEngineButton").style.display = "none";
    document.getElementById("StopEngineButton").style.display = "block";
    document.getElementById("PauseEngineButton").style.display = "none";
    document.getElementById("ChangeEngineConfButton").style.display = "none";
    openChangeSettingsDialog();
}

function ajaxEndOfRunningFlag() {
    $.ajax({
        url: ENGINE_END_URL,
        type: 'GET',
        success: function (isEnd) {

            if (isEnd === true) {
                $.growl.notice({
                    title: "Engine was Stopped!",
                    message: "Engine running reached to the required condition"
                });
            }
        },
    });
    return false;
}


function ajaxSetEngineName() {
    $.ajax({
        url: ENGINE_NAME_URL,
        success: function (name) {
            setEngineName(name);
        }
    });
}

function setEngineName(name) {

    document.getElementById("EngineName").innerText = name;

}

//GRAPH
function refreshGraph(histories) {
    graph.options.data[0].dataPoints = [];
    $.each(histories || [], function (index, engine) {
        //graph.options.data[0].dataPoints.push({x:new Data(deal.date).valueOf(), y: deal.price});
        graph.options.data[0].dataPoints.push({x: index + 1, y: engine.m_Population.getFittestScore});
    });
    graph.render();
}

//USER MESSAGES
function refreshUserMassages(messages) {
    $.each(messages || [], function (index, message) {
        $.growl.notice({
            title: "Action Posted",
            message: message
        });
    });
}

function ajaxUserMessages() {
    $.ajax({
        url: USER_MESSAGES_URL,
        success: function (messages) {
            refreshUserMassages(messages);
        }
    });
}

function ajaxUsersList() {
    $.ajax({
        url: USER_LIST_URL,
        success: function (users) {
            refreshUsersList(users);
        }
    });
}

//USERS
function refreshUsersList(users) {
    //clear all current users
    $("#usersList").empty();

    // rebuild the list of users: scan all users and add them to the list of users
    $.each(users || [], function (index, user) {
        console.log("Adding user #" + index + ": " + user);
        $('<li>' + user + '</li>')
            .appendTo($("#usersList"));
    });
}


//******************************************** TABLES ***********************************************************//

//SUBJECTS
function ajaxProgressBars() {
    $.ajax({
        url: PROGRESS_BARS_URL,
        success: function (progress) {
            console.log("Adding subjects to table " + progress);
            refreshProgressBars(progress);
        }
    });
}

function refreshProgressBars(progress) {
    document.getElementById("GenerationProgress").value = progress.m_CurrentGeneration;
    document.getElementById("GenerationProgress").max = progress.m_ExitGeneration;
    document.getElementById("FitnessProgress").value = progress.m_CurrentFitness;
    document.getElementById("FitnessProgress").max = progress.m_ExitFitness;
    document.getElementById("TimeProgress").value = progress.m_CurrentTime;
    document.getElementById("TimeProgress").max = progress.m_ExitTime;

}


//Graph
function refreshBestSolutionGraph() {
    $.ajax({
        url: BEST_SOLUTION_GRAPH_URL,
        success: function (histories) {
            console.log("Adding subjects to table " + histories);
            refreshGraph(histories);
        }
    });
}

function refreshGraph(histories) {
    graph.options.data[0].dataPoints = [];
    $.each(histories || [], function (index, currentSol) {
        //graph.options.data[0].dataPoints.push({x:new Data(deal.date).valueOf(), y: deal.price});
        graph.options.data[0].dataPoints.push({x: index + 1, y: currentSol.m_FittestScore});
    });
    graph.render();
}

//BEST SOLUTION
function ajaxBestSolution() {
    $.ajax({
        url: BEST_SOLUTION_URL,
        success: function (bestSolution) {
            refreshBestSolution(bestSolution);
        }
    });
}

function refreshBestSolution(bestSolution) {
    var value = document.getElementById("BestFitness").textContent || 0;
    if (value < bestSolution.m_BestFitness) {
        value = bestSolution.m_BestFitness;
    }


    document.getElementById("BestFitness").textContent = value;
    document.getElementById("CurrentGeneration").textContent = bestSolution.m_GenerationCount;

}

//BEST SOLUTION
function ajaxBestSolutionTable() {
    var typeSelection = document.getElementById("ShowAs");
    var firstSelection = typeSelection.value;
    var secondSelection = null;

    if (firstSelection === "Teachers") {
        var div = document.getElementById("TeachersType");
        secondSelection = div.value;
    } else if (firstSelection === "Classes") {
        var div = document.getElementById("ClassesType");
        secondSelection = div.value;
    }

    $.ajax({
        url: BEST_SOLUTION_TABLE_URL,
        data: {
            selection1: firstSelection,
            selection2: secondSelection,
        },
        success: function (bestSolutionTable) {
            refreshBestSolutionTable(bestSolutionTable);
        }
    });
}

function refreshBestSolutionTable(bestSolutionTable) {
    $("#SingleEngineTable").empty();
    var i;
    var j;
    if (document.getElementById("ShowAs").value === "Raw") {
        $('#SingleEngineTable').append('<thead><th> cell1 </th><th> cell2 </th><th> cell3 </th><th> cell4</th><th>cell5 </th><th>cell6 </th></thead>');
        i = 0;
        j = 0;
    } else {
        $('#SingleEngineTable').append('<thead><th>Sunday</th><th>Monday</th><th>Tuesday</th><th>Wednesday</th><th>Thursday</th><th>Friday</th></thead>');
        i = 1;
        j = 1;
    }


    var maxRow = bestSolutionTable[1].length;
    var trHTML = '<tr><td>';
    for (; j < maxRow; j++) {
        for (; i < bestSolutionTable.length; i++) {
            if (bestSolutionTable[i][j] !== null) {
                trHTML += bestSolutionTable[i][j];
            } else {
                trHTML += " ";
            }

            trHTML += '</td><td>';
        }
        $('#SingleEngineTable').append(trHTML);
        trHTML += '<tr><td>';
    }


}


//RULES
function ajaxRulesTable() {
    $.ajax({
        url: RULES_TABLE_URL,
        success: function (rules) {
            console.log("Adding rules to table " + rules);
            refreshRulesTable(rules);
        }
    });
}

function refreshRulesTable(rules) {
    $("#RulesTable").empty();
    $('#RulesTable').append('<thead><tr> <th>Rule\'s Name</th> <th>Difficulty</th><th>Score</th></thead>');
    $.each(rules || [], function (index, rule) {
        console.log("Adding rule " + rule);
        var trHTML = '';
        trHTML += '<tr><td>' + rule.m_RuleName + '</td><td>' + rule.m_Type + '</td><td>' + rule.m_Grade + '</td><td>';
        $('#RulesTable').append(trHTML);
    });
}

//TEACHERS
function ajaxTeacherTable() {
    $.ajax({
        url: TEACHERS_TABLE_URL,
        success: function (teachers) {
            console.log("Adding teachers to table " + teachers);
            refreshTeachersTable(teachers);
        }
    });
}

function refreshTeachersTable(teachers) {
    $("#TeachersTable").empty();
    $('#TeachersTable').append('<thead><tr> <th>Name</th> <th>ID</th><th>Teaching Subjects</th><th>Working Hours</th></thead>');
    $.each(teachers || [], function (index, teacher) {
        console.log("Adding teacher " + teacher);
        var trHTML = '';
        trHTML += '<tr><td>' + teacher.m_Name + '</td><td>' + teacher.m_Id + '</td><td>';

        $.each(teacher.subjectsCertification || [], function (index, req) {
            trHTML += req.m_Name + ', ';
        });

        trHTML += '</td><td>' + teacher.m_WorkingHours + '</td><td>';
        $('#TeachersTable').append(trHTML);
    });
}

//SUBJECTS
function ajaxSubjectsTable() {
    $.ajax({
        url: SUBJECTS_TABLE_URL,
        success: function (subjects) {
            console.log("Adding subjects to table " + subjects);
            refreshSubjectsTable(subjects);
        }
    });
}

function refreshSubjectsTable(subjects) {
    $("#SubjectsTable").empty();
    $('#SubjectsTable').append('<thead><tr> <th>Name</th> <th>ID</th></thead>');
    $.each(subjects || [], function (index, subject) {
        console.log("Adding subject " + subject);
        var trHTML = '';
        trHTML += '<tr><td>' + subject.m_Name + '</td><td>' + subject.m_Id + '</td><td>';
        $('#SubjectsTable').append(trHTML);
    });
}

//CLASSES
function ajaxClassesTable() {
    $.ajax({
        url: CLASSES_TABLE_URL,
        success: function (classes) {
            console.log("Adding classes to table " + classes);
            refreshClassesTable(classes);
        }
    });
}

function refreshClassesTable(classes) {
    $("#ClassesTable").empty();
    $('#ClassesTable').append('<thead><tr> <th>Name</th> <th>ID</th><th>Requirements</th></thead>');
    $.each(classes || [], function (index, studyClass) {
        console.log("Adding class " + studyClass);
        var trHTML = '';
        trHTML += '<tr><td>' + studyClass.m_Name + '</td><td>' + studyClass.m_Id + '</td><td>';

        $.each(studyClass.m_requirements || [], function (index, req) {
            trHTML += req.m_Subject.m_Name + ' = ' + req.m_Hours + ' ';
        });


        trHTML += '</td><td>';
        $('#ClassesTable').append(trHTML);
    });
}


//******************************************** TABLES ***********************************************************//


//ON LOAD
$(function () {
    changeEngineConfiguration();
    var dataPoints = [];
    graph = new CanvasJS.Chart("chartContainer", {
        animationEnabled: true,
        theme: "light2",
        data: [{
            type: "line",
            indexLabelFontSize: 16,
            dataPoints: []
        }]
    });
    graph.render();

    setPageRelevantButtons();
    setShowAsClassesOptions();
    setShowAsTeacherOptions();

    ajaxUsersList();//users
    setInterval(ajaxUsersList, refreshRate);

    ajaxSetEngineName();
    setInterval(ajaxSetEngineName, refreshRate);//RulesTable


    ajaxRulesTable()
    setInterval(ajaxRulesTable, refreshRate);//RulesTable
    refreshBestSolutionGraph()
    setInterval(refreshBestSolutionGraph, refreshRate);//Graph
    ajaxTeacherTable()
    // setInterval(ajaxTeacherTable, refreshRate);//TeacherTable
    ajaxSubjectsTable()
    // setInterval(ajaxSubjectsTable, refreshRate);//SubjectTable
    ajaxClassesTable()
    // setInterval(ajaxClassesTable, refreshRate);//ClassesTable

    ajaxBestSolution()
    setInterval(ajaxBestSolution, refreshRate);

    ajaxProgressBars()
    setInterval(ajaxProgressBars, refreshRate);//Progress Bars

    ajaxEngineConfiguration();


    ajaxEndOfRunningFlag();//users
    setInterval(ajaxEndOfRunningFlag, refreshRate);

    ajaxBestSolutionTable();//Best Solution Table
    setInterval(ajaxBestSolutionTable, refreshRate);

});