const BACKEND_ERROR = "Unable to get response from backend server!";

$(document).ready(function() {

  $("#refreshDepartmentsLink").click(function(event) {
    _refreshDepartments();
    event.preventDefault();
  });

  $("#addDepartmentForm").submit(function(e) {
    $.ajax({
        type: "POST",
        url: "/departments",
        contentType: "application/json",
        data: JSON.stringify(_formToJsonString($("#addDepartmentForm"))),
        success: function(data) {
          _resetFeedbackMessages();
          _resetAddDepartmentForm();
          _refreshDepartments();
        },
        error: function(xhr, status, error) {
          _resetFeedbackMessages();
          if(_isUndefined(xhr.responseText)) {
            _showErrorMessage(BACKEND_ERROR);
          }
          else {
            _showErrorMessages($.parseJSON(xhr.responseText));
          }
        }
    });
    e.preventDefault();
  });

  $("#departmentsAccordion").accordion({
    header: "> div > h3"
  });

  _refreshDepartments();
});

function _isUndefined(v) {
  return typeof v == "undefined";
}

function _refreshDepartments() {
  $.getJSON(
      "/departments",
    function(data) {
        let accordion = $("#departmentsAccordion");
        accordion.find("div").remove();
        for(let i = 0; i < data.departments.length; i++) {
          let department = $('<div class="department" />');
          department.append("<h3>" + data.departments[i].name + "</h3>");
          department.append("<div><p>" + data.departments[i].description + "</p></div>");
          accordion.append(department);
        }
      }
  )
  .fail(function(xhr, textStatus, errorThrown) {
    _resetFeedbackMessages();
    _showErrorMessage(BACKEND_ERROR);
  })
  .done(function() {
    $("#departmentsAccordion").accordion("refresh");
  });
}

function _formToJsonString(form) {
  let jsonData = {};
  form.serializeArray().map(function(x) {
    jsonData[x.name] = x.value;
  });
  return jsonData;
}

function _resetFeedbackMessages() {
  $("#feedbackMessages").find("p").remove();
}

function _resetAddDepartmentForm() {
  document.getElementById("addDepartmentForm").reset();
}

function _showErrorMessages(errorJson) {
  let title = $('<p class="errorMessage">' + errorJson.errorMessage + '</p>');
  $("#feedbackMessages").append(title);
  if(errorJson.details.length > 0) {
    let details = $('<ul></ul>');
    for(let i = 0; i < errorJson.details.length; i++) {
      details.append('<li class="errorDetail">' + errorJson.details[i] + '</li>');
    }
    $("#feedbackMessages").append($('<p>').append(details));
  }
}

function _showErrorMessage(message) {
  let title = $('<p class="errorMessage">' + message + '</p>');
  $("#feedbackMessages").append(title);
}
