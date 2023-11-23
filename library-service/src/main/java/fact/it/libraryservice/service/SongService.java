package fact.it.libraryservice.service;


import fact.it.libraryservice.dto.SongRequest;
import fact.it.libraryservice.dto.SongResponse;
import fact.it.libraryservice.model.Genre;
import fact.it.libraryservice.model.Song;
import fact.it.libraryservice.repository.SongRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.util.*;

@Service
@RequiredArgsConstructor
public class SongService {

    private final SongRepository songRepository;

    private final WebClient webClient;

    @Value("${ratingservice.baseurl}")
    private String ratingServiceBaseUrl;
    @Value("${playlistservice.baseurl}")
    private String playlistServiceBaseUrl;
    Random rand = new Random();
    public ResponseEntity<Long> addDummyData() {
        songRepository.deleteAll();
        int amount = 100;
        for (int i = 0; i < amount; i++) {
            SongRequest randomUser = generateRandomSongRequest();
            createSong(randomUser);
        }
        return new ResponseEntity<>(songRepository.count(), HttpStatus.CREATED);
    }
    List<String> randomSongNames = Arrays.asList(
            "Whispering Fire",
                    "Moonlight Serenade",
                    "Echoes of the Heart",
                    "Melodies of Tomorrow",
                    "Dancing in the Rain",
                    "Serene Symphony",
                    "Starry Nights",
                    "Memories Unforgotten",
                    "Lost in Time",
                    "Ocean's Lullaby",
                    "Forever and Beyond",
                    "Enchanted Dreams",
                    "River of Love",
                    "Chasing the Wind",
                    "Euphoria's Embrace",
                    "Shadows of the Past",
                    "Journey to Destiny",
                    "Eternal Echo",
                    "Velvet Skies",
                    "Harmonious Whispers",
                    "Midnight Rendezvous",
                    "Destiny's Call",
                    "Reflections of the Soul",
                    "Cascade of Dreams",
                    "Serenade of the Seas",
                    "Whispers in the Wind",
                    "Dreamer's Delight",
                    "Twilight Melodies",
                    "Symphony of Hope",
                    "Dance of the Stars",
                    "Infinite Love",
                    "Aurora's Embrace",
                    "Serenity's Song",
                    "Echoes of Eternity",
                    "Melody of the Heart",
                    "Ocean's Symphony",
                    "Eternal Love Affair",
                    "Rhapsody in Blue",
                    "Lost Horizon",
                    "Enigma of Time",
                    "Memories in Motion",
                    "Cosmic Ballad",
                    "Harmony's Embrace",
                    "Midnight Muse",
                    "Whispers of the Night",
                    "Voyage of the Heart",
                    "Dreamer's Destiny",
                    "Starlight Sonata",
                    "Elysian Melody",
                    "River of Dreams",
                    "Celestial Waltz",
                    "Eternal Symphony",
                    "Echoes of Infinity",
                    "Enchanted Reverie",
                    "Lullaby of the Moon",
                    "Melodies of Eternity",
                    "Ocean's Embrace",
                    "Symphony of Dreams",
                    "Chasing the Stars",
                    "Aria of Hope",
                    "Ethereal Echoes",
                    "Whispering Melodies",
                    "Moonlit Memories",
                    "Serenade of the Soul",
                    "Song of the Sirens",
                    "Cascade of Emotions",
                    "Dreamer's Desire",
                    "Symphony of the Seas",
                    "Twilight Whispers",
                    "Celestial Dreams",
                    "Enchanted Serenity",
                    "Whispering Winds",
                    "Melodies of the Night",
                    "Echoes of Passion",
                    "Ocean's Lament",
                    "Symphony of the Heart",
                    "Dancing with Fire",
                    "Eternal Serenade",
                    "River of Time",
                    "Whispers in the Dark",
                    "Euphoria's Embrace",
                    "Moonlit Rhapsody",
                    "Serenade of the Stars",
                    "Stardust Melody",
                    "Lost in Love",
                    "Echoes of Destiny",
                    "Enchanted Memories",
                    "Symphony of Silence",
                    "Melodies of the Sea",
                    "Ocean's Call",
                    "Eternal Bliss",
                    "Whispering Echoes",
                    "Midnight Melodies",
                    "Rhapsody of the Heart",
                    "Serenade of the Senses",
                    "Dreams of Tomorrow",
                    "Enigma of Love",
                    "Symphony of the Night",
                    "Chasing the Moon",
                    "Echoes of Tranquility",
                    "Serenade of the Skies",
                    "Whispering Eternity",
                    "Moonlit Symphony",
                    "Melodies of the Stars",
                    "Ocean's Fury",
                    "Symphony of Solitude",
                    "Dancing in Dreams",
                    "Eternal Embrace",
                    "Whispers of the Wind",
                    "River of Serenity",
                    "Euphoria's Echo",
                    "Twilight Serenade",
                    "Serenade of the Shadows",
                    "Echoes of Desire",
                    "Enchanted Reverie",
                    "Symphony of the Sirens",
                    "Melody of Memories",
                    "Ocean's Whispers",
                    "Eternal Whispers",
                    "Whispering Serenity",
                    "Moonlight Melodies",
                    "Serenade of the Soul",
                    "Dreams of Eternity",
                    "Enchanted Symphony",
                    "Symphony of the Stars",
                    "Echoes of the Sea",
                    "Melodies of Love",
                    "Ocean's Embrace",
                    "Eternal Echoes",
                    "Whispering Waters",
                    "Moonlit Whispers",
                    "Serenade of Dreams",
                    "Symphony of the Skies",
                    "Dancing in the Stars",
                    "Echoes of Forever",
                    "Enchanted Melody",
                    "Melodies of the Heart",
                    "Ocean's Lullaby",
                    "Eternal Serenade",
                    "Whispering Echo",
                    "Moonlight Sonata",
                    "Serenade of the Night",
                    "Symphony of Time",
                    "Dancing with Shadows",
                    "Echoes of Love",
                    "Enchanted Dreams",
                    "Melodies of the Soul",
                    "Ocean's Melody",
                    "Eternal Rhapsody",
                    "Whispering Breeze",
                    "Moonlit Lullaby",
                    "Serenade of Eternity",
                    "Symphony of Dreams",
                    "Dancing in Silence",
                    "Echoes of the Universe",
                    "Enchanted Echoes",
                    "Melodies of the Wind",
                    "Ocean's Harmony",
                    "Eternal Melody",
                    "Whispering Silence",
                    "Moonlight Whispers",
                    "Serenade of the Ocean",
                    "Symphony of Hope",
                    "Dancing with Destiny",
                    "Echoes of the Heart",
                    "Enchanted Symphony",
                    "Melodies of the Stars",
                    "Ocean's Symphony",
                    "Eternal Harmony",
                    "Whispering Dreams",
                    "Moonlit Serenade",
                    "Serenade of the Universe",
                    "Symphony of Tranquility",
                    "Dancing in the Rain",
                    "Echoes of the Soul",
                    "Enchanted Lullaby",
                    "Melodies of Destiny",
                    "Ocean's Song",
                    "Eternal Waltz",
                    "Whispering Destiny",
                    "Moonlight Dance",
                    "Serenade of Tranquility",
                    "Symphony of the Night",
                    "Dancing with Fireflies",
                    "Echoes of the Mind",
                    "Enchanted Voyage",
                    "Melodies of the Sirens",
                    "Ocean's Melody",
                    "Eternal Sonata",
                    "Whispering Melodies",
                    "Moonlit Embrace",
                    "Serenade of the Heart",
                    "Symphony of the Stars",
                    "Dancing in the Wind",
                    "Echoes of Solitude",
                    "Enchanted Whispers"
    );

    public SongRequest generateRandomSongRequest() {
        // Implement logic to generate random user data here
        // For simplicity, you can use a library like Faker to generate random data
        // Replace the placeholders with actual logic to generate random data
        SongRequest songRequest = new SongRequest();
        songRequest.setTitle(randomSongNames.get(rand.nextInt(randomSongNames.size())));
        songRequest.setArtistId(String.valueOf(rand.nextInt(10)));
        songRequest.setGenre(Genre.randomGenre()) ;
        songRequest.setDuration(rand.nextInt(300) + 60);
        songRequest.setAlbum("1");
        songRequest.setLikes(rand.nextInt(5000));
        songRequest.setReleaseDate(new Date());
        songRequest.setPlays(rand.nextInt(50000));
        songRequest.setPath("test");

        return songRequest;
    }

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
            try {
                Double averageRating = webClient.get()
                        .uri("http://" + ratingServiceBaseUrl + "/api/rating/" + songId + "/average-rating")
                        .retrieve()
                        .bodyToMono(Double.class)
                        .block();

                SongResponse songResponse = mapToSongResponse(optionalSong.get());
                songResponse.setAverageRating(averageRating);
                return new ResponseEntity<>(songResponse, HttpStatus.OK);
            } catch (WebClientResponseException.NotFound e) {
                // handle the 404 error here
                // just return null for the average and handle it client side to not display
                return new ResponseEntity<>(mapToSongResponse(optionalSong.get()), HttpStatus.OK);
            }
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

            // Call the library service to update partial songs
            ResponseEntity<?> responseEntity = webClient.put()
                .uri("http://" + playlistServiceBaseUrl + "/api/playlist/updatePartialSongs")
                .body(Mono.just(mapToSongResponse(song)), SongResponse.class) // Pass the SongResponse to the body
                .exchangeToMono(response -> {
                    if (response.statusCode().is2xxSuccessful()) {
                        return response.bodyToMono(ResponseEntity.class);
                    } else {
                        System.out.println(response.statusCode());
                        return Mono.error(new RuntimeException("Could not update partial songs!!"));
                    }
                })
                .onErrorResume(error -> {
                    // Handle the error case
                    System.out.println("Error occurred: " + error.getMessage());
                    return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
                })
                .block();

            songRepository.save(song);
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

    public List<SongResponse> getTopSongs(int count) {
        List<Song> songs = songRepository.findAll();
        Collections.shuffle(songs);
        if (count > songs.size()) {
            count = songs.size();
        }
        List<Song> topSongs = songs.subList(0, count );
        return topSongs.stream().map(this::mapToSongResponse).toList();
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
