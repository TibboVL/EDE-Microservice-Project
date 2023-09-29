package fact.it.libraryservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
@Document(value = "podcast")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Podcast {
    @Id
    private String id;
    private String title;
    private String description;
    private String authorId;
    private Date releaseDate;
    private String path; // Path where the podcast is stored
    private String coverArt;
    private Integer plays;
}
