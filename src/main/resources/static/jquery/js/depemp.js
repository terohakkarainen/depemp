$(document).ready(function() {

  $("#refreshDepartmentsLink").click(function(event) {
    refreshDepartments();
    event.preventDefault();
  });

  $("#addDepartmentForm").submit(function(e) {
    $.ajax({
        type: "POST",
        url: "/departments",
        contentType: "application/json",
        data: JSON.stringify(formToJsonString($("#addDepartmentForm"))),
        success: function(data) {
          resetFeedbackMessages();
          resetAddDepartmentForm();
          refreshDepartments();
        },
        error: function(xhr, status, error) {
          resetFeedbackMessages();
          showErrorMessages($.parseJSON(xhr.responseText));
        }
    });
    e.preventDefault();
  });

  refreshDepartments();
});

function refreshDepartments() {
  $.getJSON(
	  "/departments",
	  function(data) {
        let table = $("#departmentsTable");
        table.find("tbody tr").remove();
        for(let i = 0; i < data.departments.length; i++) {
          let tr = $('<tr class="department" />');
          tr.append("<td>" + data.departments[i].name + "</td>");
          table.find("tbody").append(tr);
        }
      }
  );
}

function formToJsonString(form) {
  let jsonData = {};
  form.serializeArray().map(function(x) { 
    jsonData[x.name] = x.value;
  });
  return jsonData;
}

function resetFeedbackMessages() {
  $("#feedbackMessages").find("p").remove();
}

function resetAddDepartmentForm() {
  document.getElementById("addDepartmentForm").reset();
}

function showErrorMessages(errorJson) {
  let title = $("<p>" + errorJson.errorMessage + "</p>");
  if(errorJson.details.length > 0) {
    let details = $('<ul></ul>');
    for(let i = 0; i < errorJson.details.length; i++) {
      details.append("<li>" + errorJson.details[i] + "</li>");
    }
    title.append(details);
  }
  $("#feedbackMessages").append(title);
}
