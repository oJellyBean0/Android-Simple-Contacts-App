package s213329913.wrap302.nmmu.assignment1_task2;

import java.io.Serializable;

/**
 * Created by s2133 on 2017/07/30.
 */

public class Contact implements Serializable {

    public String firstName;
    public String surname;
    public String cellNumber;
    public String email;

    public Contact(String firstName, String surname, String cellNumber, String email) {
        this.firstName = firstName;
        this.surname = surname;
        this.cellNumber = cellNumber;
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Contact contact = (Contact) o;

        if (!firstName.equals(contact.firstName)) return false;
        if (!surname.equals(contact.surname)) return false;
        if (!cellNumber.equals(contact.cellNumber)) return false;
        return email.equals(contact.email);

    }

    @Override
    public int hashCode() {
        int result = firstName.hashCode();
        result = 31 * result + surname.hashCode();
        result = 31 * result + cellNumber.hashCode();
        result = 31 * result + email.hashCode();
        return result;
    }
}
