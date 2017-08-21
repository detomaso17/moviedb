package example.moviedb.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "movies")
@Data
@RequiredArgsConstructor
@NoArgsConstructor
public class Movie {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @ManyToOne
    private User user;

    @NonNull
    private String title;
    private String description;
    @NonNull
    private Boolean watched;
}
