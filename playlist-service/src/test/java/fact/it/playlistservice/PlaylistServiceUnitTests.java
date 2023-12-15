package fact.it.playlistservice;


import fact.it.playlistservice.dto.PlaylistRequest;
import fact.it.playlistservice.dto.PlaylistResponse;
import fact.it.playlistservice.dto.SongResponse;
import fact.it.playlistservice.model.PartialSong;
import fact.it.playlistservice.model.Playlist;
import fact.it.playlistservice.repository.PlaylistRepository;
import fact.it.playlistservice.service.PlaylistService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PlaylistServiceUnitTests {

    @InjectMocks
    private PlaylistService playlistService;

    @Mock
    private PlaylistRepository playlistRepository;

    @Mock
    private WebClient webClient;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(playlistService, "libraryServiceBaseUrl", "http://localhost:8082");
    }

    @Test
    public void testCreatePlaylist_Success() {
        // Arrange
        PlaylistRequest playlistRequest = new PlaylistRequest();
        playlistRequest.setName("Test Playlist");
        playlistRequest.setUserId("1");
        playlistRequest.setDescription("Test Description");
        playlistRequest.setIsPublic(false);

        // Act
        ResponseEntity<PlaylistResponse> responseEntity = playlistService.createPlaylist(playlistRequest);

        // Assert
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(playlistRequest.getName(), responseEntity.getBody().getName());
        assertEquals(playlistRequest.getUserId(), responseEntity.getBody().getUserId());
        assertEquals(playlistRequest.getDescription(), responseEntity.getBody().getDescription());
        assertFalse(responseEntity.getBody().isPublic()); // by default a new playlist should be private
        verify(playlistRepository, times(1)).save(any(Playlist.class));
    }

    @Test
    public void testCreateMyFavoritesWithNoExistingFavorites_success() {
        // Arrange
        String userId = "1";

        // Act
        ResponseEntity<PlaylistResponse> responseEntity = playlistService.createMyFavorites(userId);

        // Assert
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        verify(playlistRepository, times(1)).save(any(Playlist.class));
        verify(playlistRepository, times(1)).findAllByUserId(userId);
    }

    @Test
    public void testCreateMyFavoritesWithExistingFavorites_failure() {
        // Arrange
        String userId = "1";
        List<Playlist> existingPlaylists = new ArrayList<>();
        existingPlaylists.add(Playlist.builder().name("test").isPublic(false).isFavorite(true).build());

        // Mocking the behavior of the getUserPlaylists method
        when(playlistRepository.findAllByUserId(userId)).thenReturn(existingPlaylists);
        // Act
        ResponseEntity<PlaylistResponse> responseEntity = playlistService.createMyFavorites(userId);

        // Assert
        assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
        verify(playlistRepository, times(0)).save(any(Playlist.class));
        verify(playlistRepository, times(1)).findAllByUserId(userId);
    }

    @Test
    public void testGetPlaylistWithExistingPlaylist_ReturnsPlaylist() {
        // Arrange
        String playlistId = "123";
        Playlist playlist = new Playlist();
        playlist.setId(playlistId);
        playlist.setIsPublic(true);
        when(playlistRepository.findById(playlistId)).thenReturn(Optional.of(playlist));

        // Act
        ResponseEntity<PlaylistResponse> responseEntity = playlistService.getPlaylist(playlistId);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(playlist.getId(), responseEntity.getBody().getId());
        verify(playlistRepository, times(1)).findById(playlistId);
    }

    @Test
    public void testGetUserPlaylistsWithExistingPlaylists() {
        // Arrange
        String userId = "user123";
        List<Playlist> playlists = new ArrayList<>();
        Playlist playlist = Playlist.builder().isPublic(true).build();
        playlists.add(playlist);
        when(playlistRepository.findAllByUserId(userId)).thenReturn(playlists);

        // Act
        ResponseEntity<List<PlaylistResponse>> responseEntity = playlistService.getUserPlaylists(userId);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(playlists.size(), responseEntity.getBody().size());
        verify(playlistRepository, times(1)).findAllByUserId(userId);
    }

    @Test
    public void testGetUserPlaylistsWithNotExistingPlaylists() {
        // Arrange
        String userId = "user123";

        // Simulating the case when the user playlists are not found
        when(playlistRepository.findAllByUserId(userId)).thenReturn(Collections.emptyList());

        // Act
        ResponseEntity<List<PlaylistResponse>> responseEntity = playlistService.getUserPlaylists(userId);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        verify(playlistRepository, times(1)).findAllByUserId(userId);
    }

    @Test
    public void testUpdatePlaylistWithExistingPlaylist() {
        // Arrange
        String playlistId = "123";
        PlaylistRequest playlistRequest = new PlaylistRequest();
        playlistRequest.setUserId("user123");
        playlistRequest.setName("Updated Playlist");
        playlistRequest.setDescription("Updated Description");
        playlistRequest.setIsPublic(true);

        Playlist existingPlaylist = new Playlist();
        existingPlaylist.setId(playlistId);
        when(playlistRepository.findById(playlistId)).thenReturn(Optional.of(existingPlaylist));

        // Act
        ResponseEntity<PlaylistResponse> responseEntity = playlistService.updatePlaylist(playlistId, playlistRequest);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(playlistRequest.getName(), responseEntity.getBody().getName());
        assertEquals(playlistRequest.getDescription(), responseEntity.getBody().getDescription());
        assertEquals(playlistRequest.getIsPublic(), responseEntity.getBody().isPublic());

        verify(playlistRepository, times(1)).findById(playlistId);
        verify(playlistRepository, times(1)).save(any(Playlist.class));
    }

    @Test
    public void testUpdatePlaylistWithNotExistingPlaylist() {
        // Arrange
        String playlistId = "123";
        PlaylistRequest playlistRequest = new PlaylistRequest();
        playlistRequest.setUserId("user123");
        playlistRequest.setName("Updated Playlist");
        playlistRequest.setDescription("Updated Description");
        playlistRequest.setIsPublic(true);

        // Simulating the case when the playlist is not found
        when(playlistRepository.findById(playlistId)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<PlaylistResponse> responseEntity = playlistService.updatePlaylist(playlistId, playlistRequest);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        verify(playlistRepository, times(1)).findById(playlistId);
        verify(playlistRepository, times(0)).save(any(Playlist.class));
    }

    @Test
    public void testAddSongWithExistingPlaylistAndSong() {
        // Arrange
        String playlistId = "123";
        String songId = "456";
        Playlist playlist = Playlist.builder().isPublic(false).build();
        playlist.setId(playlistId);
        playlist.setSongs(new ArrayList<>()); // Initialize the songs list here
        when(playlistRepository.findById(playlistId)).thenReturn(Optional.of(playlist));

        SongResponse songResponse = new SongResponse();
        songResponse.setId(songId);
        songResponse.setDuration(5);
        songResponse.setTitle("test");
        WebClient.RequestHeadersUriSpec requestHeadersUriSpecMock = Mockito.mock(WebClient.RequestHeadersUriSpec.class);
        WebClient.RequestHeadersSpec requestHeadersSpecMock = Mockito.mock(WebClient.RequestHeadersSpec.class);
        WebClient.ResponseSpec responseSpecMock = Mockito.mock(WebClient.ResponseSpec.class);

        when(webClient.get()).thenReturn(requestHeadersUriSpecMock);
        when(requestHeadersUriSpecMock.uri(anyString())).thenReturn(requestHeadersSpecMock);
        when(requestHeadersSpecMock.retrieve()).thenReturn(responseSpecMock);
        when(responseSpecMock.bodyToMono(SongResponse.class)).thenReturn(Mono.just(songResponse));


        // Act
        ResponseEntity<PlaylistResponse> responseEntity = playlistService.addSong(playlistId, songId);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(1, responseEntity.getBody().getSongs().size());

        verify(playlistRepository, times(1)).findById(playlistId);
        verify(playlistRepository, times(1)).save(any(Playlist.class));
    }

    @Test
    public void testAddSongWithNotExistingSong() {
        // Arrange
        String playlistId = "123";
        String songId = "456";
        Playlist playlist = Playlist.builder().isPublic(false).build();
        playlist.setId(playlistId);
        playlist.setSongs(new ArrayList<>()); // Initialize the songs list here
        when(playlistRepository.findById(playlistId)).thenReturn(Optional.of(playlist));

        WebClient.RequestHeadersUriSpec requestHeadersUriSpecMock = Mockito.mock(WebClient.RequestHeadersUriSpec.class);
        WebClient.RequestHeadersSpec requestHeadersSpecMock = Mockito.mock(WebClient.RequestHeadersSpec.class);
        WebClient.ResponseSpec responseSpecMock = Mockito.mock(WebClient.ResponseSpec.class);

        // Simulating the song not found in the response from the WebClient
        when(webClient.get()).thenReturn(requestHeadersUriSpecMock);
        when(requestHeadersUriSpecMock.uri(anyString())).thenReturn(requestHeadersSpecMock);
        when(requestHeadersSpecMock.retrieve()).thenReturn(responseSpecMock);
        when(responseSpecMock.bodyToMono(SongResponse.class)).thenReturn(Mono.empty()); // Simulating the case when the song is not found

        // Act
        ResponseEntity<PlaylistResponse> responseEntity = playlistService.addSong(playlistId, songId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        verify(playlistRepository, times(0)).save(any(Playlist.class));
    }


    @Test
    public void testRemoveSongWithExistingPlaylistAndSong() {
        // Arrange
        String playlistId = "123";
        String songId = "456";
        Playlist playlist = new Playlist();
        playlist.setId(playlistId);
        playlist.setIsPublic(true);
        playlist.setSongs(new ArrayList<>()); // Initialize the songs list here
        PartialSong partialSong = new PartialSong();
        partialSong.setSongId(songId);
        playlist.getSongs().add(partialSong);
        when(playlistRepository.findById(playlistId)).thenReturn(Optional.of(playlist));

        // Act
        ResponseEntity<PlaylistResponse> responseEntity = playlistService.removeSong(playlistId, songId);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        assertEquals(0, responseEntity.getBody().getSongs().size());

        verify(playlistRepository, times(1)).findById(playlistId);
        verify(playlistRepository, times(1)).save(any(Playlist.class));
    }

    @Test
    public void testRemoveSongWithNotExistingPlaylistOrSong() {
        // Arrange
        String playlistId = "123";
        String songId = "456";
        Playlist playlist = new Playlist();
        playlist.setId(playlistId);
        playlist.setIsPublic(true);
        playlist.setSongs(new ArrayList<>()); // Initialize the songs list here
        PartialSong partialSong = new PartialSong();
        partialSong.setSongId(songId);
        playlist.getSongs().add(partialSong);

        // Simulating playlist not found in the repository
        when(playlistRepository.findById(playlistId)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<PlaylistResponse> responseEntity = playlistService.removeSong(playlistId, songId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        verify(playlistRepository, times(0)).save(any(Playlist.class));
    }


    @Test
    public void testDeletePlaylistWithExistingPlaylist() {
        // Arrange
        String playlistId = "123";
        Playlist playlist = new Playlist();
        playlist.setId(playlistId);
        when(playlistRepository.findById(playlistId)).thenReturn(Optional.of(playlist));

        // Act
        ResponseEntity<PlaylistResponse> responseEntity = playlistService.deletePlaylist(playlistId);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        verify(playlistRepository, times(1)).deleteById(playlistId);

        verify(playlistRepository, times(1)).findById(playlistId);
        verify(playlistRepository, times(1)).deleteById(playlistId);
    }

    @Test
    public void testDeletePlaylistWithNotExistingPlaylist() {
        // Arrange
        String playlistId = "123";
        when(playlistRepository.findById(playlistId)).thenReturn(Optional.empty()); // Simulating the playlist not being found

        // Act
        ResponseEntity<PlaylistResponse> responseEntity = playlistService.deletePlaylist(playlistId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        verify(playlistRepository, times(0)).deleteById(playlistId);
    }

    @Test
    public void testGetAllPlaylists() {
        // Arrange
        List<Playlist> playlists = new ArrayList<>();
        playlists.add(Playlist.builder().isPublic(true).build());
        when(playlistRepository.findAll()).thenReturn(playlists);

        // Act
        List<PlaylistResponse> playlistResponses = playlistService.getAllPlaylists();

        // Assert
        assertEquals(playlists.size(), playlistResponses.size());

        verify(playlistRepository, times(1)).findAll();
    }
}
