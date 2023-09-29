package fact.it.playlistservice.controller;

import fact.it.playlistservice.dto.PlaylistRequest;
import fact.it.playlistservice.dto.PlaylistResponse;
import fact.it.playlistservice.service.PlaylistService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/playlist")
@RequiredArgsConstructor
public class PlaylistController {

    private final PlaylistService playlistService;

    @PostMapping
    public ResponseEntity<PlaylistResponse> createPlaylist(@RequestBody PlaylistRequest playlistRequest) {
        return playlistService.createPlaylist(playlistRequest);
    }
    @GetMapping("/{id}")
    public ResponseEntity<PlaylistResponse> getPlaylist(@PathVariable("id") String songId) {
        return playlistService.getPlaylist(songId);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlaylistResponse> updatePlaylist(@PathVariable("id") String songId, @RequestBody PlaylistRequest playlistRequest) {
        return playlistService.updatePlaylist(songId, playlistRequest);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PlaylistResponse> deletePlaylist(@PathVariable("id") String songId) {
        return playlistService.deletePlaylist(songId);
    }


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<PlaylistResponse> getAllSongs() {
        return playlistService.getAllPlaylists();
    }
}
