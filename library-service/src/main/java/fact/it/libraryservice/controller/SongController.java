package fact.it.libraryservice.controller;

import fact.it.libraryservice.dto.SongRequest;
import fact.it.libraryservice.dto.SongResponse;
import fact.it.libraryservice.service.SongService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;
import java.util.List;

@RestController
@RequestMapping("/api/library/song")
@RequiredArgsConstructor
public class SongController {

    private final SongService songService;

    // TODO add get ratings endpoint

    @PostMapping
    public ResponseEntity<SongResponse> createSong(@RequestBody SongRequest songRequest) {
        return songService.createSong(songRequest);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SongResponse> getSong(@PathVariable("id") String songId) {
        return songService.getSong(songId);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SongResponse> updateSong(@PathVariable("id") String songId, @RequestBody SongRequest songRequest) {
        return songService.updateSong(songId, songRequest);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SongResponse> deleteSong(@PathVariable("id") String songId) {
        return songService.deleteSong(songId);
    }

    // dev purposes
    @GetMapping("/all")
    public List<SongResponse> getAllSongs() {
        return songService.getAllSongs();
    }

    @GetMapping("/dummyData")
    public ResponseEntity<Long> addDummyData() {
        return songService.addDummyData();
    }
}
