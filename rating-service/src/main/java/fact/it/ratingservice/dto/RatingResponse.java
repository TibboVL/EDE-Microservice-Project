package fact.it.ratingservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class RatingResponse {
    private Long id;
    private String userId;
    private String songId;
    private Integer rating;
}
