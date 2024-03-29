package fact.it.playlistservice.dto;

import fact.it.playlistservice.model.ListType;
import fact.it.playlistservice.model.PartialSong;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlaylistRequest {
    private String name;
    private String userId;
    private String description;
    private Boolean isPublic;
    private Boolean isFavorite;
    private ListType listType;
    private List<PartialSong> songs; // List of song IDs associated with the playlist (stored as strings)

}
