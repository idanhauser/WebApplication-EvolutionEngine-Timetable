var refreshRate = 2000; //milli seconds
var USER_LIST_URL = buildUrlWithContextPath("usersList");
var USERS_DATA_URL = buildUrlWithContextPath("usersData")
var ALL_ENGINES_TABLE_URL = buildUrlWithContextPath("allEnginesTable")
var MY_ENGINES_TABLE_URL = buildUrlWithContextPath("myEnginesTable")
var USER_MESSAGES_URL = buildUrlWithContextPath("userMessages")


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

function ajaxUsersList() {
    $.ajax({
        url: USER_LIST_URL,
        success: function (users) {
            refreshUsersList(users);
        }
    });
}

function singleEngineRedirect(problemName) {
    $.ajax
    (
        {
            url: '../UsersHomePage/getSpecificEnginePage',
            data: {
                engine: problemName
            },
            type: 'GET',
            success: redirectCallback
        }
    );
}


function redirectCallback() {
    window.location = "../SingleEnginePage/singleEngine.html";
}


//All Engines TABLE
function refreshAllEnginesTable(engines) {
    $("#AllRunsTable").empty();
    $('#AllRunsTable').append('<thead><tr> <th>Username </th> <th>Problem name</th><th>Is engine running</th> <th>Initial people</th> <th>Day</th><th>Hours</th><th>Teachers</th><th>Classes</th><th>Subjects</th><th>#Soft rules</th><th>#Hard rules</th><th >Best fitness</th></tr></thead>');
    // $.each(engines || [], function (index, engine) {
    //     console.log("Adding engine " + engine);

        for (var key in engines) {
            if (engines.hasOwnProperty(key)) {
                //alert(key + " -> " + engine[key]);
                $.each(engines[key] || [], function (index, engineIdx) {
                    var trHTML = '';
                    trHTML += '<tr><td>' + key + '</td><td>' + engineIdx.m_ProblemName + '</td><td>' + engineIdx.isPause + '</td><td>' + engineIdx.m_InitialPopulation + '</td><td>' + engineIdx.m_Days + '</td><td>' + engineIdx.m_Hours + '</td><td>' + engineIdx.m_TeachersLen + '</td><td>' + engineIdx.m_StudyClassesLen + '</td><td>' + engineIdx.m_SubjectsLen + '</td><td>' + engineIdx.m_SoftRules + '</td><td>' + engineIdx.m_HardRules + '</td><td>' + engineIdx.m_FittestScore + '</td></tr>';
                    $('#AllRunsTable').append(trHTML);
                })
            }
        }
    //});
}

function ajaxAllEnginesTable() {
    $.ajax({
        url: ALL_ENGINES_TABLE_URL,
        success: function (engines) {
            console.log("Adding engines to all table " + engines);
            refreshAllEnginesTable(engines);
        }
    });
}


//My Engines TABLE
function refreshMyEnginesTable(engines) {
    $("#myRunsTable").empty();
    $('#myRunsTable').append('<thead><tr> <th>Problem name </th> <th>is Running</th><th>Initial people</th><th>Day</th><th>Hours</th><th>Teachers</th><th>Classes</th><th>Subjects</th><th>#Soft rules</th><th>#Hard rules</th><th>Best fitness</th></tr></thead>');
    $.each(engines || [], function (index, engine) {
        console.log("Adding engine " + engine);
        var trHTML = '';
        trHTML += '<tr><td>' + engine.m_ProblemName + '</td><td>' + engine.isPause + '</td><td>' + engine.m_InitialPopulation + '</td><td>' + engine.m_Days + '</td><td>' + engine.m_Hours + '</td><td>' + engine.m_TeachersLen + '</td><td>' + engine.m_StudyClassesLen + '</td><td>' + engine.m_SubjectsLen + '</td><td>' + engine.m_SoftRules + '</td><td>' + engine.m_HardRules + '</td><td>' + engine.m_FittestScore + '</td></tr>';
        $('#myRunsTable').append(trHTML);
    });

    var tr = $('#myRunsTable')[0];
    var max = tr.childElementCount;

    for (var i = 1; i < max; i++) {
        tr.children[i].onclick = function (j) {
            return function () {
                var ans = $('#myRunsTable')[0].children[j].cells[0].innerText;
                singleEngineRedirect(ans);
            };
        }(i);

    }
}

function ajaxMyEnginesTable() {
    $.ajax({
        url: MY_ENGINES_TABLE_URL,
        success: function (engines) {
            console.log(engines);
            refreshMyEnginesTable(engines);
        }
    });
}



function CrossoverChecked(selectedCrossover) {
    if (selectedCrossover.value === "AspectOriented") {
        document.getElementById("AspectOrientedDiv").style.display = "block";
    } else {
        document.getElementById("AspectOrientedDiv").style.display = "none";
    }
}


function SelectionChecked(selectedSelection) {
    if (selectedSelection.value === "Truncation" || selectedSelection.value === "Tournament") {
        document.getElementById("probabilitySelectionDiv").style.display = "block";
    } else {
        document.getElementById("probabilitySelectionDiv").style.display = "none";
    }
}

//time conditions:
function onGenerationCondition() {
    var checkBox = document.getElementById("GenerationConditionCB");
    var disabled = document.getElementById("GenerationCondition").disabled;
    // If the checkbox is checked, display the output text
    if (checkBox.checked === true) {
        document.getElementById("GenerationCondition").disabled = false;
    } else {
        document.getElementById("GenerationCondition").disabled = true;
    }
}

function onFitnessCondition() {
    var checkBox = document.getElementById("FitnessConditionCB");
    var disabled = document.getElementById("FitnessCondition").disabled;
    // If the checkbox is checked, display the output text
    if (checkBox.checked === true) {
        document.getElementById("FitnessCondition").disabled = false;
    } else {
        document.getElementById("FitnessCondition").disabled = true;
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
    if(sizerCheckBox.checked===true || flippingCheckBox.checked ===true)
    {
         document.getElementById("probabilityMutation").disabled = false;
    }
    else
    {
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
    if(sizerCheckBox.checked===true || flippingCheckBox.checked ===true)
    {
        document.getElementById("probabilityMutation").disabled = false;
    }
    else
    {
        document.getElementById("probabilityMutation").disabled = true;
    }
}



function onTimeCondition() {
    var checkBox = document.getElementById("TimeConditionCB");
    var disabled = document.getElementById("TimeCondition").disabled;
    // If the checkbox is checked, display the output text
    if (checkBox.checked === true) {
        document.getElementById("TimeCondition").disabled = false;
    } else {
        document.getElementById("TimeCondition").disabled = true;
    }
}



//New Engine
function closeIPODialog() {
    document.getElementById("ipo-dialog").open = false;
    document.getElementById("ipo-form").reset();
    $("#ipo-dialog-error").text("")
}

function openIPODialog() {
    document.getElementById("ipo-dialog").open = true;
}

function setIpo() {
    //xml file:
    var file = this[0].files[0];
    if (file !== undefined) {
        var formData = new FormData(this);
        formData.append("fake-key-1", file);
        formData.append("initialPopulation", "initialPopulation");
        formData.append("selection", "selection");
        formData.append("Elitism", "Elitism");
        formData.append("CuttingPoints", "CuttingPoints");
        formData.append("AspectOrientedType", "AspectOrientedType");
       formData.append("FlippingMutationCB", "FlippingMutationCB");
        formData.append("SizerMutationCB", "SizerMutationCB");
        formData.append("MaxTupples", "MaxTupples");
        formData.append("TotalTupples", "TotalTupples");
        formData.append("FlippingMutation", "FlippingMutation");
        formData.append("probabilitySelection", "probabilitySelection");
        formData.append("probabilityMutation", "probabilityMutation");

        //stop conditions:
        var GenerationConditionCB = document.getElementById("GenerationConditionCB");
        var FitnessConditionCB = document.getElementById("FitnessConditionCB");
        var TimeConditionCB = document.getElementById("TimeConditionCB");

        if(document.getElementById("SizerMutationCB").checked===false && document.getElementById("FlippingMutationCB").checked ===false)
        {
                alert("You have to choose at least one mutation!")
                return false;
        }

            if (GenerationConditionCB.checked === false && FitnessConditionCB.checked === false && TimeConditionCB.checked === false)
        {
            alert("You have to choose stop condition first!")
            return false;
        }
            if (GenerationConditionCB.checked === true) {
                formData.append("GenerationCondition", "GenerationCondition");
            } else {
                formData.append("GenerationCondition", "0");
            }
        if (FitnessConditionCB.checked === true) {
            formData.append("FitnessCondition", "FitnessCondition");
        } else {
            formData.append("FitnessCondition", "0");
        }

        if (TimeConditionCB.checked === true) {
            formData.append("TimeCondition", "TimeCondition");
        } else {
            formData.append("TimeCondition", "0");
        }

        $.ajax({
            url: this.action,
            //  data: $(this).serialize(),
            data: formData,
            type: 'POST',
            processData: false,
            async: false,
            contentType: false,
            timeout: 4000,
            success: function (c) {
                console.log("SUCCESS");
                closeIPODialog()
                $.growl.notice({
                    title: "An Evolution engine was created!",
                    message: ""
                });
            },
            error: function (xhr, textStatus, err) {
                $("#ipo-dialog-error").text(xhr.responseText)
                console.log(xhr.responseText);
            }
        });
    } else {
        alert("Please choose a file to load first!");
    }
    return false;
}

//ADD MONEY
// function openAddMoneyDialog() {
//
//     document.getElementById("add-money-dialog").open = true;
// }

// function closeAddMoneyDialog() {
//     document.getElementById("add-money-dialog").open = false;
// }


//USERS DATA
function ajaxUsersData() {
    $.ajax({
        url: USERS_DATA_URL,
        success: function (usersData) {
            document.getElementById("userDataSpan").textContent += usersData;
        }
    });
}

//ON LOAD
$(function () {

    ajaxUsersData();
    ajaxUsersList();
    setInterval(ajaxUsersList, refreshRate);//online users
    ajaxAllEnginesTable()
    setInterval(ajaxAllEnginesTable, refreshRate);//All engines table
    ajaxMyEnginesTable();
    setInterval(ajaxMyEnginesTable, refreshRate);//My engines table
    $("#ipo-form").submit(setIpo);//XML file load

});

