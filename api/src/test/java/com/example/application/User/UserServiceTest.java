package com.example.application.User;

import com.example.application.Auth.AuthenticationResponse;
import com.example.application.Auth.RegisterRequest;
import com.example.application.Config.JwtService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;  // main one


@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    private UserService underTest;
    @Mock
    private UserDAO userDAO;

    @Mock
    private userDTOMapper userDTOMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;


    @InjectMocks
    private UserService userManagement;

    @BeforeEach
    void setUp() {

     //   underTest = new UserService(userRepository,userDAO,userDTOMapper,passwordEncoder,jwtService,authenticationManager);

    }



    @Test
    @Disabled
    void canRegister() {

        // Given
        RegisterRequest registerRequest = new RegisterRequest("John", "Doe", "john.doe@example.com", Role.ADMIN, "USER");
        // When
        when(jwtService.generateToken(any())).thenReturn("jwt-token");
        Object result = underTest.register(registerRequest);
        // Then
        assertThat(result).isInstanceOf(AuthenticationResponse.class);
        AuthenticationResponse response = (AuthenticationResponse) result;
        assertThat(response.getAccess_token()).isEqualTo("jwt-token");
        Optional<User> savedUser = userRepository.findByEmail("john.doe@example.com");
        assertThat(savedUser).isPresent();
        assertThat(savedUser.get().getFirstname()).isEqualTo("John");
        assertThat(savedUser.get().getLastname()).isEqualTo("Doe");
        assertThat(savedUser.get().getEmail()).isEqualTo("john.doe@example.com");
        assertThat(savedUser.get().getRole()).isEqualTo("USER");
        assertThat(passwordEncoder.matches("password", savedUser.get().getPassword())).isTrue();


    }

    @Test
    void willThrowWhenEmailTaken() {

        //given
        RegisterRequest request = new RegisterRequest("John", "Doe", "john.doe@example.com", Role.ADMIN, "ROLE_USER");

        User existingUser = User.builder()
                .id(1L)
                .firstname("John")
                .lastname("Doe")
                .email("john.doe@example.com")
                .role(Role.ADMIN)
                .password("password")
                .build();

        //when

        //then
        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.of(existingUser));

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> userManagement.register(request));
        String expectedMessage = "user already exist";
        String actualMessage = exception.getMessage();

        assert(expectedMessage.equals(actualMessage));




    }







    @Test
    void simpleTestcanRegister() {

        //given
        User user1 = User.builder()
                .id(1L)
                .firstname("John")
                .lastname("Doe")
                .email("john.doe@example.com")
                .role(Role.ADMIN)
                .password("password")
                .build();
        //when
        userRepository.save(user1);

        //then
        ArgumentCaptor<User> studentArgumentCaptor = ArgumentCaptor.forClass(User.class); // what service receive



        verify(userRepository).save(studentArgumentCaptor.capture());

        User captureUser = studentArgumentCaptor.getValue(); //wht service receive user

        assertEquals(captureUser,user1);  //compare the received data between service and function if it's the sames




    }

    @Test
    @Disabled
    void authenticate() {
    }

    @Test
    @Disabled
    void getUser() {



    }

    @Test
    void canGetAllUser() {

        //given
        User user1 = User.builder()
                .id(1L)
                .firstname("John")
                .lastname("Doe")
                .email("john.doe@example.com")
                .role(Role.ADMIN)
                .password("password")
                .build();

        User user2 = User.builder()
                .id(2L)
                .firstname("Jane")
                .lastname("Doe")
                .email("jane.doe@example.com")
                .role(Role.ADMIN)
                .password("password")
                .build();

        UserDTO userDTO1 = new UserDTO("John", "Doe", "john.doe@example.com");
        UserDTO userDTO2 = new UserDTO( "Jane", "Doe", "jane.doe@example.com");

        List<User> userList = new ArrayList<>();
        userList.add(user1);
        userList.add(user2);

        List<UserDTO> expectedUserDTOList = new ArrayList<>();
        expectedUserDTOList.add(userDTO1);
        expectedUserDTOList.add(userDTO2);

        when(userDAO.findAllUser()).thenReturn(userList);
        when(userDTOMapper.apply(user1)).thenReturn(userDTO1);
        when(userDTOMapper.apply(user2)).thenReturn(userDTO2);

        List<UserDTO> actualUserDTOList = userManagement.getAllUser();

        assertEquals(expectedUserDTOList, actualUserDTOList);

        verify(userDAO, times(1)).findAllUser();
        verify(userDTOMapper, times(1)).apply(user1);
        verify(userDTOMapper, times(1)).apply(user2);
    }
}