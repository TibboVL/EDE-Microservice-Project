package fact.it.playlistservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(value = "playlist")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Playlist {
    @Id
    private String id;
    private String userId;
    private String name;
    private String description;
    private List<String> songIds; // List of song IDs associated with the playlist (stored as strings)

}
