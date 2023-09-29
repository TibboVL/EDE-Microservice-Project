package fact.it.playlistservice.repository;

import fact.it.playlistservice.model.Playlist;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface PlaylistRepository extends MongoRepository<Playlist, String> {
}
