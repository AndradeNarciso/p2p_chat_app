package com.andrade.chat_app.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.andrade.chat_app.domain.User;
import com.andrade.chat_app.dto.UserIdRequest;
import com.andrade.chat_app.dto.UserRequest;
import com.andrade.chat_app.service.UserService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @MessageMapping("/user/connect")
    @SendTo("/user/topic")
    public User saveUserController(@Valid @Payload UserRequest user) {

        System.out.println("=====================================");
        System.out.println(">>> REQUISIÇÃO CHEGOU NO BACKEND !!!");
        System.out.println(">>> /app/user/connect foi chamado agora");
        System.out.println(">>> FullName recebido: " + user.fullName());
        System.out.println(">>> NickName recebido: " + user.nickName());
        System.out.println("=====================================");

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

}
