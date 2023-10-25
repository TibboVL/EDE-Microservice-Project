package fact.it.userservice;

import fact.it.userservice.dto.UserRequest;
import fact.it.userservice.dto.UserResponse;
import fact.it.userservice.model.User;
import fact.it.userservice.repository.UserRepository;
import fact.it.userservice.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class UserServiceUnitTests {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Test
    public void testCreateUser_Success() {
        // Arrange
        UserRequest userRequest = new UserRequest();
        userRequest.setFirstname("John");
        userRequest.setLastname("Doe");
        userRequest.setUsername("johndoe");
        userRequest.setEmail("johndoe@example.com");
        userRequest.setDateOfBirth(new Date());

        // Act
        ResponseEntity<UserResponse> responseEntity = userService.createUser(userRequest);

        // Assert
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(userRequest.getFirstname(), responseEntity.getBody().getFirstname());
        assertEquals(userRequest.getLastname(), responseEntity.getBody().getLastname());
        assertEquals(userRequest.getUsername(), responseEntity.getBody().getUsername());
        assertEquals(userRequest.getEmail(), responseEntity.getBody().getEmail());
        assertEquals(userRequest.getDateOfBirth(), responseEntity.getBody().getDateOfBirth());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void testGetUserWithExistingUser_ReturnsUser() {
        // Arrange
        String userId = "123";
        User user = new User();
        user.setId(userId);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // Act
        ResponseEntity<UserResponse> responseEntity = userService.getUser(userId);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(user.getId(), responseEntity.getBody().getId());
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    public void testUpdateUserWithExistingUser() {
        // Arrange
        String userId = "123";
        UserRequest userRequest = new UserRequest();
        userRequest.setFirstname("John");
        userRequest.setLastname("Doe");
        userRequest.setUsername("johndoe");
        userRequest.setEmail("johndoe@example.com");
        userRequest.setDateOfBirth(new Date());
        userRequest.setRegistrationDate(new Date());

        User existingUser = new User();
        existingUser.setId(userId);
        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));

        // Act
        ResponseEntity<UserResponse> responseEntity = userService.updateUser(userId, userRequest);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(userRequest.getFirstname(), responseEntity.getBody().getFirstname());
        assertEquals(userRequest.getLastname(), responseEntity.getBody().getLastname());
        assertEquals(userRequest.getUsername(), responseEntity.getBody().getUsername());
        assertEquals(userRequest.getEmail(), responseEntity.getBody().getEmail());
        assertEquals(userRequest.getDateOfBirth(), responseEntity.getBody().getDateOfBirth());
        assertEquals(userRequest.getRegistrationDate(), responseEntity.getBody().getRegistrationDate());

        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void testUpdateUserWithNotExistingUser() {
        // Arrange
        String userId = "123";
        UserRequest userRequest = new UserRequest();
        userRequest.setFirstname("John");
        userRequest.setLastname("Doe");
        userRequest.setUsername("johndoe");
        userRequest.setEmail("johndoe@example.com");
        userRequest.setDateOfBirth(new Date());
        userRequest.setRegistrationDate(new Date());
        when(userRepository.findById(userId)).thenReturn(Optional.empty()); // Simulating the user not being found

        // Act
        ResponseEntity<UserResponse> responseEntity = userService.updateUser(userId, userRequest);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, times(0)).save(any(User.class));
    }


    @Test
    public void testDeleteUserWithExistingUser() {
        // Arrange
        String userId = "123";
        User user = new User();
        user.setId(userId);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // Act
        ResponseEntity<UserResponse> responseEntity = userService.deleteUser(userId);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    public void testDeleteUserWithNotExistingUser() {
        // Arrange
        String userId = "123";
        when(userRepository.findById(userId)).thenReturn(Optional.empty()); // Simulating the user not being found

        // Act
        ResponseEntity<UserResponse> responseEntity = userService.deleteUser(userId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        verify(userRepository, times(0)).deleteById(userId);
    }


    @Test
    public void testGetAllUsers() {
        // Arrange
        List<User> users = new ArrayList<>();
        users.add(new User());
        when(userRepository.findAll()).thenReturn(users);

        // Act
        List<UserResponse> userResponses = userService.getAllUsers();

        // Assert
        assertEquals(users.size(), userResponses.size());
        verify(userRepository, times(1)).findAll();
    }

}
