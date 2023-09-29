package fact.it.libraryservice.repository;

import fact.it.libraryservice.model.Song;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SongRepository extends MongoRepository<Song, String> {

}
