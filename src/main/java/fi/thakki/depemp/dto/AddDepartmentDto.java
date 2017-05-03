package fi.thakki.depemp.dto;

public class AddDepartmentDto {

	private String myName;
	private String myDescription;

	public String getName() {
		return myName;
	}

	public void setName(String name) {
		myName = name;
	}

	public String getDescription() {
		return myDescription;
	}

	public void setDescription(String description) {
		myDescription = description;
	}
}
