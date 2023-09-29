package fact.it.libraryservice.service;


import fact.it.libraryservice.dto.SongRequest;
import fact.it.libraryservice.dto.SongResponse;
import fact.it.libraryservice.model.Song;
import fact.it.libraryservice.repository.SongRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SongService {

    private final SongRepository songRepository;

    public void createSong(SongRequest songRequest) {
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
