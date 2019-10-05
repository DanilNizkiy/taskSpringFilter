package example.model;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;

@Data
@Entity
@Table(name = "contactsName22")
public class Contact {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private long id;

    private String name;

    public Contact() {

    }

    public Contact(String name) {
        this.name = name;
    }


}