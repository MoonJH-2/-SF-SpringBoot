package com.moon.demo01.service;

import com.moon.demo01.dto.UserRequest;
import com.moon.demo01.dto.UserResponse;
import com.moon.demo01.entity.User;
import com.moon.demo01.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import static org.mockito.Mockito.when;

@Slf4j
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void registerUser_ShouldSaveUser_WhenValidRequest() {
        UserRequest request = new UserRequest("test@example.com", "password123", "Test User");
        User savedUser = new User();
        savedUser.setId("1");
        savedUser.setEmail("test@example.com");
        savedUser.setName("Test User");
        savedUser.setPassword("encodedPassword");

        when(userRepository.existsByEmail(request.getEmail())).thenReturn(false);
        when(passwordEncoder.encode(request.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        UserResponse response = userService.registerUser(request);

        log.info("UserRequest: {}", request);
        log.info("Saved User: {}", savedUser);
        log.info("Response: {}", response);

        assertThat(response).isNotNull();
        assertThat(response.getEmail()).isEqualTo(request.getEmail());
        assertThat(response.getName()).isEqualTo(request.getName());
        verify(userRepository, times(1)).existsByEmail(request.getEmail());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void registerUser_ShouldThrowException_WhenEmailExists() {
        UserRequest request = new UserRequest("test@example.com", "password123", "Test User");

        when(userRepository.existsByEmail(request.getEmail())).thenReturn(true);
        log.info("Mock setup: existsByEmail for {} = true", request.getEmail());


        assertThatThrownBy(() -> userService.registerUser(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이메일이 이미 존재합니다.");

        //log.info("Exception successfully thrown for duplicate email: {}", request.getEmail());


        verify(userRepository, times(1)).existsByEmail(request.getEmail());
        verify(userRepository, never()).save(any(User.class));
        log.info("Verified: existsByEmail was called once, save was never called.");
    }

}
/*  테스트 시나리오 설명
 *   1. registerUser_ShouldSaveUser_WhenValidRequest
 *       UserRequest가 유효하고 이메일이 중복되지 않은 경우:
 *           UserRepository.save()가 호출되고, 성공적으로 저장된 UserResponse가 반환.
 *           PasswordEncoder를 통해 비밀번호를 암호화하는 동작을 확인한다.
 *   2. registerUser_ShouldThrowException_WhenEmailExists
 *       이미 존재하는 이메일로 UserRequest가 들어온 경우:
 *           예외가 발생해야 함.
 *           UserRepository.save()는 호출되지 않아야 함.
 *
 *   Mock 동작
 *       when().thenReturn(): 특정 상황에서 Mock 객체의 동작 정의.
 *       verify(): 메서드 호출 횟수를 검증.
 * */