package fact.it.libraryservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;


@Document(value = "song")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Song {
    @Id
    private String id;
    private String title;
    private String artist;
    private String album;
    private Genre genre;
    private Integer duration; // represented in seconds
    private Date releaseDate;
    private String path; // path where song is stored
    private String coverArt;
    private Integer plays;
    private Integer likes;
}
