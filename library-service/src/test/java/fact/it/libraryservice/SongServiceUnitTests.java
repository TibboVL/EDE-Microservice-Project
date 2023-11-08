package fact.it.libraryservice;

import fact.it.libraryservice.dto.SongRequest;
import fact.it.libraryservice.dto.SongResponse;
import fact.it.libraryservice.model.Genre;
import fact.it.libraryservice.model.Song;
import fact.it.libraryservice.repository.SongRepository;
import fact.it.libraryservice.service.SongService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SongServiceUnitTests {

    @InjectMocks
    private SongService songService;

    @Mock
    private SongRepository songRepository;

    @Mock
    private WebClient webClient;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(songService, "ratingServiceBaseUrl", "http://localhost:8084");
        ReflectionTestUtils.setField(songService, "playlistServiceBaseUrl", "http://localhost:8083");
    }


    @Test
    public void testCreateSong_Success() {
        // Arrange
        SongRequest songRequest = new SongRequest();
        songRequest.setTitle("Test Song");
        songRequest.setAlbum("Test Album");
        songRequest.setGenre(Genre.POP);
        songRequest.setDuration(180);
        songRequest.setReleaseDate(new Date());
        songRequest.setPath("/test/path");
        songRequest.setCoverArt("test cover");
        songRequest.setPlays(0);
        songRequest.setLikes(0);

        // Act
        ResponseEntity<SongResponse> responseEntity = songService.createSong(songRequest);

        // Assert
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(songRequest.getTitle(), responseEntity.getBody().getTitle());
        assertEquals(songRequest.getAlbum(), responseEntity.getBody().getAlbum());
        assertEquals(songRequest.getGenre(), responseEntity.getBody().getGenre());
        assertEquals(songRequest.getDuration(), responseEntity.getBody().getDuration());
        verify(songRepository, times(1)).save(any(Song.class));
    }

    @Test
    public void testGetSongWithExistingSong_ReturnsSong() {
        // Arrange
        String songId = "123";
        Song song = new Song();
        song.setId(songId);
        when(songRepository.findById(songId)).thenReturn(Optional.of(song));

        WebClient.RequestHeadersUriSpec requestHeadersUriSpecMock = Mockito.mock(WebClient.RequestHeadersUriSpec.class);
        WebClient.RequestHeadersSpec requestHeadersSpecMock = Mockito.mock(WebClient.RequestHeadersSpec.class);
        WebClient.ResponseSpec responseSpecMock = Mockito.mock(WebClient.ResponseSpec.class);

        when(webClient.get()).thenReturn(requestHeadersUriSpecMock);
        when(requestHeadersUriSpecMock.uri(anyString())).thenReturn(requestHeadersSpecMock);
        when(requestHeadersSpecMock.retrieve()).thenReturn(responseSpecMock);
        when(responseSpecMock.bodyToMono(Double.class)).thenReturn(Mono.just(5.0));

        // Act
        ResponseEntity<SongResponse> responseEntity = songService.getSong(songId);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(song.getId(), responseEntity.getBody().getId());
        verify(songRepository, times(1)).findById(songId);
    }

    @Test
    public void testUpdateSongWithExistingSong() {
        // Arrange
        String songId = "123";
        SongRequest songRequest = new SongRequest();
        songRequest.setTitle("Updated Song");
        songRequest.setAlbum("Updated Album");
        songRequest.setGenre(Genre.ROCK);
        songRequest.setDuration(240);
        songRequest.setReleaseDate(new Date());
        songRequest.setPath("/test/updatedPath");
        songRequest.setCoverArt("updated cover");
        songRequest.setPlays(10);
        songRequest.setLikes(5);

        Song existingSong = new Song();
        existingSong.setId(songId);
        when(songRepository.findById(songId)).thenReturn(Optional.of(existingSong));

        // Initialize the necessary variables and mocks
        WebClient.RequestBodySpec requestBodySpec = Mockito.mock(WebClient.RequestBodySpec.class);
        WebClient.RequestBodyUriSpec requestBodyUriSpec = Mockito.mock(WebClient.RequestBodyUriSpec.class);
        WebClient.RequestHeadersSpec requestHeadersSpec = Mockito.mock(WebClient.RequestHeadersSpec.class);
        WebClient.ResponseSpec responseSpec = Mockito.mock(WebClient.ResponseSpec.class);
        ClientResponse clientResponse = Mockito.mock(ClientResponse.class);

        // Configure the mock behavior
        when(webClient.put()).thenReturn(requestBodyUriSpec);
        when(requestBodyUriSpec.uri(anyString())).thenReturn(requestBodySpec);
        when(requestBodySpec.body(any(Mono.class), eq(SongResponse.class))).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.exchangeToMono(any(Function.class)))
                .thenReturn(Mono.just(clientResponse).flatMap(response -> {
                    if (response.statusCode().is2xxSuccessful()) {
                        return Mono.just(ResponseEntity.ok().build()); // Adjust the response type as needed
                    } else {
                        System.out.println(response.statusCode());
                        return Mono.error(new RuntimeException("Could not update partial songs!!"));
                    }
                }));

        // Act
        ResponseEntity<SongResponse> responseEntity = songService.updateSong(songId, songRequest);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(songRequest.getTitle(), responseEntity.getBody().getTitle());
        assertEquals(songRequest.getAlbum(), responseEntity.getBody().getAlbum());
        assertEquals(songRequest.getGenre(), responseEntity.getBody().getGenre());
        assertEquals(songRequest.getDuration(), responseEntity.getBody().getDuration());
        verify(songRepository, times(1)).findById(songId);
        verify(songRepository, times(1)).save(any(Song.class));
    }

    @Test
    public void testUpdateSongWithNotExistingSong() {
        // Arrange
        String songId = "123";
        SongRequest songRequest = new SongRequest();
        songRequest.setTitle("Updated Song");
        songRequest.setAlbum("Updated Album");
        songRequest.setGenre(Genre.ROCK);
        songRequest.setDuration(240);
        songRequest.setReleaseDate(new Date());
        songRequest.setPath("/test/updatedPath");
        songRequest.setCoverArt("updated cover");
        songRequest.setPlays(10);
        songRequest.setLikes(5);

        when(songRepository.findById(songId)).thenReturn(Optional.empty()); // Simulating the song not being found

        // Act
        ResponseEntity<SongResponse> responseEntity = songService.updateSong(songId, songRequest);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        verify(songRepository, times(1)).findById(songId);
        verify(songRepository, times(0)).save(any(Song.class));
    }


    @Test
    public void testDeleteSongWithExistingSong() {
        // Arrange
        String songId = "123";
        Song song = new Song();
        song.setId(songId);
        when(songRepository.findById(songId)).thenReturn(Optional.of(song));

        // Act
        ResponseEntity<SongResponse> responseEntity = songService.deleteSong(songId);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        verify(songRepository, times(1)).deleteById(songId);
    }
    @Test
    public void testDeleteSongWithNotExistingSong() {
        // Arrange
        String songId = "123";
        when(songRepository.findById(songId)).thenReturn(Optional.empty()); // Simulating the song not being found

        // Act
        ResponseEntity<SongResponse> responseEntity = songService.deleteSong(songId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        verify(songRepository, times(0)).deleteById(songId);
    }


    @Test
    public void testGetAllSongs() {
        // Arrange
        List<Song> songs = new ArrayList<>();
        songs.add(new Song());
        when(songRepository.findAll()).thenReturn(songs);

        // Act
        List<SongResponse> songResponses = songService.getAllSongs();

        // Assert
        assertEquals(songs.size(), songResponses.size());
        verify(songRepository, times(1)).findAll();
    }
}