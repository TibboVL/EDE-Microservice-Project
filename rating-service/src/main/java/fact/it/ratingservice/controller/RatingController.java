package fact.it.ratingservice.controller;

import fact.it.ratingservice.dto.RatingRequest;
import fact.it.ratingservice.dto.RatingResponse;
import fact.it.ratingservice.service.RatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rating")
@RequiredArgsConstructor
public class RatingController {

    private final RatingService ratingService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<RatingResponse> getAllRatings() {
        return ratingService.getAllRatings();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void createRating(@RequestBody RatingRequest ratingRequest) {
        ratingService.createRating(ratingRequest);
    }
}
