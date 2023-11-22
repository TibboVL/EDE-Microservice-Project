package fact.it.userservice.service;

import fact.it.userservice.dto.UserRequest;
import fact.it.userservice.dto.UserResponse;
import fact.it.userservice.model.User;
import fact.it.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public ResponseEntity<Long> addDummyData() {
        userRepository.deleteAll();
        int amount = 100;
        for (int i = 0; i < amount; i++) {
            UserRequest randomUser = generateRandomUserRequest();
            createUser(randomUser);
        }
        return new ResponseEntity<>(userRepository.count(), HttpStatus.CREATED);
    }
    public UserRequest generateRandomUserRequest() {
        // Implement logic to generate random user data here
        // For simplicity, you can use a library like Faker to generate random data
        // Replace the placeholders with actual logic to generate random data
        UserRequest userRequest = new UserRequest();
        userRequest.setFirstname("RandomFirstName");
        userRequest.setLastname("RandomLastName");
        userRequest.setUsername("RandomUsername");
        userRequest.setEmail("random@example.com");
        userRequest.setDateOfBirth(new Date());
        return userRequest;
    }

    public ResponseEntity<UserResponse> createUser(UserRequest userRequest) {
        User user = User.builder()
                .userId(userRequest.getUserId())
                .firstname(userRequest.getFirstname())
                .lastname(userRequest.getLastname())
                .username(userRequest.getUsername())
                .email(userRequest.getEmail())
                .dateOfBirth(userRequest.getDateOfBirth())
                .registrationDate(new Date())
                .build();

        userRepository.save(user);
        return new ResponseEntity<>(mapToUserResponse(user), HttpStatus.CREATED);
    }

    public ResponseEntity<UserResponse> getUser(String userId) {
        Optional<User> optionalRating = userRepository.findById(userId);
        if (optionalRating.isPresent()) {
            return new ResponseEntity<>(mapToUserResponse(optionalRating.get()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<UserResponse> checkExistenceUser(String userId) {
        Optional<User> optionalRating = userRepository.findFirstByUserId(userId);
        if (optionalRating.isPresent()) {
            return new ResponseEntity<>(mapToUserResponse(optionalRating.get()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<UserResponse> updateUser(String userId, UserRequest userRequest) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setFirstname(userRequest.getFirstname());
            user.setLastname(userRequest.getLastname());
            user.setUsername(userRequest.getUsername());
            user.setEmail(userRequest.getEmail());
            user.setDateOfBirth(userRequest.getDateOfBirth());
            user.setRegistrationDate(userRequest.getRegistrationDate());
            userRepository.save(user);
            return new ResponseEntity<>(mapToUserResponse(optionalUser.get()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<UserResponse> deleteUser(String userId) {
        Optional<User> optionalRating = userRepository.findById(userId);
        if (optionalRating.isPresent()) {
            userRepository.deleteById(userId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public List<UserResponse> getAllUsers() {
        List<User> users = userRepository.findAll();

        return users.stream().map(this::mapToUserResponse).toList();
    }

    private UserResponse mapToUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .userId(user.getUserId())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .username(user.getUsername())
                .email(user.getEmail())
                .dateOfBirth(user.getDateOfBirth())
                .registrationDate(user.getRegistrationDate())
                .build();
    }
}
