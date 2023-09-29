package fact.it.ratingservice.repository;


import fact.it.ratingservice.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RatingRepository extends JpaRepository<Rating, Long> {
}
