package fact.it.playlistservice.service;


import fact.it.playlistservice.dto.PlaylistRequest;
import fact.it.playlistservice.dto.PlaylistResponse;
import fact.it.playlistservice.model.Playlist;
import fact.it.playlistservice.repository.PlaylistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PlaylistService {

    private final PlaylistRepository playlistRepository;

    public ResponseEntity<PlaylistResponse> createPlaylist(PlaylistRequest playlistRequest) {
        Playlist playlist = Playlist.builder()
                .name(playlistRequest.getName())
                .userId(playlistRequest.getUserId())
                .description(playlistRequest.getDescription())
                .songIds(playlistRequest.getSongIds())
                .build();

        playlistRepository.save(playlist);
        return new ResponseEntity<>(mapToPlaylistResponse(playlist), HttpStatus.CREATED);
    }

    public ResponseEntity<PlaylistResponse> getPlaylist(String songId) {
        Optional<Playlist> optionalPlaylist = playlistRepository.findById(songId);
        if (optionalPlaylist.isPresent()) {
            return new ResponseEntity<>(mapToPlaylistResponse(optionalPlaylist.get()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<PlaylistResponse> updatePlaylist(String songId, PlaylistRequest playlistRequest) {
        Optional<Playlist> optionalPlaylist = playlistRepository.findById(songId);
        if (optionalPlaylist.isPresent()) {
            Playlist song = optionalPlaylist.get();
            song.setUserId(playlistRequest.getUserId());
            song.setName(playlistRequest.getName());
            song.setDescription(playlistRequest.getDescription());
            song.setSongIds(playlistRequest.getSongIds());
            return new ResponseEntity<>(mapToPlaylistResponse(optionalPlaylist.get()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<PlaylistResponse> deletePlaylist(String songId) {
        Optional<Playlist> optionalSong = playlistRepository.findById(songId);
        if (optionalSong.isPresent()) {
            playlistRepository.deleteById(songId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    public List<PlaylistResponse> getAllPlaylists() {
        List<Playlist> songs = playlistRepository.findAll();
        return songs.stream().map(this::mapToPlaylistResponse).toList();
    }

    private PlaylistResponse mapToPlaylistResponse(Playlist playlist) {
        return PlaylistResponse.builder()
                .id(playlist.getId())
                .userId(playlist.getUserId())
                .name(playlist.getName())
                .description(playlist.getDescription())
                .songIds(playlist.getSongIds())
                .build();
    }
}
