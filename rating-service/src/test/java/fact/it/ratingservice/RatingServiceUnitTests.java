package fact.it.ratingservice;

import fact.it.ratingservice.dto.RatingRequest;
import fact.it.ratingservice.dto.RatingResponse;
import fact.it.ratingservice.model.Rating;
import fact.it.ratingservice.repository.RatingRepository;
import fact.it.ratingservice.service.RatingService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RatingServiceUnitTests {

    @InjectMocks
    private RatingService ratingService;

    @Mock
    private RatingRepository ratingRepository;

    @Test
    public void testCreateRating_Success() {
        // Arrange
        RatingRequest ratingRequest = new RatingRequest();
        ratingRequest.setSongId("123");
        ratingRequest.setUserId("456");
        ratingRequest.setRating(4);

        // Act
        ResponseEntity<RatingResponse> responseEntity = ratingService.createRating(ratingRequest);

        // Assert
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(ratingRequest.getSongId(), responseEntity.getBody().getSongId());
        assertEquals(ratingRequest.getUserId(), responseEntity.getBody().getUserId());
        assertEquals(ratingRequest.getRating(), responseEntity.getBody().getRating());
        verify(ratingRepository, times(1)).save(any(Rating.class));
    }

    @Test
    public void testGetRatingWithExistingRating_ReturnsRating() {
        // Arrange
        Long ratingId = 123L;
        Rating rating = new Rating();
        rating.setId(ratingId);
        when(ratingRepository.findById(ratingId)).thenReturn(Optional.of(rating));

        // Act
        ResponseEntity<RatingResponse> responseEntity = ratingService.getRating(ratingId);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(rating.getId(), responseEntity.getBody().getId());
        verify(ratingRepository, times(1)).findById(ratingId);
    }

    @Test
    public void testUpdateRatingWithExistingRating() {
        // Arrange
        Long ratingId = 123L;
        RatingRequest ratingRequest = new RatingRequest();
        ratingRequest.setSongId("123");
        ratingRequest.setUserId("456");
        ratingRequest.setRating(4);

        Rating existingRating = new Rating();
        existingRating.setId(ratingId);
        when(ratingRepository.findById(ratingId)).thenReturn(Optional.of(existingRating));

        // Act
        ResponseEntity<RatingResponse> responseEntity = ratingService.updateRating(ratingId, ratingRequest);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(ratingRequest.getSongId(), responseEntity.getBody().getSongId());
        assertEquals(ratingRequest.getUserId(), responseEntity.getBody().getUserId());
        assertEquals(ratingRequest.getRating(), responseEntity.getBody().getRating());
        verify(ratingRepository, times(1)).findById(ratingId);
        verify(ratingRepository, times(1)).save(any(Rating.class));
    }

    @Test
    public void testUpdateRatingWithNotExistingRating() {
        // Arrange
        Long ratingId = 123L;
        RatingRequest ratingRequest = new RatingRequest();
        ratingRequest.setSongId("123");
        ratingRequest.setUserId("456");
        ratingRequest.setRating(4);

        when(ratingRepository.findById(ratingId)).thenReturn(Optional.empty()); // Simulate the rating not being found

        // Act
        ResponseEntity<RatingResponse> responseEntity = ratingService.updateRating(ratingId, ratingRequest);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        verify(ratingRepository, times(1)).findById(ratingId);
        verify(ratingRepository, times(0)).save(any(Rating.class));
    }

    @Test
    public void testDeleteRatingWithExistingRating() {
        // Arrange
        Long ratingId = 123L;
        Rating rating = new Rating();
        rating.setId(ratingId);
        when(ratingRepository.findById(ratingId)).thenReturn(Optional.of(rating));

        // Act
        ResponseEntity<RatingResponse> responseEntity = ratingService.deleteRating(ratingId);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        verify(ratingRepository, times(1)).deleteById(ratingId);
    }

    @Test
    public void testDeleteRatingWithNotExistingRating() {
        // Arrange
        Long ratingId = 123L;
        when(ratingRepository.findById(ratingId)).thenReturn(Optional.empty()); // Simulate the rating not being found

        // Act
        ResponseEntity<RatingResponse> responseEntity = ratingService.deleteRating(ratingId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        verify(ratingRepository, times(0)).deleteById(ratingId);
    }


    @Test
    public void testGetAllRatings() {
        // Arrange
        List<Rating> ratings = new ArrayList<>();
        ratings.add(new Rating());
        when(ratingRepository.findAll()).thenReturn(ratings);

        // Act
        List<RatingResponse> ratingResponses = ratingService.getAllRatings();

        // Assert
        assertEquals(ratings.size(), ratingResponses.size());
        verify(ratingRepository, times(1)).findAll();
    }
}
