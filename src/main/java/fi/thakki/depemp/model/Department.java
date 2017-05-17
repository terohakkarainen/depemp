package fi.thakki.depemp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Department implements Identifiable {

    public static final int NAME_LENGTH = LengthDomain.SHORT_DESCRIPTION;
    public static final int DESCRIPTION_LENGTH = LengthDomain.LONG_DESCRIPTION;

    private Long myId;
    private String myName;
    private String myDescription;

    @Override
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return myId;
    }

    public void setId(
            final Long id) {
        myId = id;
    }

    @Column(name = "name", nullable = false, unique = true, length = NAME_LENGTH)
    public String getName() {
        return myName;
    }

    public void setName(
            final String name) {
        myName = name;
    }

    @Column(name = "description", length = DESCRIPTION_LENGTH)
    public String getDescription() {
        return myDescription;
    }

    public void setDescription(
            final String description) {
        myDescription = description;
    }
}
