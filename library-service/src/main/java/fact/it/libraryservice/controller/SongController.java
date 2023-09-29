package fact.it.libraryservice.controller;

import fact.it.libraryservice.dto.SongRequest;
import fact.it.libraryservice.dto.SongResponse;
import fact.it.libraryservice.service.SongService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/songs")
@RequiredArgsConstructor
public class SongController {

    private final SongService songService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<SongResponse> getAllSongs() {
        return songService.getAllSongs();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void createSong(@RequestBody SongRequest songRequest) {
        songService.createSong(songRequest);
    }
}
