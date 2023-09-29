package fact.it.playlistservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlaylistResponse {
    private String id;
    private String userId;
    private String name;
    private String description;
    private List<String> songIds; // List of song IDs associated with the playlist (stored as strings)

}
