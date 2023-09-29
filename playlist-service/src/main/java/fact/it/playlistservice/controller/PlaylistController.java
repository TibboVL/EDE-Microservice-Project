package fact.it.playlistservice.controller;

import fact.it.playlistservice.dto.PlaylistRequest;
import fact.it.playlistservice.dto.PlaylistResponse;
import fact.it.playlistservice.service.PlaylistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/playlist")
@RequiredArgsConstructor
public class PlaylistController {

    private final PlaylistService playlistService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<PlaylistResponse> getAllSongs() {
        return playlistService.getAllSongs();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void createSong(@RequestBody PlaylistRequest songRequest) {
        playlistService.createSong(songRequest);
    }
}
