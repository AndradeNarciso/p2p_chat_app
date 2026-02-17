package com.andrade.chat_app.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.andrade.chat_app.domain.User;
import com.andrade.chat_app.dto.user.UserIdRequest;
import com.andrade.chat_app.dto.user.UserRequest;
import com.andrade.chat_app.service.UserService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @MessageMapping("/user/connect")
    @SendTo("/topic/users")
    public User saveUserController(@Valid @Payload UserRequest user) {
        return userService.saveUserService(user);
    }

    @MessageMapping("/user/disconnect")
    @SendTo("/user/topic")
    public User disconnectUser(@Valid @Payload UserIdRequest user) {
        return userService.disconnectUserService(user);
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAll() {
        return new ResponseEntity<>(userService.findConnectedUser(), HttpStatus.OK);
    }

    @PostMapping("/id")
    public ResponseEntity<String> getUserId(@RequestBody UserRequest userRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getIdUserService(userRequest));
    }

}
