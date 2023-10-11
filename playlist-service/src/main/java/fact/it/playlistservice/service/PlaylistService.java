package fact.it.playlistservice.service;


import fact.it.playlistservice.dto.PartialSongResponse;
import fact.it.playlistservice.dto.PlaylistRequest;
import fact.it.playlistservice.dto.PlaylistResponse;
import fact.it.playlistservice.dto.SongResponse;
import fact.it.playlistservice.model.PartialSong;
import fact.it.playlistservice.model.Playlist;
import fact.it.playlistservice.repository.PlaylistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PlaylistService {

    private final PlaylistRepository playlistRepository;
    private final WebClient webClient;

    @Value("${libraryservice.baseurl}")
    private String libraryServiceBaseUrl;

    public ResponseEntity<PlaylistResponse> createPlaylist(PlaylistRequest playlistRequest) {
        Playlist playlist = Playlist.builder()
                .name(playlistRequest.getName())
                .userId(playlistRequest.getUserId())
                .description(playlistRequest.getDescription())
                .isPublic(Boolean.FALSE)
                .songs(new ArrayList<>())
                .build();

        playlistRepository.save(playlist);
        return new ResponseEntity<>(mapToPlaylistResponse(playlist), HttpStatus.CREATED);
    }

    public ResponseEntity<String> createMyFavorites(String userId) {
        List<PlaylistResponse> userPlaylists = getUserPlaylists(userId).getBody();

        boolean hasFavorites = true;
        if (userPlaylists == null) {
            hasFavorites = false;
        } else if (userPlaylists.stream().anyMatch(playlistResponse ->
                playlistResponse.getIsFavorite() != null && playlistResponse.getIsFavorite())) {
            hasFavorites = false;
        }

        // if user has no favorite playlist add one else send forbidden
        if (!hasFavorites) {
            Playlist playlist = Playlist.builder()
                    .name("My favorites")
                    .userId(userId)
                    .description("")
                    .isPublic(false)
                    .isFavorite(true)
                    .songs(new ArrayList<>())
                    .build();
            playlistRepository.save(playlist);
            return new ResponseEntity<>(playlist.getId(), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    public ResponseEntity<PlaylistResponse> getPlaylist(String playlistId) {
        Optional<Playlist> optionalPlaylist = playlistRepository.findById(playlistId);
        if (optionalPlaylist.isPresent()) {
            return new ResponseEntity<>(mapToPlaylistResponse(optionalPlaylist.get()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<List<PlaylistResponse>> getUserPlaylists(String userId) {
        List<Playlist> playlists = playlistRepository.findAllByUserId(userId);
        if (playlists.size() < 1) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(playlists.stream().map(playlist -> mapToPlaylistResponse(playlist)).toList(), HttpStatus.OK);
    }

    public ResponseEntity<PlaylistResponse> updatePlaylist(String playlistId, PlaylistRequest playlistRequest) {
        Optional<Playlist> optionalPlaylist = playlistRepository.findById(playlistId);
        if (optionalPlaylist.isPresent()) {
            Playlist playlist = optionalPlaylist.get();
            playlist.setUserId(playlistRequest.getUserId());
            playlist.setName(playlistRequest.getName());
            playlist.setDescription(playlistRequest.getDescription());
            playlist.setIsPublic(playlistRequest.getIsPublic());
//            song.setSongIds(playlistRequest.getSongIds());
            return new ResponseEntity<>(mapToPlaylistResponse(optionalPlaylist.get()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<PlaylistResponse> addSong(String playlistId, String songId) {
        Optional<Playlist> optionalPlaylist = playlistRepository.findById(playlistId);
        if (optionalPlaylist.isPresent()) {
            SongResponse optionalSong = webClient.get()
                    .uri("http://" + libraryServiceBaseUrl + "/api/library/song/" + songId)
                    .retrieve()
                    .bodyToMono(SongResponse.class)
                    .block();
            if (optionalSong != null) {
                PartialSong partialSong = PartialSong.builder()
                        .id(optionalSong.getId())
                        .title(optionalSong.getTitle())
                        .duration(optionalSong.getDuration())
                        .build();
                Playlist playlist = optionalPlaylist.get();
                playlist.getSongs().add(partialSong);
                playlistRepository.save(playlist);
            }
            return new ResponseEntity<>(mapToPlaylistResponse(optionalPlaylist.get()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<PlaylistResponse> removeSong(String playlistId, String songId) {
        Optional<Playlist> optionalPlaylist = playlistRepository.findById(playlistId);
        if (optionalPlaylist.isPresent()) {

            Playlist playlist = optionalPlaylist.get();
            playlist.getSongs().removeIf(partialSong -> partialSong.getId().equals(songId));
            playlistRepository.save(playlist);

            return new ResponseEntity<>(mapToPlaylistResponse(optionalPlaylist.get()), HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<PlaylistResponse> deletePlaylist(String playlistId) {
        Optional<Playlist> optionalSong = playlistRepository.findById(playlistId);
        if (optionalSong.isPresent()) {
            playlistRepository.deleteById(playlistId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    public List<PlaylistResponse> getAllPlaylists() {
        List<Playlist> playlists = playlistRepository.findAll();
        return playlists.stream().map(this::mapToPlaylistResponse).toList();
    }

    private PlaylistResponse mapToPlaylistResponse(Playlist playlist) {
        return PlaylistResponse.builder()
                .id(playlist.getId())
                .userId(playlist.getUserId())
                .name(playlist.getName())
                .description(playlist.getDescription())
                .songs(playlist.getSongs())
                .build();
    }



}
