package fact.it.playlistservice.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(value = "partialSong")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class PartialSong {
    @Id
    private String id;
    private String title;
    private int duration; // Duration in seconds
}
