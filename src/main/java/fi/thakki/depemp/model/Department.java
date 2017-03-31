package fi.thakki.depemp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Department {

	private Long myId;
	private String myName;
	private String myDescription;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	public Long getId() {
		return myId;
	}
	
	public void setId(final Long id) {
		myId = id;
	}

	@Column(name="name", nullable=false, length=LengthDomain.SHORT_DESCRIPTION)
	public String getName() {
		return myName;
	}

	public void setName(final String name) {
		myName = name;
	}

	@Column(name="description", length=LengthDomain.LONG_DESCRIPTION)
	public String getDescription() {
		return myDescription;
	}
	
	public void setDescription(final String description) {
		myDescription = description;
	}
}
