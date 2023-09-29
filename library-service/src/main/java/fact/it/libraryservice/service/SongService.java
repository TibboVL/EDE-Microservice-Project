package fact.it.libraryservice.service;


import fact.it.libraryservice.dto.SongRequest;
import fact.it.libraryservice.dto.SongResponse;
import fact.it.libraryservice.model.Song;
import fact.it.libraryservice.repository.SongRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SongService {

    private final SongRepository songRepository;

    public ResponseEntity<SongResponse> createSong(SongRequest songRequest) {
        Song song = Song.builder()
                .title(songRequest.getTitle())
                .album(songRequest.getAlbum())
                .genre(songRequest.getGenre())
                .duration(songRequest.getDuration())
                .releaseDate(songRequest.getReleaseDate())
                .path(songRequest.getPath())
                .coverArt(songRequest.getCoverArt())
                .plays(songRequest.getPlays())
                .likes(songRequest.getLikes())
                .build();

        songRepository.save(song);
        return new ResponseEntity<>(mapToSongResponse(song), HttpStatus.CREATED);
    }

    public ResponseEntity<SongResponse> getSong(String songId) {
        Optional<Song> optionalSong = songRepository.findById(songId);
        if (optionalSong.isPresent()) {
            return new ResponseEntity<>(mapToSongResponse(optionalSong.get()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<SongResponse> updateSong(String songId, SongRequest songRequest) {
        Optional<Song> optionalSong = songRepository.findById(songId);
        if (optionalSong.isPresent()) {
            Song song = optionalSong.get();
            song.setTitle(songRequest.getTitle());
            song.setAlbum(songRequest.getAlbum());
            song.setGenre(songRequest.getGenre());
            song.setDuration(songRequest.getDuration());
            song.setReleaseDate(songRequest.getReleaseDate());
            song.setPath(songRequest.getPath());
            song.setCoverArt(songRequest.getCoverArt());
            song.setPlays(songRequest.getPlays());
            song.setLikes(songRequest.getLikes());
            return new ResponseEntity<>(mapToSongResponse(optionalSong.get()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<SongResponse> deleteSong(String songId) {
        Optional<Song> optionalSong = songRepository.findById(songId);
        if (optionalSong.isPresent()) {
            songRepository.deleteById(songId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public List<SongResponse> getAllSongs() {
        List<Song> songs = songRepository.findAll();
        return songs.stream().map(this::mapToSongResponse).toList();
    }

    private SongResponse mapToSongResponse(Song song) {
        return SongResponse.builder()
                .id(song.getId())
                .title(song.getTitle())
                .album(song.getAlbum())
                .genre(song.getGenre())
                .duration(song.getDuration())
                .releaseDate(song.getReleaseDate())
                .path(song.getPath())
                .coverArt(song.getCoverArt())
                .plays(song.getPlays())
                .likes(song.getLikes())
                .build();
    }
}
