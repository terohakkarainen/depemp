package fi.thakki.depemp.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Department {

	private Long myId;
	private String myName;
	
	@Id
	public Long getId() {
		return myId;
	}
	
	public void setId(final Long id) {
		myId = id;
	}
	
	public String getName() {
		return myName;
	}

	public void setName(final String name) {
		myName = name;
	}
}
