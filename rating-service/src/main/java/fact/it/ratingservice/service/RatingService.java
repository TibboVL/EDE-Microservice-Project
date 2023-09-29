package fact.it.ratingservice.service;

import fact.it.ratingservice.dto.RatingRequest;
import fact.it.ratingservice.dto.RatingResponse;
import fact.it.ratingservice.model.Rating;
import fact.it.ratingservice.repository.RatingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RatingService {

    private final RatingRepository ratingRepository;
    private final WebClient webClient;

    public List<RatingResponse> getAllRatings() {
        List<Rating> ratings = ratingRepository.findAll();

        return ratings.stream().map(this::mapToOrderLineItem).toList();
    }

    public void createRating(RatingRequest ratingRequest) {
        Rating rating = Rating.builder()
                .songId(ratingRequest.getSongId())
                .userId(ratingRequest.getUserId())
                .rating(ratingRequest.getRating())
                .build();
        ratingRepository.save(rating);
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
