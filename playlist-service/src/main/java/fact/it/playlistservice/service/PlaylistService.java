package fact.it.playlistservice.service;


import fact.it.playlistservice.dto.PlaylistRequest;
import fact.it.playlistservice.dto.PlaylistResponse;
import fact.it.playlistservice.model.Playlist;
import fact.it.playlistservice.repository.PlaylistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlaylistService {

    private final PlaylistRepository playlistRepository;

    public void createSong(PlaylistRequest songRequest) {
        Playlist playlist = Playlist.builder()
                .name(songRequest.getName())
                .userId(songRequest.getUserId())
                .description(songRequest.getDescription())
                .songIds(songRequest.getSongIds())
                .build();

        playlistRepository.save(playlist);
    }

    public List<PlaylistResponse> getAllSongs() {
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
