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
          document.getElementById("addDepartmentForm").reset();
          refreshDepartments();
        },
        error: function(xhr, status, error) {
          var json = $.parseJSON(xhr.responseText);
          alert(json.errorMessage);
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
        let tr;
        let table = $("#departmentsTable");
        table.find("tbody tr").remove();
        for(let i = 0; i < data.departments.length; i++) {
          tr = $('<tr/>');
          tr.append("<td>" + data.departments[i].name + "</td>");
          table.find("tbody").append(tr);
        }
      }
  );
}

function formToJsonString(form) {
  var jsonData = {};
  form.serializeArray().map(function(x) { 
    jsonData[x.name] = x.value;
  });
  return jsonData;
}
