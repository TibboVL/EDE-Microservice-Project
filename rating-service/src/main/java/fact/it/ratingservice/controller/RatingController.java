package fact.it.ratingservice.controller;

import fact.it.ratingservice.dto.RatingRequest;
import fact.it.ratingservice.dto.RatingResponse;
import fact.it.ratingservice.model.Rating;
import fact.it.ratingservice.service.RatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rating")
@RequiredArgsConstructor
public class RatingController {

    private final RatingService ratingService;

    @PostMapping
    public ResponseEntity<RatingResponse> createRating(@RequestBody RatingRequest ratingRequest) {
        return ratingService.createRating(ratingRequest);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RatingResponse> getRating(@PathVariable("id") Long ratingId) {
        return ratingService.getRating(ratingId);
    }

    // private endpoint accessed via library service
    @GetMapping("/{songId}/average-rating")
    public ResponseEntity<Double> getAverageRating(@PathVariable("songId") String songId) {
        return ratingService.getAverageRating(songId);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RatingResponse> updateRating(@PathVariable("id") Long ratingId, @RequestBody RatingRequest ratingRequest) {
        return ratingService.updateRating(ratingId, ratingRequest);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<RatingResponse> deleteRating(@PathVariable("id") Long ratingId) {
        return ratingService.deleteRating(ratingId);
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<RatingResponse> getAllRatings() {
        return ratingService.getAllRatings();
    }

}
