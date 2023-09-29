package fact.it.ratingservice.controller;

import fact.it.ratingservice.dto.RatingRequest;
import fact.it.ratingservice.dto.RatingResponse;
import fact.it.ratingservice.model.Rating;
import fact.it.ratingservice.service.RatingService;
import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<RatingResponse> getRating(@PathVariable("id") Long songId) {
        return ratingService.getRating(songId);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RatingResponse> updateRating(@PathVariable("id") Long songId, @RequestBody RatingRequest ratingRequest) {
        return ratingService.updateRating(songId, ratingRequest);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<RatingResponse> deleteRating(@PathVariable("id") Long songId) {
        return ratingService.deleteRating(songId);
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<RatingResponse> getAllRatings() {
        return ratingService.getAllRatings();
    }

}
