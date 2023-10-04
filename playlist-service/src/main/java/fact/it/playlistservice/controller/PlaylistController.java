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
    public ResponseEntity<PlaylistResponse> getPlaylist(@PathVariable("id") String playlistId) {
        return playlistService.getPlaylist(playlistId);
    }
    @GetMapping("/user/{id}")
    public ResponseEntity<List<PlaylistResponse>> getPlaylistFromUser(@PathVariable("id") String userId) {
        return playlistService.getUserPlaylists(userId);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlaylistResponse> updatePlaylist(@PathVariable("id") String playlistId, @RequestBody PlaylistRequest playlistRequest) {
        return playlistService.updatePlaylist(playlistId, playlistRequest);
    }

    @PutMapping("/{id}/{songId}")
    public ResponseEntity<PlaylistResponse> addSong(@PathVariable("id") String playlistId, @PathVariable("songId") String songId) {
        return playlistService.addSong(playlistId, songId);
    }

    @DeleteMapping("/{id}/{songId}")
    public ResponseEntity<PlaylistResponse> removeSong(@PathVariable("id") String playlistId, @PathVariable("songId") String songId) {
        return playlistService.removeSong(playlistId, songId);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PlaylistResponse> deletePlaylist(@PathVariable("id") String playlistId) {
        return playlistService.deletePlaylist(playlistId);
    }


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<PlaylistResponse> getAllSongs() {
        return playlistService.getAllPlaylists();
    }
}
