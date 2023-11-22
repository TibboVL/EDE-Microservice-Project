package fact.it.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private String id;
    private String userId;
    private String firstname;
    private String  lastname;
    private String  username;
    private String  email;
    private Date dateOfBirth;
    private Date registrationDate;
}
