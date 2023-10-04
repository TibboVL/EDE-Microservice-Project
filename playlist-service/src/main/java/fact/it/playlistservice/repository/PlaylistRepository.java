package fact.it.playlistservice.repository;

import fact.it.playlistservice.model.Playlist;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;


public interface PlaylistRepository extends MongoRepository<Playlist, String> {
    // get a users playlists
    List<Playlist> findAllByUserId(String userId);
}
