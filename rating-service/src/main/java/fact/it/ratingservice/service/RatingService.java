package fact.it.ratingservice.service;

import fact.it.ratingservice.dto.RatingRequest;
import fact.it.ratingservice.dto.RatingResponse;
import fact.it.ratingservice.model.Rating;
import fact.it.ratingservice.repository.RatingRepository;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RatingService {

    private final RatingRepository ratingRepository;
    private final WebClient webClient;


    public ResponseEntity<RatingResponse> createRating(RatingRequest ratingRequest) {
        Rating rating = Rating.builder()
                .songId(ratingRequest.getSongId())
                .userId(ratingRequest.getUserId())
                .rating(ratingRequest.getRating())
                .build();
        ratingRepository.save(rating);
        return new ResponseEntity<>(mapToOrderLineItem(rating), HttpStatus.CREATED);
    }

    public ResponseEntity<RatingResponse> getRating(Long ratingId) {
        Optional<Rating> optionalRating = ratingRepository.findById(ratingId);
        if (optionalRating.isPresent()) {
            return new ResponseEntity<>(mapToOrderLineItem(optionalRating.get()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<RatingResponse> updateRating(Long songId, RatingRequest ratingRequest) {
        Optional<Rating> optionalRating = ratingRepository.findById(songId);
        if (optionalRating.isPresent()) {
            Rating rating = optionalRating.get();
            rating.setUserId(ratingRequest.getUserId());
            rating.setSongId(ratingRequest.getSongId());
            rating.setRating(ratingRequest.getRating());
            return new ResponseEntity<>(mapToOrderLineItem(optionalRating.get()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<RatingResponse> deleteRating(Long ratingId) {
        Optional<Rating> optionalRating = ratingRepository.findById(ratingId);
        if (optionalRating.isPresent()) {
            ratingRepository.deleteById(ratingId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }



    public List<RatingResponse> getAllRatings() {
        List<Rating> ratings = ratingRepository.findAll();

        return ratings.stream().map(this::mapToOrderLineItem).toList();
    }

    private RatingResponse mapToOrderLineItem(Rating rating) {
        return RatingResponse.builder()
                .id(rating.getId())
                .userId(rating.getUserId())
                .songId(rating.getSongId())
                .rating(rating.getRating())
                .build();

    }
}
