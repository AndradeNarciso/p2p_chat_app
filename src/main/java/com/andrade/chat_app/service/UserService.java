package com.andrade.chat_app.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.andrade.chat_app.domain.User;
import com.andrade.chat_app.dto.UserIdRequest;
import com.andrade.chat_app.dto.UserRequest;
import com.andrade.chat_app.enums.Status;
import com.andrade.chat_app.exception.QueryException;
import com.andrade.chat_app.repository.UserRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    @Transactional(rollbackFor = Exception.class)
    public User saveUserService(UserRequest userRequest) {
        User user = User.builder()
                .fullName(userRequest.fullName())
                .nickName(userRequest.nickName())
                .build();
        return userRepository.save(user);
    }

    public User disconnectUserService(UserIdRequest user) {
        User savedUser = userRepository
                .findById(user.id())
                .orElseThrow(() -> new QueryException("Unable to find user"));

        if (!(savedUser.getStatus() == Status.ONLINE)) {
            log.info("User already desconnected");
            return savedUser;
        }

        savedUser.setStatus(Status.OFFLINE);
        return userRepository.save(savedUser);

    }

    public List<User> findConnectedUser() {

        List<User> connectedUser = userRepository
                .findAllByStatus(Status.ONLINE)
                .orElseThrow(() -> new QueryException("Was not found Connected user"));

        return connectedUser;

    }

}
