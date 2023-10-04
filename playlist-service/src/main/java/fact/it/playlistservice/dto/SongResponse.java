package fact.it.playlistservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SongResponse {
    private String id;
    private String title;
    private String artist;
    private String album;
    private String genre;
    private Integer duration; // represented in seconds
    private Date releaseDate;
    private String path; // path where song is stored
    private String coverArt;
    private Integer plays;
    private Integer likes;
}
