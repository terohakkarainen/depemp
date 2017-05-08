$(document).ready(function() {

  $("#updateLink").click(function(event) {
    updateDepartmentList();
    event.preventDefault();
  });

  updateDepartmentList();
});

function updateDepartmentList() {
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
