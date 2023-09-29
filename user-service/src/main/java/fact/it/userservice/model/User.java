package fact.it.userservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document(value = "user")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class User {
    private String id;
    private String firstname;
    private String  lastname;
    private String  username;
    private String  email;
    private Date dateOfBirth;
    private Date registrationDate;
//    private List<Long> favoritePlaylists; // List of playlist ids
//    private Long roleId;
}
