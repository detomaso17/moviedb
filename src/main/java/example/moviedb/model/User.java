package example.moviedb.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
@Data
@SequenceGenerator(name = "user_seq", sequenceName = "user_seq", initialValue = 1)
public class User {

    @Id
    @GeneratedValue(generator = "user_seq")
    private Long id;

    private String username;
    private String password;
    private Boolean enabled;
}
